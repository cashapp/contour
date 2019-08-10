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

@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.squareup.contour

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.squareup.contour.constraints.SizeConfig
import com.squareup.contour.errors.CircularReferenceDetected
import com.squareup.contour.resolvers.ComparisonResolver
import com.squareup.contour.resolvers.ComparisonResolver.CompareBy.MaxOf
import com.squareup.contour.resolvers.ComparisonResolver.CompareBy.MinOf
import com.squareup.contour.resolvers.ScalarResolver
import com.squareup.contour.resolvers.SimpleScalarResolver
import com.squareup.contour.resolvers.SimpleScalarResolver.Point.Baseline
import com.squareup.contour.resolvers.SimpleScalarResolver.Point.Max
import com.squareup.contour.resolvers.SimpleScalarResolver.Point.Mid
import com.squareup.contour.resolvers.SimpleScalarResolver.Point.Min
import com.squareup.contour.utils.toXInt
import com.squareup.contour.utils.toYInt
import com.squareup.contour.utils.unwrapXIntLambda
import com.squareup.contour.utils.unwrapXIntToXIntLambda
import com.squareup.contour.utils.unwrapYIntLambda
import com.squareup.contour.utils.unwrapYIntToYIntLambda
import com.squareup.contour.wrappers.HasDimensions
import com.squareup.contour.wrappers.ParentGeometryProvider
import com.squareup.contour.wrappers.ViewDimensions
import kotlin.math.max
import kotlin.math.min

private const val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT

