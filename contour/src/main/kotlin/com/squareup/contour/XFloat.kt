@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour

inline class XFloat(val value: Float) {

  inline operator fun plus(other: Int) = XFloat(value + other)
  inline operator fun plus(other: XInt) = XFloat(value + other.value)
  inline operator fun plus(other: Float) = XFloat(value + other)
  inline operator fun plus(other: XFloat) = XFloat(value + other.value)

  inline operator fun minus(other: Int) = XFloat(value - other)
  inline operator fun minus(other: XInt) = XFloat(value - other.value)
  inline operator fun minus(other: Float) = XFloat(value - other)
  inline operator fun minus(other: XFloat) = XFloat(value - other.value)

  inline operator fun times(other: Int) = XFloat(value * other)
  inline operator fun times(other: XInt) = XFloat(value * other.value)
  inline operator fun times(other: Float) = XFloat(value * other)
  inline operator fun times(other: XFloat) = XFloat(value * other.value)

  inline operator fun div(other: Int) = XFloat(value / other)
  inline operator fun div(other: XInt) = XFloat(value / other.value)
  inline operator fun div(other: Float) = XFloat(value / other)
  inline operator fun div(other: XFloat) = XFloat(value / other.value)

  inline fun toY() = YFloat(value)

  companion object {
    val ZERO = XFloat(0f)
    val MIN_VALUE = XFloat(Float.MIN_VALUE)
  }
}