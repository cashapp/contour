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
import com.squareup.contour.LeftRightXInt
import com.squareup.contour.Geometry
import com.squareup.contour.ScalarXInt
import com.squareup.contour.StartEndXInt
import com.squareup.contour.StartEndXInt.LtrInt
import com.squareup.contour.StartEndXInt.RtlInt
import com.squareup.contour.YInt
import com.squareup.contour.constraints.SizeConfig
import com.squareup.contour.utils.toYInt

internal class ParentGeometry(
  private val widthConfig: SizeConfig,
  private val heightConfig: SizeConfig,
  private val paddingConfig: () -> Rect,
  private val isLayoutRtl: () -> Boolean
) : Geometry {
  override fun left(): LeftRightXInt = LeftRightXInt(padding().left)
  override fun right(): LeftRightXInt = LeftRightXInt(widthConfig.resolve() - padding().right)
  override fun start(): StartEndXInt =
    if (isLayoutRtl()) RtlInt(right().value) else LtrInt(left().value)
  override fun end(): StartEndXInt =
    if (isLayoutRtl()) RtlInt(left().value) else LtrInt(right().value)
  override fun width(): ScalarXInt = ScalarXInt(widthConfig.resolve())
  override fun centerX(): ScalarXInt = ScalarXInt(widthConfig.resolve() / 2)

  override fun top(): YInt = YInt.ZERO + padding().top
  override fun bottom(): YInt = heightConfig.resolve().toYInt() - padding().bottom
  override fun height(): YInt = heightConfig.resolve().toYInt()
  override fun centerY(): YInt = heightConfig.resolve().toYInt() / 2

  override fun padding(): Rect = paddingConfig()
}
