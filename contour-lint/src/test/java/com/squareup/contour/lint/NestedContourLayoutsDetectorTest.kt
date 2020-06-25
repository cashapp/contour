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

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters.JVM

@Suppress("UnstableApiUsage")
@FixMethodOrder(JVM)
class NestedContourLayoutsDetectorTest {

  private fun lint(source: TestFile): TestLintTask {
    return TestLintTask.lint().files(
        VIEW_STUB,
        AXIS_SOLVERS_STUB,
        CONTOUR_LAYOUT_STUB,
        source
    )
  }

  @Test fun `nested contour views with layoutBy{} are okay`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : ContourLayout(context) {
      |  private val view1 = BarView(context).layoutBy {
      |    LayoutSpec(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |  } 
      |}
      |
      |class BarView(context: Context) : ContourLayout(context)              
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .expectClean()
  }

  @Test fun `nested contour views with 'this' as the receiver`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : ContourLayout(context) {
      |  private val view1 = BarView(context).apply {
      |    layoutBy(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |  }
      |  private val view2 = with(BarView(context)) {
      |    layoutBy(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |  }
      |  private val view3 = BarView(context).run {
      |    applyLayout(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |  }
      |}
      |
      |class BarView(context: Context) : ContourLayout(context)              
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .expect(
            """
            |src/sample/FooView.kt:8: Error: Calling layoutBy(x,y) on a nested contour view (BarView) here is an error.
            | Prefer using BarView.layoutBy { LayoutSpec(x,y) } instead. [NestedContourLayouts]
            |    layoutBy(
            |    ~~~~~~~~
            |src/sample/FooView.kt:14: Error: Calling layoutBy(x,y) on a nested contour view (BarView) here is an error.
            | Prefer using BarView.layoutBy { LayoutSpec(x,y) } instead. [NestedContourLayouts]
            |    layoutBy(
            |    ~~~~~~~~
            |src/sample/FooView.kt:20: Error: Calling applyLayout(x,y) on a nested contour view (BarView) here is an error.
            | Prefer using BarView.layoutBy { LayoutSpec(x,y) } instead. [NestedContourLayouts]
            |    applyLayout(
            |    ~~~~~~~~~~~
            |3 errors, 0 warnings
            """.trimMargin()
        )
  }

  @Test fun `nested contour views with 'this' as the argument`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : ContourLayout(context) {
      |  private val view1 = BarView(context).let {
      |    layoutBy(
      |        x = leftTo { parent.left() },
      |        y = topTo { parent.top() }
      |    )
      |  }
      |  
      |  private val view2 = BarView(context).also {
      |    layoutBy(
      |        x = rightTo { parent.right() },
      |        y = bottomTo { parent.bottom() }
      |    )
      |  }
      |}
      |
      |class BarView(context: Context) : ContourLayout(context)              
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .expectClean()
  }

  @Test fun `nested non-contour views`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import android.view.View
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : ContourLayout(context) {
      |  private val view = View(context).apply {
      |    layoutBy(
      |        x = leftTo { parent.left() },
      |        y = bottomTo { parent.bottom() }
      |    )
      |  }
      |}
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .expectClean()
  }

  @Test fun `calling layoutBy outside a lambda`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import android.view.View
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : ContourLayout(context) {
      |  private val view1 = View(context)
      |  private val view2 = BarView(context)
      | 
      | override fun onInitializeLayout() {
      |    view1.layoutBy(
      |        x = leftTo { parent.left() },
      |        y = topTo { parent.top() }
      |    )
      |
      |    view2.layoutBy(
      |        x = rightTo { parent.right() },
      |        y = bottomTo { parent.bottom() }
      |    )
      |  }
      |}
      |
      |class BarView(context: Context) : ContourLayout(context)
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .expectClean()
  }

  @Test fun `nested contour views with complex class hierarchy`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : OnClickListener, ContourLayout(context) {
      |  private val view = BarBarView(context).apply {
      |    layoutBy(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |  }
      |}
      |
      |class BarBarView(context: Context) : OnClickListener, BarView(context)    
      |open class BarView(context: Context) : ContourLayout(context)
      |interface OnClickListener
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .expectErrorCount(1)
  }

  @Test fun `generate fix for simple cases`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : ContourLayout(context) {
      |  private val view1 = BarView(context).apply {
      |    textSize = 1f
      |    textColor = "#FFFFFF"
      |    layoutBy(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |    setBackgroundColor("#000000")
      |  }
      |}
      |
      |class BarView(context: Context) : ContourLayout(context)
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .checkFix(
            fix = null,
            after = kotlin(
                """
                |package sample
                |
                |import android.content.Context
                |import com.squareup.contour.ContourLayout
                |
                |class FooView(context: Context) : ContourLayout(context) {
                |  private val view1 = BarView(context).layoutBy {
                |    textSize = 1f
                |    textColor = "#FFFFFF"
                |    setBackgroundColor("#000000")
                |    LayoutSpec(
                |        x = matchParentX(),
                |        y = matchParentY()
                |    )
                |  }
                |}
                |
                |class BarView(context: Context) : ContourLayout(context)
                """.trimMargin()
            )
        )
  }

  @Test fun `avoid generation of fix for complex cases`() {
    val source = """
      |package sample
      |
      |import android.content.Context
      |import com.squareup.contour.ContourLayout
      |
      |class FooView(context: Context) : ContourLayout(context) {
      |  private val view1 = BarView(context).apply { param ->
      |    layoutBy(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |  }
      |  private val view2 = with(BarView(context)) {
      |    layoutBy(
      |        x = matchParentX(),
      |        y = matchParentY()
      |    )
      |  }
      |}
      |
      |class BarView(context: Context) : ContourLayout(context)
      """.trimMargin()

    lint(kotlin(source))
        .issues(NestedContourLayoutsDetector.ISSUE)
        .run()
        .checkFix(
            fix = null,
            after = kotlin(source)
        )
  }

  companion object {
    private val AXIS_SOLVERS_STUB = kotlin(
        """
        |package com.squareup.contour.solvers
        |
        |interface AxisSolver
        |interface XAxisSolver : AxisSolver
        |interface YAxisSolver : AxisSolver
        """.trimMargin()
    )
    private val CONTOUR_LAYOUT_STUB = kotlin(
        """
        |package com.squareup.contour
        |
        |import android.content.Context
        |import android.view.View
        |import android.view.ViewGroup
        |import com.squareup.contour.solvers.XAxisSolver
        |import com.squareup.contour.solvers.YAxisSolver
        |
        |open class ContourLayout(context: Context) : ViewGroup(context) {
        |  fun View.layoutBy(x: XAxisSolver, y: YAxisSolver): Unit = TODO()
        |  fun <T> T.layoutBy(addToViewGroup: Boolean, spec: T.() -> LayoutSpec): T = TODO()
        |  fun <T> T.applyLayout(x: XAxisSolver, y: YAxisSolver): T = TODO()
        |}
        """.trimMargin()
    )
    private val VIEW_STUB = java(
        """
        |package android.view;
        |
        |import android.content.Context;
        |
        |public class View {
        |  public View(Context context) {}
        |}
        """.trimMargin()
    )
  }
}
