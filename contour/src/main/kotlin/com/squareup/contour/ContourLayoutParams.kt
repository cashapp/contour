package com.squareup.contour

import android.view.ViewGroup
import com.squareup.contour.resolvers.ScalarResolver
import com.squareup.contour.wrappers.HasDimensions

private const val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT

internal class ContourLayoutParams(
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