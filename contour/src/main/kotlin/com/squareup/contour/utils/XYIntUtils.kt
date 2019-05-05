@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour.utils

import com.squareup.contour.IntProvider
import com.squareup.contour.LayoutContext
import com.squareup.contour.XInt
import com.squareup.contour.YInt

internal inline fun unwrapXIntToXInt(crossinline lambda: (XInt) -> XInt): (Int) -> Int =
  { lambda(it.toXInt()).value }

internal inline fun unwrapYIntToYInt(crossinline lambda: (YInt) -> YInt): (Int) -> Int =
  { lambda(it.toYInt()).value }


internal typealias XProvider = LayoutContext.() -> XInt
internal typealias YProvider = LayoutContext.() -> YInt

internal inline fun unwrapXProvider(crossinline lambda: XProvider): IntProvider = { lambda().value }
internal inline fun unwrapYProvider(crossinline lambda: YProvider): IntProvider = { lambda().value }

internal inline fun Int.toXInt(): XInt = XInt(this)
internal inline fun Float.toXInt(): XInt = XInt(toInt())
internal inline fun Int.toYInt(): YInt = YInt(this)
internal inline fun Float.toYInt(): YInt = YInt(toInt())