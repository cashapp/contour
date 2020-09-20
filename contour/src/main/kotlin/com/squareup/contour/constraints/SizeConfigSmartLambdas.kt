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

package com.squareup.contour.constraints

import android.view.View
import com.squareup.contour.ContourLayout
import com.squareup.contour.constraints.SizeConfigSmartLambdas.CoordinateAxis.HORIZONTAL
import com.squareup.contour.constraints.SizeConfigSmartLambdas.CoordinateAxis.VERTICAL
import com.squareup.contour.utils.children
import kotlin.math.max

internal object SizeConfigSmartLambdas {
  fun matchParent(): SizeConfigLambda = { it }
  fun wrapContent(view: ContourLayout, axis: CoordinateAxis): SizeConfigLambda = {
    view.run {
      children
          .filter { it.visibility != View.GONE }
          .map {
            when (axis) {
              VERTICAL -> max(it.bottom().value, paddingTop) + paddingBottom
              HORIZONTAL -> max(it.right().value, paddingLeft) + paddingRight
            }
          }
          .max() ?: totalPadding(axis)
    }
  }

  private fun ContourLayout.totalPadding(axis: CoordinateAxis) = when (axis) {
    VERTICAL -> paddingTop + paddingBottom
    HORIZONTAL -> paddingLeft + paddingRight
  }

  enum class CoordinateAxis {
    VERTICAL,
    HORIZONTAL
  }
}