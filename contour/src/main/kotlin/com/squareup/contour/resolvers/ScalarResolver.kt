package com.squareup.contour.resolvers

import com.squareup.contour.ContourLayout.LayoutSpec

internal interface ScalarResolver {
  fun min(): Int
  fun mid(): Int
  fun max(): Int
  fun range(): Int

  fun onAttach(parent: LayoutSpec)
  fun onRangeResolved(value: Int)

  fun measureSpec(): Int
  fun clear()
}