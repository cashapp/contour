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