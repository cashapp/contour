@file:Suppress("unused")

package com.squareup.contour

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.squareup.contour.ComparisonResolver.CompareBy.MaxOf
import com.squareup.contour.ComparisonResolver.CompareBy.MinOf

open class ContourLayout(
  context: Context,
  attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

  private val density = context.resources.displayMetrics.density

  val Int.dip: Int
    get() = (density * this).toInt()

  val Float.dip: Float
    get() = density * this

  inline fun Int.toXInt(): XInt = XInt(this)
  inline fun Float.toXInt(): XInt = XInt(toInt())
  inline fun Int.toYInt(): YInt = YInt(this)
  inline fun Float.toYInt(): YInt = YInt(toInt())

  private val widthConfig = SizeConfig()
  private val heightConfig = SizeConfig()
  private val geometryProvider = ParentGeometryProvider(widthConfig, heightConfig)
  private var initialized: Boolean = true

  private inline fun <T> View.withParams(block: ContourLayoutParams.() -> T): T {
    val params = layoutParams as ContourLayoutParams
    if (parent !== this@ContourLayout) {
      throw IllegalArgumentException("Referencing view outside of ViewGroup.")
    }
    return try {
      params.block()
    } catch (e: CircularReferenceDetected) {
      // Want first stacktrace element to be following line.
      // Thread.currentThread().stackTrace does not do this.
      val trace = Throwable().stackTrace
      val current = trace.getOrNull(0)
      val calledBy = trace.getOrNull(1)
      e.add(CircularReferenceDetected.TraceElement(this, current, calledBy))
      throw e
    }
  }

  fun View.left(): XInt = withParams { left() }
  fun View.top(): YInt = withParams { top() }
  fun View.right(): XInt = withParams { right() }
  fun View.bottom(): YInt = withParams { bottom() }
  fun View.centerX(): XInt = withParams { centerX() }
  fun View.centerY(): YInt = withParams { centerY() }
  fun View.width(): XInt = withParams { width() }
  fun View.height(): YInt = withParams { height() }
  fun View.preferredHeight(): YInt = withParams { preferredHeight() }

  fun topTo(provider: YProvider): FromTopContext =
    SimpleScalarResolver(
        positionConstraint(
            point = Point.Min,
            lambda = unwrapYProvider(provider)
        )
    )

  fun bottomTo(provider: YProvider): FromBottomContext =
    SimpleScalarResolver(
        positionConstraint(
            point = Point.Max,
            lambda = unwrapYProvider(provider)
        )
    )

  fun verticallyCenterTo(provider: YProvider): FromYPositionedContext =
    SimpleScalarResolver(
        positionConstraint(
            point = Point.Mid,
            lambda = unwrapYProvider(provider)
        )
    )

  fun leftTo(provider: XProvider): FromLeftContext =
    SimpleScalarResolver(
        positionConstraint(
            point = Point.Min,
            lambda = unwrapXProvider(provider)
        )
    )

  fun rightTo(provider: XProvider): FromRightContext =
    SimpleScalarResolver(
        positionConstraint(
            point = Point.Max,
            lambda = unwrapXProvider(provider)
        )
    )

  fun horizontallyCenterTo(provider: XProvider): FromHorizontalCenterContext =
    SimpleScalarResolver(
        positionConstraint(
            point = Point.Mid,
            lambda = unwrapXProvider(provider)
        )
    )

  fun minOf(
    p0: YPositionWithoutSize,
    p1: YPositionWithoutSize
  ): YResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MinOf)
  }

  fun maxOf(
    p0: YPositionWithoutSize,
    p1: YPositionWithoutSize
  ): YResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MaxOf)
  }

  fun minOf(
    p0: XPositionWithoutSize,
    p1: XPositionWithoutSize
  ): XResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MinOf)
  }

  fun maxOf(
    p0: XPositionWithoutSize,
    p1: XPositionWithoutSize
  ): XResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MaxOf)
  }

  fun View.layoutOf(
    x: XResolver,
    y: YResolver
  ) {
    x as ScalarResolver
    y as ScalarResolver
    layoutParams = ContourLayoutParams(ViewDimensions(this), x, y)
  }

  fun widthOf(config: (available: XInt) -> XInt) {
    widthConfig.lambda = unwrapXIntToXInt(config)
  }

  fun heightOf(config: (available: YInt) -> YInt) {
    heightConfig.lambda = unwrapYIntToYInt(config)
  }

  override fun addView(
    child: View?,
    index: Int,
    params: LayoutParams?
  ) {
    val recurseParams = child?.layoutParams as? ContourLayoutParams
    recurseParams?.parent = geometryProvider
    super.addView(child, index, params)
  }

  override fun requestLayout() {
    if (initialized) {
      widthConfig.clear()
      heightConfig.clear()
      for (i in 0 until childCount) {
        val child = getChildAt(i)
        (child.layoutParams as? ContourLayoutParams)?.clear()
      }
    }
    super.requestLayout()
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    widthConfig.available = MeasureSpec.getSize(widthMeasureSpec)
    heightConfig.available = MeasureSpec.getSize(heightMeasureSpec)
    setMeasuredDimension(widthConfig.resolve(), heightConfig.resolve())
  }

  override fun onLayout(
    changed: Boolean,
    l: Int,
    t: Int,
    r: Int,
    b: Int
  ) {
    for (i in 0 until childCount) {
      val child = getChildAt(i)
      val params = child.layoutParams as ContourLayoutParams
      child.measure(params.x.measureSpec(), params.y.measureSpec())
      child.layout(
          params.left().value, params.top().value, params.right().value, params.bottom().value
      )
    }
  }
}
