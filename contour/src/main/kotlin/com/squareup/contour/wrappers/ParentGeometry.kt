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

package com.squareup.contour.wrappers

import android.graphics.Rect
import com.squareup.contour.Geometry
import com.squareup.contour.XInt
import com.squareup.contour.YInt
import com.squareup.contour.constraints.SizeConfig
import com.squareup.contour.utils.toXInt
import com.squareup.contour.utils.toYInt

internal class ParentGeometry(
  private val widthConfig: SizeConfig,
  private val heightConfig: SizeConfig,
  private val paddingConfig: () -> Rect
) : Geometry {
  override fun left(): XInt = XInt.ZERO + padding().left
  override fun right(): XInt = widthConfig.resolve().toXInt() - padding().right
  override fun width(): XInt = widthConfig.resolve().toXInt()
  override fun centerX(): XInt = widthConfig.resolve().toXInt() / 2

  override fun top(): YInt = YInt.ZERO + padding().top
  override fun bottom(): YInt = heightConfig.resolve().toYInt() - padding().bottom
  override fun height(): YInt = heightConfig.resolve().toYInt()
  override fun centerY(): YInt = heightConfig.resolve().toYInt() / 2

  override fun padding(): Rect = paddingConfig()
}
