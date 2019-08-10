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

@file:Suppress("unused")

package com.squareup.contour

import com.squareup.contour.SizeMode.Exact
import com.squareup.contour.resolvers.ScalarResolver

interface XResolver : ScalarResolver
interface YResolver : ScalarResolver

interface LayoutContext {
  val parent: GeometryProvider
}

interface HasXPositionNoSize
interface HasYPositionNoSize

interface WidthOfOnlyContext : XResolver, HasXPositionNoSize, WidthOfAllowedContext
interface HeightOfOnlyContext : YResolver, HasYPositionNoSize, HeightOfAllowedContext

interface WidthOfAllowedContext {
  fun widthOf(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XInt
  ): XResolver

  fun widthOfFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XFloat
  ): XResolver
}

interface HeightOfAllowedContext {
  fun heightOf(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YInt
  ): YResolver

  fun heightOfFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YFloat
  ): YResolver
}

interface FromLeftContext : XResolver, HasXPositionNoSize, WidthOfAllowedContext {
  fun rightTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XInt
  ): XResolver

  fun rightToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XFloat
  ): XResolver
}

interface FromRightContext : XResolver, HasXPositionNoSize, WidthOfAllowedContext {
  fun leftTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XInt
  ): XResolver

  fun leftToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XFloat
  ): XResolver
}

interface FromTopContext : YResolver, HasYPositionNoSize, HeightOfAllowedContext {
  fun bottomTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YInt
  ): YResolver

  fun bottomToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YFloat
  ): YResolver
}

interface FromBottomContext : YResolver, HasYPositionNoSize, HeightOfAllowedContext {
  fun topTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YInt
  ): YResolver

  fun topToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YFloat
  ): YResolver
}