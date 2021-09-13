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

import com.squareup.contour.StartEndXInt.LtrInt
import com.squareup.contour.StartEndXInt.RtlInt

/**
 * Represents a [Float] on the x axis that is compatible with relative positioning, meaning that it
 * can be used in relation to the start or end edge of a view.
 */
interface StartEndCompatibleXFloat : XFloat

/**
 * Represents a [Float] on the x axis that with a relative position, meaning that it is defined in
 * relation to the start or end edge of a view.
 */
interface StartEndXFloat : StartEndCompatibleXFloat {
  operator fun plus(other: Int) : StartEndXFloat
  operator fun plus(other: ScalarXInt) : StartEndXFloat
  operator fun plus(other: Float) : StartEndXFloat
  operator fun plus(other: ScalarXFloat) : StartEndXFloat

  operator fun minus(other: Int) : StartEndXFloat
  operator fun minus(other: ScalarXInt) : StartEndXFloat
  operator fun minus(other: Float) : StartEndXFloat
  operator fun minus(other: ScalarXFloat) : StartEndXFloat

  @JvmInline
  value class LtrFloat(override val value: Float) : StartEndXFloat {
    override operator fun plus(other: Int) = LtrFloat(value + other)
    override operator fun plus(other: ScalarXInt) = LtrFloat(value + other.value)
    override operator fun plus(other: Float) = LtrFloat(value + other)
    override operator fun plus(other: ScalarXFloat) = LtrFloat(value + other.value)

    override operator fun minus(other: Int) = LtrFloat(value - other)
    override operator fun minus(other: ScalarXInt) = LtrFloat(value - other.value)
    override operator fun minus(other: Float) = LtrFloat(value - other)
    override operator fun minus(other: ScalarXFloat) = LtrFloat(value - other.value)

    inline fun toInt() = LtrInt(value.toInt())
  }

  @JvmInline
  value class RtlFloat(override val value: Float) : StartEndXFloat {
    override operator fun plus(other: Int) = RtlFloat(value - other)
    override operator fun plus(other: ScalarXInt) = RtlFloat(value - other.value)
    override operator fun plus(other: Float) = RtlFloat(value - other)
    override operator fun plus(other: ScalarXFloat) = RtlFloat(value - other.value)

    override operator fun minus(other: Int) = RtlFloat(value + other)
    override operator fun minus(other: ScalarXInt) = RtlFloat(value + other.value)
    override operator fun minus(other: Float) = RtlFloat(value + other)
    override operator fun minus(other: ScalarXFloat) = RtlFloat(value + other.value)

    inline fun toInt() = RtlInt(value.toInt())
  }
}
