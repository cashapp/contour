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
  fun View.width(): XInt = withParams { width() }
  fun View.height(): YInt = withParams { height() }
  fun View.preferredHeight(): YInt = withParams { preferredHeight() }

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

  fun verticallyCenterTo(provider: YProvider): FromYPositionedContext =
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

  fun horizontallyCenterTo(provider: XProvider): FromHorizontalCenterContext =
    SimpleScalarResolver(
        PositionConstraint(
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
}