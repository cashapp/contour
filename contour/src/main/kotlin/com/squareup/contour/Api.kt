@file:Suppress("unused")

package com.squareup.contour

import com.squareup.contour.SizeMode.Exact
import com.squareup.contour.utils.XProvider
import com.squareup.contour.utils.YProvider

interface XResolver
interface YResolver

internal typealias IntProvider = LayoutContext.() -> Int

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
    provider: XProvider
  ): XResolver
}

interface HeightOfAllowedContext {
  fun heightOf(
    mode: SizeMode = Exact,
    provider: YProvider
  ): YResolver
}

interface FromLeftContext : XResolver, HasXPositionNoSize, WidthOfAllowedContext {
  fun rightTo(
    mode: SizeMode = Exact,
    provider: XProvider
  ): XResolver
}

interface FromRightContext : XResolver, HasXPositionNoSize, WidthOfAllowedContext {
  fun leftTo(
    mode: SizeMode = Exact,
    provider: XProvider
  ): XResolver
}

interface FromTopContext : YResolver, HasYPositionNoSize, HeightOfAllowedContext {
  fun bottomTo(
    mode: SizeMode = Exact,
    provider: YProvider
  ): YResolver
}

interface FromBottomContext : YResolver, HasYPositionNoSize, HeightOfAllowedContext {
  fun topTo(
    mode: SizeMode = Exact,
    provider: YProvider
  ): YResolver
}