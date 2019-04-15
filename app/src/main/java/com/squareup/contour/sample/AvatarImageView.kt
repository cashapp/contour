package com.squareup.contour.sample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.widget.ImageView
import com.squareup.contour.dip

class AvatarImageView(context: Context) : ImageView(context) {
    private val path = Path()
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 3f.dip
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
