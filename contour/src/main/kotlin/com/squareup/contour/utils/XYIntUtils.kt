@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour.utils

import com.squareup.contour.LayoutContext
import com.squareup.contour.XFloat
import com.squareup.contour.XInt
import com.squareup.contour.YFloat
import com.squareup.contour.YInt

internal inline fun unwrapXIntToXIntLambda(
  crossinline lambda: (XInt) -> XInt
): (Int) -> Int =
  { lambda(it.toXInt()).value }

internal inline fun unwrapYIntToYIntLambda(
  crossinline lambda: (YInt) -> YInt
): (Int) -> Int =
  { lambda(it.toYInt()).value }

internal inline fun unwrapXIntLambda(
  crossinline lambda: LayoutContext.() -> XInt
): LayoutContext.() -> Int =
  { lambda().value }

internal inline fun unwrapXFloatLambda(
  crossinline lambda: LayoutContext.() -> XFloat
): LayoutContext.() -> Int =
  { lambda().value.toInt() }

internal inline fun unwrapYIntLambda(
  crossinline lambda: LayoutContext.() -> YInt
): LayoutContext.() -> Int =
  { lambda().value }

internal inline fun unwrapYFloatLambda(
  crossinline lambda: LayoutContext.() -> YFloat
): LayoutContext.() -> Int =
  { lambda().value.toInt() }

internal inline fun Int.toXInt() = XInt(this)
internal inline fun Int.toYInt() = YInt(this)