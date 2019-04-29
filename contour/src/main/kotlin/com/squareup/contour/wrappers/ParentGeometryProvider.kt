package com.squareup.contour.wrappers

import com.squareup.contour.GeometryProvider
import com.squareup.contour.XInt
import com.squareup.contour.YInt
import com.squareup.contour.constraints.SizeConfig
import com.squareup.contour.toXInt
import com.squareup.contour.toYInt

internal class ParentGeometryProvider(
  private val widthConfig: SizeConfig,
  private val heightConfig: SizeConfig
) : GeometryProvider {
  override fun left(): XInt = XInt.ZERO
  override fun right(): XInt = widthConfig.resolve().toXInt()
  override fun width(): XInt = widthConfig.resolve().toXInt()
  override fun width(amount: Float): XInt = (widthConfig.resolve() * amount).toXInt()
  override fun centerX(): XInt = (widthConfig.resolve() / 2).toXInt()

  override fun top(): YInt = YInt.ZERO
  override fun bottom(): YInt = heightConfig.resolve().toYInt()
  override fun height(): YInt = heightConfig.resolve().toYInt()
  override fun height(amount: Float): YInt = (heightConfig.resolve() * amount).toYInt()
  override fun centerY(): YInt = heightConfig.resolve().toYInt() / 2
}