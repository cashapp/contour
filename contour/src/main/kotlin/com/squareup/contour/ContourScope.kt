package com.squareup.contour

import android.view.View
import com.squareup.contour.ContourLayout.LayoutSpec
import com.squareup.contour.constraints.PositionConstraint
import com.squareup.contour.errors.CircularReferenceDetected
import com.squareup.contour.resolvers.ComparisonResolver
import com.squareup.contour.resolvers.ComparisonResolver.CompareBy.MaxOf
import com.squareup.contour.resolvers.ComparisonResolver.CompareBy.MinOf
import com.squareup.contour.resolvers.ScalarResolver
import com.squareup.contour.resolvers.SimpleScalarResolver
import com.squareup.contour.resolvers.SimpleScalarResolver.Point
import com.squareup.contour.utils.XProvider
import com.squareup.contour.utils.YProvider
import com.squareup.contour.utils.toXInt
import com.squareup.contour.utils.toYInt
import com.squareup.contour.utils.unwrapXProvider
import com.squareup.contour.utils.unwrapYProvider

interface ContourScope {

  private inline fun <T> View.withParams(block: LayoutSpec.() -> T): T {
    val params = layoutParams as LayoutSpec
    if (parent !== this@ContourScope) {
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
  fun View.baseline(): YInt = withParams { centerY() }
  fun View.width(): XInt = withParams { width() }
  fun View.height(): YInt = withParams { height() }
  fun View.preferredHeight(): YInt = withParams { preferredHeight() }

  fun baselineTo(provider: YProvider): HeightOfOnlyContext =
    SimpleScalarResolver(
        PositionConstraint(
            point = Point.Baseline,
            lambda = unwrapYProvider(provider)
        )
    )

  fun topTo(provider: YProvider): FromTopContext =
    SimpleScalarResolver(
        PositionConstraint(
            point = Point.Min,
            lambda = unwrapYProvider(provider)
        )
    )

  fun bottomTo(provider: YProvider): FromBottomContext =
    SimpleScalarResolver(
        PositionConstraint(
            point = Point.Max,
            lambda = unwrapYProvider(provider)
        )
    )

  fun centerVerticallyTo(provider: YProvider): HeightOfOnlyContext =
    SimpleScalarResolver(
        PositionConstraint(
            point = Point.Mid,
            lambda = unwrapYProvider(provider)
        )
    )

  fun leftTo(provider: XProvider): FromLeftContext =
    SimpleScalarResolver(
        PositionConstraint(
            point = Point.Min,
            lambda = unwrapXProvider(provider)
        )
    )

  fun rightTo(provider: XProvider): FromRightContext =
    SimpleScalarResolver(
        PositionConstraint(
            point = Point.Max,
            lambda = unwrapXProvider(provider)
        )
    )

  fun centerHorizontallyTo(provider: XProvider): WidthOfOnlyContext =
    SimpleScalarResolver(
        PositionConstraint(
            point = Point.Mid,
            lambda = unwrapXProvider(provider)
        )
    )

  fun minOf(a: XInt, b: XInt): XInt = Math.min(a.value, b.value).toXInt()
  fun minOf(a: YInt, b: YInt): YInt = Math.min(a.value, b.value).toYInt()

  fun maxOf(a: XInt, b: XInt): XInt = Math.max(a.value, b.value).toXInt()
  fun maxOf(a: YInt, b: YInt): YInt = Math.max(a.value, b.value).toYInt()

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
}