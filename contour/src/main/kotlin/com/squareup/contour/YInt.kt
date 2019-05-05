@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour

inline class YInt(val value: Int) {

  inline operator fun plus(other: Int): YInt =
    YInt(value + other)
  inline operator fun plus(other: YInt): YInt =
    YInt(value + other.value)

  inline operator fun minus(other: Int): YInt =
    YInt(value - other)
  inline operator fun minus(other: YInt): YInt =
    YInt(value - other.value)

  inline operator fun times(other: Int): YInt =
    YInt(value * other)
  inline operator fun times(other: YInt): YInt =
    YInt(value * other.value)

  inline operator fun div(other: Int): YInt =
    YInt(value / other)
  inline operator fun div(other: YInt): YInt =
    YInt(value / other.value)

  inline fun toX(): XInt = XInt(value)

  companion object {
    val ZERO: YInt = YInt(0)
    val NOT_SET: YInt = YInt(Int.MIN_VALUE)
  }
}