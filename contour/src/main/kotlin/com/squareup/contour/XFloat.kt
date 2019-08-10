/*
 * Copyright 2019 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")

package com.squareup.contour

/**
 * Represents an [Float] on the x axis.
 */
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