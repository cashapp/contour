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

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.Gravity.CENTER_HORIZONTAL
import android.widget.TextView
import com.squareup.contour.Chainer
import com.squareup.contour.Chainer.Style
import com.squareup.contour.ContourLayout

@SuppressLint("SetTextI18n")
class ChainPacked(context: Context) : ContourLayout(context) {
  private val view1 = TextView(context).apply {
    gravity = CENTER_HORIZONTAL
    text = "View 1"
  }

  private val view2 = TextView(context).apply {
    gravity = CENTER_HORIZONTAL
    text = "View 2"
  }

  private val view3 = TextView(context).apply {
    gravity = CENTER_HORIZONTAL
    text = "View 3"
  }

  private val view4 = TextView(context).apply {
    gravity = CENTER_HORIZONTAL
    text = "View 4"
  }

  private val view5 = TextView(context).apply {
    gravity = CENTER_HORIZONTAL
    text = "View 5"
  }

  private val view6 = TextView(context).apply {
    gravity = CENTER_HORIZONTAL
    text = "View 6"
  }

  init {
    val chainer = Chainer(
        orientation = Chainer.Orientation.VERTICAL,
        style = Style.PACKED,
        elements = listOf(
            Chainer.Element(view1),
            Chainer.Element(view2),
            Chainer.Element(view3),
            Chainer.Element(view4),
            Chainer.Element(view5),
            Chainer.Element(view6)
        ),
        start = { parent.top().value },
        end = { parent.bottom().value }
    )

    view1.layoutBy(
        matchParentX(),
        topTo { chainer.start(this@ChainPacked, view1).toYInt() }
    )

    view2.layoutBy(
        matchParentX(),
        topTo { chainer.start(this@ChainPacked, view2).toYInt() }
    )

    view3.layoutBy(
        matchParentX(),
        topTo { chainer.start(this@ChainPacked, view3).toYInt() }
    )

    view4.layoutBy(
        matchParentX(),
        topTo { chainer.start(this@ChainPacked, view4).toYInt() }
    )

    view5.layoutBy(
        matchParentX(),
        topTo { chainer.start(this@ChainPacked, view5).toYInt() }
    )

    view6.layoutBy(
        matchParentX(),
        topTo { chainer.start(this@ChainPacked, view6).toYInt() }
    )
  }
}
