/*
 * Copyright 2019 Square Inc.
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

package com.squareup.contour.constraints

import com.squareup.contour.LayoutContainer
import com.squareup.contour.SizeMode
import com.squareup.contour.SizeMode.Exact
import com.squareup.contour.errors.CircularReferenceDetected
import com.squareup.contour.solvers.SimpleAxisSolver.Point
import com.squareup.contour.solvers.SimpleAxisSolver.Point.End
import com.squareup.contour.solvers.SimpleAxisSolver.Point.Max
import com.squareup.contour.solvers.SimpleAxisSolver.Point.Min
import com.squareup.contour.solvers.SimpleAxisSolver.Point.Start

internal open class Constraint {
  private var isResolving: Boolean = false
  private var container: LayoutContainer? = null
  private var value: Int = Int.MIN_VALUE
  var mode: SizeMode = Exact
  var lambda: (LayoutContainer.() -> Int)? = null

  val isSet: Boolean get() = lambda != null

  fun onAttachContext(container: LayoutContainer) {
    this.container = container
  }

  fun resolve(): Int {
    if (value == Int.MIN_VALUE) {
      val context =
        checkNotNull(container) { "Constraint called before LayoutContainer attached" }
      val lambda = checkNotNull(lambda) { "Constraint not set" }

      try {
        if (isResolving) throw CircularReferenceDetected()

        isResolving = true
        value = lambda(context)
      } finally {
        isResolving = false
      }
    }
    return value
  }

  fun clear() {
    value = Int.MIN_VALUE
  }
}

internal class PositionConstraint(
  var point: Point = Min,
  private val isLayoutRtl: () -> Boolean,
  lambda: (LayoutContainer.() -> Int)? = null
) : Constraint() {
  init {
    this.lambda = lambda
  }

  fun isRelativeMin(): Boolean =
    when (point) {
      Min -> true
      Start -> !isLayoutRtl()
      End -> isLayoutRtl()
      else -> false
    }

  fun isRelativeMax(): Boolean =
    when (point) {
      Max -> true
      Start -> isLayoutRtl()
      End -> !isLayoutRtl()
      else -> false
    }
}
