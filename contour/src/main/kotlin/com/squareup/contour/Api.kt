@file:Suppress("unused")

package com.squareup.contour

import android.view.View

typealias IntProvider = LayoutContext.() -> Int
typealias XProvider = LayoutContext.() -> XInt
typealias YProvider = LayoutContext.() -> YInt

inline fun unwrapXProvider(crossinline lambda: XProvider): IntProvider = { lambda().value }
inline fun unwrapYProvider(crossinline lambda: YProvider): IntProvider = { lambda().value }

interface LayoutContext : HasParentGeometry
interface HasParentGeometry {
    val parent: GeometryProvider
}

interface XPositionWithoutSize
interface YPositionWithoutSize

interface FromHorizontalCenterContext : XResolver, XPositionWithoutSize {
    fun widthOf(provider: XProvider): XResolver
}

interface FromLeftContext : XResolver, XPositionWithoutSize {
    fun rightTo(provider: XProvider): XResolver
    fun widthOf(provider: XProvider): XResolver
}

interface FromRightContext : XResolver, XPositionWithoutSize {
    fun leftTo(provider: XProvider): XResolver
    fun widthOf(provider: XProvider): XResolver
}

interface FromYPositionedContext : YResolver, YPositionWithoutSize {
    fun heightOf(provider: YProvider): YResolver
}

interface FromTopContext : YResolver, YPositionWithoutSize {
    fun bottomTo(provider: YProvider): YResolver
    fun heightOf(provider: YProvider): YResolver
}

interface FromBottomContext : YResolver, YPositionWithoutSize {
    fun topTo(provider: YProvider): YResolver
    fun heightOf(provider: YProvider): YResolver
}

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

fun maxOf(
    p0: YPositionWithoutSize,
    p1: YPositionWithoutSize
) : YResolver {
    p0 as ScalarResolver
    p1 as ScalarResolver
    return MaxOfResolver(p0, p1)
}

fun View.layoutOf(
    x: XResolver,
    y: YResolver
) {
    x as ScalarResolver
    y as ScalarResolver
    layoutParams = ContourLayoutParams(ViewDimensions(this), x, y)
}