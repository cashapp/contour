package com.squareup.contour.wrappers

import android.view.View

internal interface HasDimensions {
  fun measure(
    w: Int,
    h: Int
  )

  val width: Int
  val height: Int
  val baseline: Int
}

internal class ViewDimensions(private val view: View) : HasDimensions {
  override fun measure(
    w: Int,
    h: Int
  ) {
    view.measure(w, h)
  }

  override val width: Int
    get() = view.measuredWidth
  override val height: Int
    get() = view.measuredHeight
  override val baseline: Int
    get() = view.baseline
}