package com.squareup.contour.resolvers

import android.view.View
import com.squareup.contour.ContourLayout.LayoutSpec
import com.squareup.contour.FromBottomContext
import com.squareup.contour.FromHorizontalCenterContext
import com.squareup.contour.FromLeftContext
import com.squareup.contour.FromRightContext
import com.squareup.contour.FromTopContext
import com.squareup.contour.FromYPositionedContext
import com.squareup.contour.SizeMode
import com.squareup.contour.XResolver
import com.squareup.contour.YResolver
import com.squareup.contour.constraints.Constraint
import com.squareup.contour.constraints.PositionConstraint
import com.squareup.contour.utils.XProvider
import com.squareup.contour.utils.YProvider
import com.squareup.contour.utils.unwrapXProvider
import com.squareup.contour.utils.unwrapYProvider

import kotlin.math.abs

internal class SimpleScalarResolver(private val p0: PositionConstraint) : ScalarResolver,
    XResolver, FromLeftContext, FromRightContext, FromHorizontalCenterContext,
    YResolver, FromTopContext, FromBottomContext, FromYPositionedContext {

  internal enum class Point {
    Min,
    Mid,
    Max
  }

  private lateinit var parent: LayoutSpec

  private val p1 = PositionConstraint()
  private val size = Constraint()

  private var min = Int.MIN_VALUE
  private var mid = Int.MIN_VALUE
  private var max = Int.MIN_VALUE
  private var range = Int.MIN_VALUE

  override fun min(): Int {
    if (min == Int.MIN_VALUE) {
      if (p0.point == Point.Min) {
        min = p0.resolve()
      } else {
        parent.measureSelf()
        resolveAxis()
      }
    }
    return min
  }

  override fun mid(): Int {
    if (mid == Int.MIN_VALUE) {
      if (p0.point == Point.Mid) {
        mid = p0.resolve()
      } else {
        parent.measureSelf()
        resolveAxis()
      }
    }
    return mid
  }

  override fun max(): Int {
    if (max == Int.MIN_VALUE) {
      if (p0.point == Point.Max) {
        max = p0.resolve()
      } else {
        parent.measureSelf()
        resolveAxis()
      }
    }
    return max
  }

  override fun range(): Int {
    if (range == Int.MIN_VALUE) {
      parent.measureSelf()
    }
    return range
  }

  private fun resolveAxis() {
    check(range != Int.MIN_VALUE)

    val hV = range / 2
    when (p0.point) {
      Point.Min -> {
        min = p0.resolve()
        mid = min + hV
        max = min + range
      }
      Point.Mid -> {
        mid = p0.resolve()
        min = mid - hV
        max = mid + hV
      }
      Point.Max -> {
        max = p0.resolve()
        mid = max - hV
        min = max - range
      }
    }
  }

  override fun onAttach(parent: LayoutSpec) {
    this.parent = parent
    p0.onAttachContext(parent)
    p1.onAttachContext(parent)
    size.onAttachContext(parent)
  }

  override fun onRangeResolved(value: Int) {
    range = value
  }

  override fun measureSpec(): Int {
    return if (p1.isSet) {
      View.MeasureSpec.makeMeasureSpec(abs(p0.resolve() - p1.resolve()), p1.mode.mask)
    } else if (size.isSet) {
      View.MeasureSpec.makeMeasureSpec(size.resolve(), size.mode.mask)
    } else {
      0
    }
  }

  override fun clear() {
    min = Int.MIN_VALUE
    mid = Int.MIN_VALUE
    max = Int.MIN_VALUE
    range = Int.MIN_VALUE
    p0.clear()
    p1.clear()
    size.clear()
  }

  override fun topTo(mode: SizeMode, provider: YProvider): YResolver {
    p1.point = Point.Min
    p1.mode = mode
    p1.lambda = unwrapYProvider(provider)
    return this
  }

  override fun bottomTo(mode: SizeMode, provider: YProvider): YResolver {
    p1.point = Point.Mid
    p1.mode = mode
    p1.lambda = unwrapYProvider(provider)
    return this
  }

  override fun heightOf(mode: SizeMode, provider: YProvider): YResolver {
    size.mode = mode
    size.lambda = unwrapYProvider(provider)
    return this
  }

  override fun leftTo(mode: SizeMode, provider: XProvider): XResolver {
    p1.point = Point.Min
    p1.mode = mode
    p1.lambda = unwrapXProvider(provider)
    return this
  }

  override fun rightTo(mode: SizeMode, provider: XProvider): XResolver {
    p1.point = Point.Max
    p1.mode = mode
    p1.lambda = unwrapXProvider(provider)
    return this
  }

  override fun widthOf(mode: SizeMode, provider: XProvider): XResolver {
    size.mode = mode
    size.lambda = unwrapXProvider(provider)
    return this
  }
}