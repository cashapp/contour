@file:Suppress("unused")

package com.squareup.contour

import com.squareup.contour.SizeMode.Exact

interface YResolver
interface XResolver

internal typealias IntProvider = LayoutContext.() -> Int

interface LayoutContext {
  val parent: GeometryProvider
}

interface XPositionWithoutSize
interface YPositionWithoutSize

interface FromHorizontalCenterContext : XResolver, XPositionWithoutSize, WidthOfAllowedContext
interface FromYPositionedContext : YResolver, HeightOfAllowedContext

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

interface FromLeftContext : XResolver, XPositionWithoutSize, WidthOfAllowedContext {
  fun rightTo(
    mode: SizeMode = Exact,
    provider: XProvider
  ): XResolver
}

interface FromRightContext : XResolver, XPositionWithoutSize, WidthOfAllowedContext {
  fun leftTo(
    mode: SizeMode = Exact,
    provider: XProvider
  ): XResolver
}

interface FromTopContext : YResolver, YPositionWithoutSize, HeightOfAllowedContext {
  fun bottomTo(
    mode: SizeMode = Exact,
    provider: YProvider
  ): YResolver
}

interface FromBottomContext : YResolver, YPositionWithoutSize, HeightOfAllowedContext {
  fun topTo(
    mode: SizeMode = Exact,
    provider: YProvider
  ): YResolver
}