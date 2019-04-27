@file:Suppress("unused")

package com.squareup.contour

interface GeometryProvider {
  fun left(): XInt
  fun right(): XInt
  fun width(): XInt
  fun width(amount: Float): XInt
  fun centerX(): XInt

  fun top(): YInt
  fun bottom(): YInt
  fun height(): YInt
  fun height(amount: Float): YInt
  fun centerY(): YInt
}

class ParentGeometryProvider(
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