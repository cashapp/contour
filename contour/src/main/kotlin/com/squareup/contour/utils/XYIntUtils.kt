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

package com.squareup.contour.utils

import com.squareup.contour.LayoutContainer
import com.squareup.contour.XFloat
import com.squareup.contour.XInt
import com.squareup.contour.YFloat
import com.squareup.contour.YInt

internal inline fun unwrapXIntToXIntLambda(
  crossinline lambda: (XInt) -> XInt
): (Int) -> Int =
  { lambda(it.toXInt()).value }

internal inline fun unwrapYIntToYIntLambda(
  crossinline lambda: (YInt) -> YInt
): (Int) -> Int =
  { lambda(it.toYInt()).value }

internal inline fun unwrapXIntLambda(
  crossinline lambda: LayoutContainer.() -> XInt
): LayoutContainer.() -> Int =
  { lambda().value }

internal inline fun unwrapXFloatLambda(
  crossinline lambda: LayoutContainer.() -> XFloat
): LayoutContainer.() -> Int =
  { lambda().value.toInt() }

internal inline fun unwrapYIntLambda(
  crossinline lambda: LayoutContainer.() -> YInt
): LayoutContainer.() -> Int =
  { lambda().value }

internal inline fun unwrapYFloatLambda(
  crossinline lambda: LayoutContainer.() -> YFloat
): LayoutContainer.() -> Int =
  { lambda().value.toInt() }

internal inline fun Int.toXInt() = XInt(this)
internal inline fun Int.toYInt() = YInt(this)