open class ContourLayout(
  context: Context,
  attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

  private val density = context.resources.displayMetrics.density

  private val widthConfig = SizeConfig()
  private val heightConfig = SizeConfig()
  private val geometryProvider = ParentGeometryProvider(widthConfig, heightConfig)
  private var constructed: Boolean = true
  private var initialized: Boolean = false

  open fun onInitializeLayout() {}

  private fun initializeLayout() {
    if (!initialized) {
      onInitializeLayout()
      initialized = true
    }
  }

  override fun requestLayout() {
    if (constructed) {
      invalidateAll()
    }
    super.requestLayout()
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    initializeLayout()
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    initializeLayout()

    // Clear caches to force layout recalculations
    invalidateAll()

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
      val params = child.layoutParams as LayoutSpec
      child.measure(params.x.measureSpec(), params.y.measureSpec())
      child.layout(
          params.left().value, params.top().value,
          params.right().value, params.bottom().value
      )
    }
  }

  private fun invalidateAll() {
    widthConfig.clear()
    heightConfig.clear()
    for (i in 0 until childCount) {
      val child = getChildAt(i)
      (child.layoutParams as? LayoutSpec)?.clear()
    }
  }

  private inline fun View.spec(): LayoutSpec {
    if (parent !== this@ContourLayout) {
      throw IllegalArgumentException("Referencing view outside of ViewGroup.")
    }
    return layoutParams as LayoutSpec
  }

  private inline fun <T> View.handleCrd(block: () -> T): T {
    return try {
      block()
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

  // API

  val Int.dip: Int get() = (density * this).toInt()
  val Int.xdip: XInt get() = XInt((density * this).toInt())
  val Int.ydip: YInt get() = YInt((density * this).toInt())

  val Float.dip: Float get() = density * this
  val Float.xdip: XFloat get() = XFloat(density * this)
  val Float.ydip: YFloat get() = YFloat(density * this)

  inline fun Int.toXInt(): XInt = XInt(this)
  inline fun Int.toYInt(): YInt = YInt(this)

  @Deprecated(
      "Views should be configured by overriding onInitializeLayout() in your ContourLayout " +
          "subclass and calling view.applyLayout() on the corresponding view."
  )
  fun <T : View> T.contourOf(
    addToViewGroup: Boolean = true,
    config: T.() -> LayoutSpec
  ): T {
    val viewGroup = this@ContourLayout
    val spec = config()
    spec.dimen = ViewDimensions(this)
    spec.parent = viewGroup.geometryProvider
    layoutParams = spec
    if (addToViewGroup) {
      viewGroup.addView(this)
    }
    return this
  }

  fun contourWidthOf(config: (available: XInt) -> XInt) {
    widthConfig.lambda = unwrapXIntToXIntLambda(config)
  }

  fun contourHeightOf(config: (available: YInt) -> YInt) {
    heightConfig.lambda = unwrapYIntToYIntLambda(config)
  }

  fun View.applyLayout(
    x: XResolver,
    y: YResolver,
    addToViewGroup: Boolean = true
  ) {
    val viewGroup = this@ContourLayout
    val spec = LayoutSpec(x, y)
    spec.dimen = ViewDimensions(this)
    spec.parent = viewGroup.geometryProvider
    layoutParams = spec
    if (addToViewGroup && parent == null) {
      viewGroup.addView(this)
    }
  }

  @Suppress("MemberVisibilityCanBePrivate")
  fun View.updateLayout(
    x: XResolver = spec().x,
    y: YResolver = spec().y
  ) {
    val viewGroup = this@ContourLayout
    val spec = LayoutSpec(x, y)
    spec.dimen = ViewDimensions(this)
    spec.parent = viewGroup.geometryProvider
    layoutParams = spec
  }

  @Deprecated("Use updateLayout", ReplaceWith("updateLayout(x, y)"))
  fun View.updateLayoutSpec(
    x: XResolver = spec().x,
    y: YResolver = spec().y
  ) {
    updateLayout(x, y)
  }

  fun View.left(): XInt = handleCrd { spec().left() }
  fun View.top(): YInt = handleCrd { spec().top() }
  fun View.right(): XInt = handleCrd { spec().right() }
  fun View.bottom(): YInt = handleCrd { spec().bottom() }
  fun View.centerX(): XInt = handleCrd { spec().centerX() }
  fun View.centerY(): YInt = handleCrd { spec().centerY() }
  fun View.baseline(): YInt = handleCrd { spec().baseline() }
  fun View.width(): XInt = handleCrd { spec().width() }
  fun View.height(): YInt = handleCrd { spec().height() }
  fun View.preferredWidth(): XInt = handleCrd { spec().preferredWidth() }
  fun View.preferredHeight(): YInt = handleCrd { spec().preferredHeight() }

  fun baselineTo(provider: LayoutContext.() -> YInt): HeightOfOnlyContext =
    SimpleScalarResolver(
        point = Baseline,
        lambda = unwrapYIntLambda(provider)
    )

  fun topTo(provider: LayoutContext.() -> YInt): FromTopContext =
    SimpleScalarResolver(
        point = Min,
        lambda = unwrapYIntLambda(provider)
    )

  fun bottomTo(provider: LayoutContext.() -> YInt): FromBottomContext =
    SimpleScalarResolver(
        point = Max,
        lambda = unwrapYIntLambda(provider)
    )

  fun centerVerticallyTo(provider: LayoutContext.() -> YInt): HeightOfOnlyContext =
    SimpleScalarResolver(
        point = Mid,
        lambda = unwrapYIntLambda(provider)
    )

  fun leftTo(provider: LayoutContext.() -> XInt): FromLeftContext =
    SimpleScalarResolver(
        point = Min,
        lambda = unwrapXIntLambda(provider)
    )

  fun rightTo(provider: LayoutContext.() -> XInt): FromRightContext =
    SimpleScalarResolver(
        point = Max,
        lambda = unwrapXIntLambda(provider)
    )

  fun centerHorizontallyTo(provider: LayoutContext.() -> XInt): WidthOfOnlyContext =
    SimpleScalarResolver(
        point = Mid,
        lambda = unwrapXIntLambda(provider)
    )

  fun minOf(
    a: XInt,
    b: XInt
  ): XInt = min(a.value, b.value).toXInt()

  fun minOf(
    a: YInt,
    b: YInt
  ): YInt = min(a.value, b.value).toYInt()

  fun maxOf(
    a: XInt,
    b: XInt
  ): XInt = max(a.value, b.value).toXInt()

  fun maxOf(
    a: YInt,
    b: YInt
  ): YInt = max(a.value, b.value).toYInt()

  fun minOf(
    p0: HasYPositionNoSize,
    p1: HasYPositionNoSize
  ): YResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MinOf)
  }

  fun maxOf(
    p0: HasYPositionNoSize,
    p1: HasYPositionNoSize
  ): YResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MaxOf)
  }

  fun minOf(
    p0: HasXPositionNoSize,
    p1: HasXPositionNoSize
  ): XResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MinOf)
  }

  fun maxOf(
    p0: HasXPositionNoSize,
    p1: HasXPositionNoSize
  ): XResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return ComparisonResolver(p0, p1, MaxOf)
  }

  class LayoutSpec(
    internal val x: XResolver,
    internal val y: YResolver
  ) : ViewGroup.LayoutParams(WRAP, WRAP), LayoutContext {

    override lateinit var parent: GeometryProvider
    internal lateinit var dimen: HasDimensions

    init {
      this.x.onAttach(this)
      this.y.onAttach(this)
    }

    internal fun left(): XInt = x.min().toXInt()
    internal fun right(): XInt = x.max().toXInt()
    internal fun centerX(): XInt = x.mid().toXInt()
    internal fun top(): YInt = y.min().toYInt()
    internal fun bottom(): YInt = y.max().toYInt()
    internal fun centerY(): YInt = y.mid().toYInt()
    internal fun baseline(): YInt = y.baseline().toYInt()
    internal fun width(): XInt = x.range().toXInt()
    internal fun height(): YInt = y.range().toYInt()

    internal fun preferredWidth(): XInt {
      dimen.measure(0, y.measureSpec())
      return dimen.width.toXInt()
    }

    internal fun preferredHeight(): YInt {
      dimen.measure(x.measureSpec(), 0)
      return dimen.height.toYInt()
    }

    internal fun measureSelf() {
      dimen.measure(x.measureSpec(), y.measureSpec())
      x.onRangeResolved(dimen.width, 0)
      y.onRangeResolved(dimen.height, dimen.baseline)
    }

    internal fun clear() {
      x.clear()
      y.clear()
    }
  }
}
