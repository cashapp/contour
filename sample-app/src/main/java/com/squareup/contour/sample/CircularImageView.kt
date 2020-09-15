package com.squareup.contour.sample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader.TileMode
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Transformation

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

class CircleTransformation : Transformation {
  override fun transform(source: Bitmap): Bitmap? {
    val size = Math.min(source.width, source.height)
    val x = (source.width - size) / 2
    val y = (source.height - size) / 2
    val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
    if (squaredBitmap != source) {
      source.recycle()
    }
    val bitmap = Bitmap.createBitmap(size, size, source.config)
    val canvas = Canvas(bitmap)
    val paint = Paint()
    val shader =
      BitmapShader(squaredBitmap, TileMode.CLAMP, TileMode.CLAMP)
    paint.shader = shader
    paint.isAntiAlias = true
    val r = size / 2f
    canvas.drawCircle(r, r, r, paint)
    squaredBitmap.recycle()
    return bitmap
  }

  override fun key(): String? {
    return "circle"
  }
}