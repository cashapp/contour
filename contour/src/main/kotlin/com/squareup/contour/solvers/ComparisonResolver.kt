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

package com.squareup.contour.solvers

import android.view.View
import com.squareup.contour.constraints.Constraint
import com.squareup.contour.ContourLayout.LayoutSpec
import com.squareup.contour.solvers.ComparisonResolver.CompareBy.MaxOf
import com.squareup.contour.solvers.ComparisonResolver.CompareBy.MinOf

internal class ComparisonResolver(
    private val p0: AxisSolver,
    private val p1: AxisSolver,
    private val compareBy: CompareBy
) : XAxisSolver, YAxisSolver {

  internal enum class CompareBy {
    MaxOf,
    MinOf
  }

  private val size = Constraint()
  private var found: AxisSolver? = null
  private lateinit var parent: LayoutSpec
  private var range: Int = Int.MIN_VALUE

  private fun findWinner(): AxisSolver {
    val found = found
    if (found != null) return found
    else {
      when (compareBy) {
        MaxOf -> {
          val max = if (p0.min() >= p1.min()) p0 else p1
          this.found = max
          return max
        }
        MinOf -> {
          val min = if (p0.min() <= p1.min()) p0 else p1
          this.found = min
          return min
        }
      }

    }
  }

  override fun min(): Int = findWinner().min()
  override fun mid(): Int = findWinner().mid()
  override fun baseline(): Int = findWinner().baseline()
  override fun max(): Int = findWinner().max()

  override fun range(): Int {
    if (range == Int.MIN_VALUE) {
      parent.measureSelf()
    }
    return range
  }

  override fun onAttach(parent: LayoutSpec) {
    this.parent = parent
    p0.onAttach(parent)
    p1.onAttach(parent)
  }

  override fun onRangeResolved(range: Int, baselineRange: Int) {
    this.range = range
    p0.onRangeResolved(range, baselineRange)
    p1.onRangeResolved(range, baselineRange)
  }

  override fun measureSpec(): Int {
    return if (size.isSet) {
      View.MeasureSpec.makeMeasureSpec(size.resolve(), View.MeasureSpec.EXACTLY)
    } else {
      0
    }
  }

  override fun clear() {
    p0.clear()
    p1.clear()
    found = null
    range = Int.MIN_VALUE
  }
}
