/*
 * Copyright 2020 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squareup.contour.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity.ERROR
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiType
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.getContainingUClass
import org.jetbrains.uast.getParentOfType
import org.jetbrains.uast.getUCallExpression

@Suppress("UnstableApiUsage")
class NestedContourLayoutsDetector : Detector(), Detector.UastScanner {

  override fun getApplicableUastTypes(): List<Class<out UElement>> =
    listOf(UCallExpression::class.java)

  override fun createUastHandler(context: JavaContext): UElementHandler {
    return object : UElementHandler() {
      override fun visitCallExpression(node: UCallExpression) {
        if (isContourLayoutFunction(node)) {
          check(context, node)
        }
      }
    }
  }

  private fun isContourLayoutFunction(node: UCallExpression): Boolean {
    if (node.methodName != "layoutBy" && node.methodName != "applyLayout") return false
    if (node.valueArgumentCount < 2) return false

    // Type resolution for kotlin classes doesn't work when lint
    // is run from the command line so node.resolve() will be null.
    val function = node.resolve()
    if (function == null) {
      // Skip the 0th param, which will be the type param (<T>).
      val argType = { at: Int -> node.getArgumentForParameter(at)?.getExpressionType() }
      return isAxisSolver(argType(1)) && isAxisSolver(argType(2))

    } else if (function.containingClass?.qualifiedName == "com.squareup.contour.ContourLayout") {
      val params = function.parameters
      return params[1]?.name == "x" && params[2]?.name == "y"
    }
    return false
  }

  private fun isAxisSolver(type: PsiType?): Boolean {
    if (type == null) return false
    if (type.canonicalText == "com.squareup.contour.solvers.AxisSolver") return true
    for (superT in type.superTypes) {
      if (isAxisSolver(superT)) return true
    }
    return false
  }

  private fun check(context: JavaContext, node: UCallExpression) {
    val isInsideLambda = node.getParentOfType<ULambdaExpression>() != null
    val isInsideContourView = isContourView(context, node.getContainingUClass())
    if (!isInsideLambda || !isInsideContourView) {
      return
    }

    val methodReceiver = (node.getUCallExpression()?.receiverType as? PsiClassType)?.resolve()
    val containingView = node.getContainingUClass()!!

    if (!containingView.isEquivalentTo(methodReceiver)) {
      // Code example:
      // class BarView : ContourLayout
      // class FooView : ContourLayout {
      //   val view = BarView(context).apply {
      //     layout(x, y) <- ERROR! The receiver of this layout() call isn't FooView.
      //   }
      // }
      val nestedContourView = methodReceiver?.name
      context.report(
          issue = ISSUE,
          scope = node,
          location = context.getNameLocation(node),
          message = "Calling `${node.methodName}()` on the wrong scope: `$nestedContourView` " +
              "instead of `${containingView.name}`. This will result in an infinite loop. " +
              "Consider using a lambda that offers `$nestedContourView` as an argument " +
              "(e.g., `also`, `let`) instead of a receiver (e.g., `with`, `apply`, `run`) or" +
              " moving this layout logic to `${containingView.name}`'s `init` block."
      )
    }
  }

  private fun isContourView(context: JavaContext, type: PsiClass?): Boolean {
    return context.evaluator.extendsClass(type, "com.squareup.contour.ContourLayout", true)
  }

  companion object {
    val ISSUE = Issue.create(
        id = "NestedContourLayouts",
        briefDescription = "Incorrectly nested ContourLayouts",
        explanation = """
          When a nested contour view is initialized and laid out using a lambda that offers \
          `this` as a receiver (e.g., Kotlin's `apply{}` and `run{}`), it's easy to accidentally \
          call `layoutBy()`/`applyLayout()` on the wrong scope. This lint flags these kinds of \
          errors. Here's an example:
          
          ```
          class FooView : ContourLayout {
            val view = BarView(context).apply {
              layoutBy(x, y) <- ERROR! The receiver of this call is BarView and not FooView!
            }
          }
          class BarView(context: Context) : ContourLayout
          ```
          
          The above example result in a StackOverflowException because `FooView` ends up laying \ 
          out itself rather than `BarView`.
        """.trimMargin(),
        category = Category.CORRECTNESS,
        severity = ERROR,
        priority = 5,
        implementation = Implementation(
            NestedContourLayoutsDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )
  }
}
