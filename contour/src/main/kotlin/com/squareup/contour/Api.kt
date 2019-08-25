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
import com.squareup.contour.solvers.XAxisSolver
import com.squareup.contour.solvers.YAxisSolver

interface LayoutContext {
  val parent: GeometryProvider
}

interface HasXPositionNoSize
interface HasYPositionNoSize

interface WidthOfOnlyContext : XAxisSolver, HasXPositionNoSize, WidthOfAllowedContext
interface HeightOfOnlyContext : YAxisSolver, HasYPositionNoSize, HeightOfAllowedContext

interface WidthOfAllowedContext {
  fun widthOf(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XInt
  ): XAxisSolver

  fun widthOfFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XFloat
  ): XAxisSolver
}

interface HeightOfAllowedContext {
  fun heightOf(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YInt
  ): YAxisSolver

  fun heightOfFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YFloat
  ): YAxisSolver
}

interface FromLeftContext : XAxisSolver, HasXPositionNoSize, WidthOfAllowedContext {
  fun rightTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XInt
  ): XAxisSolver

  fun rightToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XFloat
  ): XAxisSolver
}

interface FromRightContext : XAxisSolver, HasXPositionNoSize, WidthOfAllowedContext {
  fun leftTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XInt
  ): XAxisSolver

  fun leftToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> XFloat
  ): XAxisSolver
}

interface FromTopContext : YAxisSolver, HasYPositionNoSize, HeightOfAllowedContext {
  fun bottomTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YInt
  ): YAxisSolver

  fun bottomToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YFloat
  ): YAxisSolver
}

interface FromBottomContext : YAxisSolver, HasYPositionNoSize, HeightOfAllowedContext {
  fun topTo(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YInt
  ): YAxisSolver

  fun topToFloat(
    mode: SizeMode = Exact,
    provider: LayoutContext.() -> YFloat
  ): YAxisSolver
}