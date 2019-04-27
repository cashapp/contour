@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour

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

inline class XInt(val value: Int) {
  operator fun minus(other: Int): XInt = XInt(value - other)
  operator fun plus(other: Int): XInt = XInt(value + other)
  operator fun div(other: Int): XInt = XInt(value / other)
  operator fun times(other: Int): XInt = XInt(value * other)
  fun toY(): YInt = YInt(value)

  companion object {
    val ZERO: XInt = XInt(0)
    val MIN_VALUE: XInt = XInt(Int.MIN_VALUE)
  }
}

inline class YInt(val value: Int) {
  operator fun minus(other: Int): YInt = YInt(value - other)
  operator fun plus(other: Int): YInt = YInt(value + other)
  operator fun div(other: Int): YInt = YInt(value / other)
  operator fun times(other: Int): YInt = YInt(value * other)
  fun toX(): XInt = XInt(value)

  companion object {
    val ZERO: YInt = YInt(0)
    val NOT_SET: YInt = YInt(Int.MIN_VALUE)
  }
}