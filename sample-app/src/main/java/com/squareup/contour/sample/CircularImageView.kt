package com.squareup.contour.sample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.widget.AppCompatImageView

class CircularImageView(context: Context) : AppCompatImageView(context) {
  private val path = Path()
  override fun onDraw(canvas: Canvas) {
    val r = width / 2f
    path.reset()
    path.addCircle(r, r, r, Path.Direction.CW)
    canvas.clipPath(path)
    super.onDraw(canvas)
  }
}
