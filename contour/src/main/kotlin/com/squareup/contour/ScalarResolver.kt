package com.squareup.contour

import android.view.View
import kotlin.math.abs

interface YResolver
interface XResolver

internal interface ScalarResolver {
  fun min(): Int
  fun mid(): Int
  fun max(): Int
  fun range(): Int

  fun onAttach(parent: ContourLayoutParams)
  fun onRangeResolved(value: Int)

  fun measureSpec(): Int
  fun clear()
}

internal class SimpleScalarResolver(private val p0: PositionConstraint) : ScalarResolver,
    XResolver, FromLeftContext, FromRightContext, FromHorizontalCenterContext,
    YResolver, FromTopContext, FromBottomContext, FromYPositionedContext {

  private lateinit var parent: ContourLayoutParams

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

  override fun onAttach(parent: ContourLayoutParams) {
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
      View.MeasureSpec.makeMeasureSpec(abs(p0.resolve() - p1.resolve()), View.MeasureSpec.EXACTLY)
    } else if (size.isSet) {
      View.MeasureSpec.makeMeasureSpec(size.resolve(), View.MeasureSpec.EXACTLY)
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

  override fun topTo(provider: YProvider): YResolver {
    p1.point = Point.Min
    p1.lambda = unwrapYProvider(provider)
    return this
  }

  override fun bottomTo(provider: YProvider): YResolver {
    p1.point = Point.Mid
    p1.lambda = unwrapYProvider(provider)
    return this
  }

  override fun heightOf(provider: YProvider): YResolver {
    size.lambda = unwrapYProvider(provider)
    return this
  }

  override fun leftTo(provider: XProvider): XResolver {
    p1.point = Point.Min
    p1.lambda = unwrapXProvider(provider)
    return this
  }

  override fun rightTo(provider: XProvider): XResolver {
    p1.point = Point.Max
    p1.lambda = unwrapXProvider(provider)
    return this
  }

  override fun widthOf(provider: XProvider): XResolver {
    size.lambda = unwrapXProvider(provider)
    return this
  }
}