/*
 * Copyright 2020 Square Inc.
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
 * Represents an [Int] on the x axis that is positioning agnostic, meaning that it isn't defined in
 * relation to the edge of a view (e.g. dip values, widths, center points).
 */
@JvmInline
value class ScalarXInt(override val value: Int) : LeftRightCompatibleXInt, StartEndCompatibleXInt {
  inline operator fun plus(other: Int) = ScalarXInt(value + other)
  inline operator fun plus(other: ScalarXInt) = ScalarXInt(value + other.value)
  inline operator fun plus(other: Float) = ScalarXFloat(value + other)
  inline operator fun plus(other: ScalarXFloat) = ScalarXFloat(value + other.value)

  inline operator fun minus(other: Int) = ScalarXInt(value - other)
  inline operator fun minus(other: ScalarXInt) = ScalarXInt(value - other.value)
  inline operator fun minus(other: Float) = ScalarXFloat(value - other)
  inline operator fun minus(other: ScalarXFloat) = ScalarXFloat(value - other.value)

  inline operator fun times(other: Int) = ScalarXInt(value * other)
  inline operator fun times(other: ScalarXInt) = ScalarXInt(value * other.value)
  inline operator fun times(other: Float) = ScalarXFloat(value * other)
  inline operator fun times(other: ScalarXFloat) = ScalarXFloat(value * other.value)

  inline operator fun div(other: Int) = ScalarXInt(value / other)
  inline operator fun div(other: ScalarXInt) = ScalarXInt(value / other.value)
  inline operator fun div(other: Float) = ScalarXFloat(value / other)
  inline operator fun div(other: ScalarXFloat) = ScalarXFloat(value / other.value)

  inline operator fun compareTo(other: Int) = value.compareTo(other)
  inline operator fun compareTo(other: LeftRightCompatibleXInt) = value.compareTo(other.value)
  inline operator fun compareTo(other: Float) = value.compareTo(other)
  inline operator fun compareTo(other: LeftRightCompatibleXFloat) = value.compareTo(other.value)

  inline fun toY() = YInt(value)
  inline fun toFloat() = ScalarXFloat(value.toFloat())
}
