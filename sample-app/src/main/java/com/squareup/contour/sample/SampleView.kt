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
import android.graphics.Color
import android.graphics.Color.WHITE
import android.graphics.Typeface
import android.graphics.Typeface.NORMAL
import android.util.AttributeSet
import android.view.Gravity.CENTER_VERTICAL
import android.view.WindowInsets
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.core.view.updatePadding
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.squareup.contour.ContourLayout

@SuppressLint("SetTextI18n")
class SampleView(context: Context, attrs: AttributeSet? = null) : ContourLayout(context, attrs) {
  private val toolbar = TextView(context).apply {
    gravity = CENTER_VERTICAL
    letterSpacing = 0.05f
    text = "Contour"
    textSize = 18f
    typeface = Typeface.create("sans-serif-medium", NORMAL)
    setTextColor(WHITE)
    updatePadding(left = 26.dip)
  }

  private val card1 = ExpandableBioCard1(context)
  private val card2 = ExpandableBioCard2(context)

  init {
    setBackgroundColor(Color.parseColor("#303030"))
    contourHeightMatchParent()

    toolbar.layoutBy(
        x = leftTo { parent.left() },
        y = topTo { parent.top() + 4.ydip }.heightOf { 56.ydip }
    )
    card1.layoutBy(
        x = matchParentX(marginLeft = 24.dip, marginRight = 24.dip),
        y = topTo { toolbar.bottom() + 8.ydip }
    )

    val xPadding = { if (card2.isSelected) 0.xdip else 24.xdip }
    card2.layoutBy(
        x = leftTo { parent.left() + xPadding() }.rightTo { parent.right() - xPadding() },
        y = topTo {
          if (card2.isSelected) parent.top() else card1.bottom() + 24.ydip
        }.heightOf {
          if (card2.isSelected) parent.height() else card2.preferredHeight()
        }
    )

    card2.setOnClickListener {
      TransitionManager.beginDelayedTransition(this, ChangeBounds()
          .setInterpolator(OvershootInterpolator(1f))
          .setDuration(400)
      )

      card2.isSelected = !card2.isSelected
      requestLayout()
    }
  }

  override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
    updatePadding(top = insets.systemWindowInsetTop)
    return super.onApplyWindowInsets(insets)
  }
}
