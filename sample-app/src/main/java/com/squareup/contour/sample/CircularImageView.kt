/*
 * Copyright 2020 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
