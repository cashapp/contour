@file:Suppress("unused")

package com.squareup.contour

internal typealias IntProvider = LayoutContext.() -> Int

interface LayoutContext {
  val parent: GeometryProvider
}

interface XPositionWithoutSize
interface YPositionWithoutSize

interface FromHorizontalCenterContext : XResolver, XPositionWithoutSize {
  fun widthOf(provider: XProvider): XResolver
}

interface FromLeftContext : XResolver, XPositionWithoutSize {
  fun rightTo(provider: XProvider): XResolver
  fun widthOf(provider: XProvider): XResolver
}

interface FromRightContext : XResolver, XPositionWithoutSize {
  fun leftTo(provider: XProvider): XResolver
  fun widthOf(provider: XProvider): XResolver
}

interface FromYPositionedContext : YResolver, YPositionWithoutSize {
  fun heightOf(provider: YProvider): YResolver
}

interface FromTopContext : YResolver, YPositionWithoutSize {
  fun bottomTo(provider: YProvider): YResolver
  fun heightOf(provider: YProvider): YResolver
}

interface FromBottomContext : YResolver, YPositionWithoutSize {
  fun topTo(provider: YProvider): YResolver
  fun heightOf(provider: YProvider): YResolver
}