package com.squareup.contour.resolvers

import com.squareup.contour.ContourLayout.LayoutSpec

interface ScalarResolver {
  fun min(): Int
  fun mid(): Int
  fun baseline(): Int
  fun max(): Int
  fun range(): Int

  fun onAttach(parent: LayoutSpec)
  fun onRangeResolved(range: Int, baselineRange: Int)

  fun measureSpec(): Int
  fun clear()
}