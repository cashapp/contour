package com.squareup.contour.utils

import android.content.Context
import android.view.View

class FakeTextView(
  context: Context,
  private val text: String,
  private val textSize: Int,
  private val baseline: Int
) : View(context) {
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
    val widthMode = MeasureSpec.getMode(widthMeasureSpec)

    val availableHeight = MeasureSpec.getSize(heightMeasureSpec)
    val heightMode = MeasureSpec.getMode(heightMeasureSpec)

    setMeasuredDimension(
        if (widthMode == MeasureSpec.EXACTLY) availableWidth
        else text.length * textSize,
        if (heightMode == MeasureSpec.EXACTLY) availableHeight
        else textSize
    )
  }

  override fun getBaseline(): Int = baseline
}
