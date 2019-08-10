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
 * Represents an [Float] on the y axis.
 */
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