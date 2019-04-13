@file:Suppress("unused")

package com.squareup.contour

import android.view.View
import android.view.ViewGroup


interface XProvider
interface YProvider

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

class XProviders : XProvider, FromLeftContext,
    FromRightContext, FromHorizontalCenterContext {

    internal var l: XConfig? = null
    internal var r: XConfig? = null
    internal var w: XConfig? = null
    internal var cX: XConfig? = null

    override fun leftTo(config: XConfig): XProvider {
        l = config
        return this
    }

    override fun rightTo(config: XConfig): XProvider {
        r = config
        return this
    }

    override fun widthOf(config: XConfig): XProvider {
        w = config
        return this
    }
}

class YProviders : YProvider, FromTopContext,
    FromBottomContext, FromYPositionedContext {

    internal var t: YConfig? = null
    internal var b: YConfig? = null
    internal var cY: YConfig? = null
    internal var h: YConfig? = null
    internal var group: YGroup? = null

    override fun bottomTo(config: YConfig): YProvider {
        b = config
        return this
    }

    override fun heightOf(config: YConfig): YProvider {
        h = config
        return this
    }

    override fun topTo(config: YConfig): YProvider {
        t = config
        return this
    }
}

fun topTo(config: YConfig): FromTopContext {
    val providers = YProviders()
    providers.topTo(config)
    return providers
}

fun bottomTo(config: YConfig): FromBottomContext {
    val providers = YProviders()
    providers.bottomTo(config)
    return providers
}

fun verticallyCenterTo(config: YConfig): FromYPositionedContext {
    val providers = YProviders()
    providers.cY = config
    return providers
}

fun leftTo(config: XConfig): FromLeftContext {
    val providers = XProviders()
    providers.leftTo(config)
    return providers
}

fun rightTo(config: XConfig): FromRightContext {
    val providers = XProviders()
    providers.rightTo(config)
    return providers
}

fun horizontallyCenterTo(config: XConfig): FromHorizontalCenterContext {
    val providers = XProviders()
    providers.cX = config
    return providers
}

fun addToGroup(provider: () -> YGroup): FromYPositionedContext {
    val providers = YProviders()
    providers.group = provider.invoke()
    return providers
}

fun View.layoutOf(
    x: XProvider,
    y: YProvider
) {
    x as XProviders
    y as YProviders
    layoutParams = ContourLayoutParams(ViewDimensions(this)).apply {
        l.configuration = x.l
        r.configuration = x.r
        cX.configuration = x.cX
        w.configuration = x.w

        t.configuration = y.t
        b.configuration = y.b
        cY.configuration = y.cY
        h.configuration = y.h

        yGroup = y.group
        y.group?.add(this)
    }
}