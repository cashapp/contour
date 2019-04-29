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

