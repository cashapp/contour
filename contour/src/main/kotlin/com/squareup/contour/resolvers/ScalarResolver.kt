package com.squareup.contour.resolvers

import com.squareup.contour.ContourLayoutParams

internal interface ScalarResolver {
  fun min(): Int
  fun mid(): Int
  fun max(): Int
  fun range(): Int

  fun onAttach(parent: ContourLayoutParams)
  fun onRangeResolved(value: Int)

  fun measureSpec(): Int
  fun clear()
}