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

import com.squareup.contour.StartEndXFloat.LtrFloat
import com.squareup.contour.StartEndXFloat.RtlFloat

/**
 * Represents an [Int] on the x axis that is compatible with relative positioning, meaning that it
 * can be used in relation to the start or end edge of a view.
 */
interface StartEndCompatibleXInt : XInt

/**
 * Represents an [Int] on the x axis that with a relative position, meaning that it is defined in
 * relation to the start or end edge of a view.
 */
interface StartEndXInt : StartEndCompatibleXInt {
  operator fun plus(other: Int) : StartEndXInt
  operator fun plus(other: ScalarXInt) : StartEndXInt
  operator fun plus(other: Float) : StartEndXFloat
  operator fun plus(other: ScalarXFloat) : StartEndXFloat

  operator fun minus(other: Int) : StartEndXInt
  operator fun minus(other: ScalarXInt) : StartEndXInt
  operator fun minus(other: Float) : StartEndXFloat
  operator fun minus(other: ScalarXFloat) : StartEndXFloat

  @JvmInline
  value class LtrInt(override val value: Int) : StartEndXInt {
    override operator fun plus(other: Int) = LtrInt(value + other)
    override operator fun plus(other: ScalarXInt) = LtrInt(value + other.value)
    override operator fun plus(other: Float) = LtrFloat(value + other)
    override operator fun plus(other: ScalarXFloat) = LtrFloat(value + other.value)

    override operator fun minus(other: Int) = LtrInt(value - other)
    override operator fun minus(other: ScalarXInt) = LtrInt(value - other.value)
    override operator fun minus(other: Float) = LtrFloat(value - other)
    override operator fun minus(other: ScalarXFloat) = LtrFloat(value - other.value)

    inline fun toFloat() = LtrFloat(value.toFloat())
  }

  @JvmInline
  value class RtlInt(override val value: Int) : StartEndXInt {
    override operator fun plus(other: Int) = RtlInt(value - other)
    override operator fun plus(other: ScalarXInt) = RtlInt(value - other.value)
    override operator fun plus(other: Float) = RtlFloat(value - other)
    override operator fun plus(other: ScalarXFloat) = RtlFloat(value - other.value)

    override operator fun minus(other: Int) = RtlInt(value + other)
    override operator fun minus(other: ScalarXInt) = RtlInt(value + other.value)
    override operator fun minus(other: Float) = RtlFloat(value + other)
    override operator fun minus(other: ScalarXFloat) = RtlFloat(value + other.value)

    inline fun toFloat() = RtlFloat(value.toFloat())
  }
}
