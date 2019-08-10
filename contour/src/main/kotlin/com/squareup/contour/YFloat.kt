@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour

inline class YFloat(val value: Float) {

  inline operator fun plus(other: Int) = YFloat(value + other)
  inline operator fun plus(other: YInt) = YFloat(value + other.value)
  inline operator fun plus(other: Float) = YFloat(value + other)
  inline operator fun plus(other: YFloat) = YFloat(value + other.value)

  inline operator fun minus(other: Int) = YFloat(value - other)
  inline operator fun minus(other: YInt) = YFloat(value - other.value)
  inline operator fun minus(other: Float) = YFloat(value - other)
  inline operator fun minus(other: YFloat) = YFloat(value - other.value)

  inline operator fun times(other: Int) = YFloat(value * other)
  inline operator fun times(other: YInt) = YFloat(value * other.value)
  inline operator fun times(other: Float) = YFloat(value * other)
  inline operator fun times(other: YFloat) = YFloat(value * other.value)

  inline operator fun div(other: Int) = YFloat(value / other)
  inline operator fun div(other: YInt) = YFloat(value / other.value)
  inline operator fun div(other: Float) = YFloat(value / other)
  inline operator fun div(other: YFloat) = YFloat(value / other.value)

  inline fun toX() = XFloat(value)

  companion object {
    val ZERO = YFloat(0f)
    val MIN_VALUE = YFloat(Float.MIN_VALUE)
  }
}