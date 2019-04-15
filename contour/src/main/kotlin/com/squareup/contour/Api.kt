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

interface FromHorizontalCenterContext : XResolver {
    fun widthOf(provider: XProvider): XResolver
}

interface FromLeftContext : XResolver {
    fun rightTo(provider: XProvider): XResolver
    fun widthOf(provider: XProvider): XResolver
}

interface FromRightContext : XResolver {
    fun leftTo(provider: XProvider): XResolver
    fun widthOf(provider: XProvider): XResolver
}

interface FromYPositionedContext : YResolver {
    fun heightOf(provider: YProvider): YResolver
}

interface FromTopContext : YResolver {
    fun bottomTo(provider: YProvider): YResolver
    fun heightOf(provider: YProvider): YResolver
}

interface FromBottomContext : YResolver {
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

fun View.layoutOf(
    x: XResolver,
    y: YResolver
) {
    x as ScalarResolver
    y as ScalarResolver
    layoutParams = ContourLayoutParams(ViewDimensions(this), x, y)
}