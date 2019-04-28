package com.squareup.contour

import android.view.View.MeasureSpec

enum class SizeMode(val mask: Int) {
  Exact(MeasureSpec.EXACTLY),
  AtMost(MeasureSpec.AT_MOST)
}