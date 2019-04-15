@file:Suppress("unused")

package com.squareup.contour

import android.view.View
import android.view.ViewGroup

typealias XConfig = HasParentGeometry.() -> XInt
typealias YConfig = HasParentGeometry.() -> YInt

inline fun <T : View> T.alsoAddTo(viewGroup: ViewGroup): T {
    viewGroup.addView(this)
    return this
}

interface HasParentGeometry {
    val parent: ContourLayout.ParentGeometryProvider
}

interface FromHorizontalCenterContext : XProvider {
    fun widthOf(config: XConfig) : XProvider
}

interface FromLeftContext : XProvider {
    fun rightTo(config: XConfig) : XProvider
    fun widthOf(config: XConfig) : XProvider
}

interface FromRightContext : XProvider {
    fun leftTo(config: XConfig) : XProvider
    fun widthOf(config: XConfig) : XProvider
}

interface FromYPositionedContext : YProvider {
    fun heightOf(config: YConfig) : YProvider
}

interface FromTopContext : YProvider {
    fun bottomTo(config: YConfig) : YProvider
    fun heightOf(config: YConfig) : YProvider
}

interface FromBottomContext : YProvider {
    fun topTo(config: YConfig) : YProvider
    fun heightOf(config: YConfig) : YProvider
}

fun topTo(config: YConfig): FromTopContext {
    val providers = SimpleScalarProvider()
    providers.c0.apply {
        edge = Point.Min
        configuration = config
    }
    return providers
}

fun bottomTo(config: YConfig): FromBottomContext {
    val providers = SimpleScalarProvider()
    providers.c0.apply {
        edge = Point.Max
        configuration = config
    }
    return providers
}

fun verticallyCenterTo(config: YConfig): FromYPositionedContext {
    val providers = SimpleScalarProvider()
    providers.c0.apply {
        edge = Point.Mid
        configuration = config
    }
    return providers
}

fun leftTo(config: XConfig): FromLeftContext {
    val providers = SimpleScalarProvider()
    providers.c0.apply {
        edge = Point.Min
        configuration = config
    }
    return providers
}

fun rightTo(config: XConfig): FromRightContext {
    val providers = SimpleScalarProvider()
    providers.c0.apply {
        edge = Point.Max
        configuration = config
    }
    return providers
}

fun horizontallyCenterTo(config: XConfig): FromHorizontalCenterContext {
    val providers = SimpleScalarProvider()
    providers.c0.apply {
        edge = Point.Mid
        configuration = config
    }
    return providers
}

fun View.layoutOf(
    x: XProvider,
    y: YProvider
) {
    x as ScalarProvider
    y as ScalarProvider
    layoutParams = ContourLayoutParams(ViewDimensions(this), x, y)
}