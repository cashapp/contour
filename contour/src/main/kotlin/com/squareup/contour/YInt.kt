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
 * Represents an [Int] on the y axis.
 */
inline class YInt(val value: Int) {

  inline operator fun plus(other: Int) = YInt(value + other)
  inline operator fun plus(other: YInt) = YInt(value + other.value)
  inline operator fun plus(other: Float) = YFloat(value + other)
  inline operator fun plus(other: YFloat) = YFloat(value + other.value)

  inline operator fun minus(other: Int) = YInt(value - other)
  inline operator fun minus(other: YInt) = YInt(value - other.value)
  inline operator fun minus(other: Float) = YFloat(value - other)
  inline operator fun minus(other: YFloat) = YFloat(value - other.value)

  inline operator fun times(other: Int) = YInt(value * other)
  inline operator fun times(other: YInt) = YInt(value * other.value)
  inline operator fun times(other: Float) = YFloat(value * other)
  inline operator fun times(other: YFloat) = YFloat(value * other.value)

  inline operator fun div(other: Int) = YInt(value / other)
  inline operator fun div(other: YInt) = YInt(value / other.value)
  inline operator fun div(other: Float) = YFloat(value / other)
  inline operator fun div(other: YFloat) = YFloat(value / other.value)

  inline fun toX() = XInt(value)

  companion object {
    val ZERO = YInt(0)
    val MIN_VALUE = YInt(Int.MIN_VALUE)
  }
}