@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour

inline class XInt(val value: Int) {

  inline operator fun plus(other: Int): XInt =
    XInt(value + other)
  inline operator fun plus(other: XInt): XInt =
    XInt(value + other.value)

  inline operator fun minus(other: Int): XInt =
    XInt(value - other)
  inline operator fun minus(other: XInt): XInt =
    XInt(value - other.value)

  inline operator fun times(other: Int): XInt =
    XInt(value * other)
  inline operator fun times(other: XInt): XInt =
    XInt(value * other.value)

  inline operator fun div(other: Int): XInt =
    XInt(value / other)
  inline operator fun div(other: XInt): XInt =
    XInt(value / other.value)

  inline fun toY(): YInt = YInt(value)

  companion object {
    val ZERO: XInt = XInt(0)
    val MIN_VALUE: XInt = XInt(Int.MIN_VALUE)
  }
}