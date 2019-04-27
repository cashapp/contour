package com.squareup.contour

import android.view.View
import android.view.ViewGroup

interface HasDimensions {
  fun measure(
    w: Int,
    h: Int
  )

  val width: Int
  val height: Int
}

class ViewDimensions(private val view: View) : HasDimensions {
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
}

private const val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT

class ContourLayoutParams(
  private val dimen: HasDimensions,
  internal val x: ScalarResolver,
  internal val y: ScalarResolver
) : ViewGroup.LayoutParams(WRAP, WRAP), LayoutContext {

  override lateinit var parent: GeometryProvider

  init {
    x.onAttach(this)
    y.onAttach(this)
  }

  fun left(): XInt = x.min().toXInt()
  fun right(): XInt = x.max().toXInt()
  fun centerX(): XInt = x.mid().toXInt()
  fun top(): YInt = y.min().toYInt()
  fun bottom(): YInt = y.max().toYInt()
  fun centerY(): YInt = y.mid().toYInt()
  fun width(): XInt = x.range().toXInt()
  fun height(): YInt = y.range().toYInt()

  fun preferredHeight(): YInt {
    dimen.measure(x.measureSpec(), 0)
    return dimen.height.toYInt()
  }

  internal fun measureSelf() {
    dimen.measure(x.measureSpec(), y.measureSpec())
    x.onRangeResolved(dimen.width)
    y.onRangeResolved(dimen.height)
  }

  internal fun clear() {
    x.clear()
    y.clear()
  }
}