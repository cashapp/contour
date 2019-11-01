package com.squareup.contour.sample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.v7.widget.AppCompatImageView

class AvatarImageView(context: Context) : AppCompatImageView(context) {
  private val path = Path()
  val paint = Paint().apply {
    isAntiAlias = true
    style = Paint.Style.STROKE
    color = Color.WHITE
  }

  override fun onDraw(canvas: Canvas) {
    val r = width / 2f
    path.reset()
    path.addCircle(r, r, r, Path.Direction.CW)
    canvas.clipPath(path)
    super.onDraw(canvas)
    canvas.drawCircle(r, r, r, paint)
  }
}
