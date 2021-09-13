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

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Color.DKGRAY
import android.graphics.Typeface.BOLD
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.PaintDrawable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import android.widget.TextView
import com.squareup.contour.ContourLayout
import com.squareup.picasso.Picasso

@SuppressLint("SetTextI18n")
class ExpandableBioCard2(context: Context, attrs: AttributeSet? = null) : ContourLayout(context, attrs) {
  private val imageView = ImageView(context).apply {
    scaleType = CENTER_CROP
    setBackgroundColor(Color.GRAY)

    if (!isInEditMode) {
      Picasso.get()
              .load("https://i.imgur.com/ajdangY.jpg")
              .placeholder(ColorDrawable(Color.parseColor("#3d3d3d")))
              .into(this)
    }
  }

  private val title = TextView(context).apply {
    textSize = 16f
    text = "Nicolas Cage"
    setTextColor(White)
    setTypeface(typeface, BOLD)
  }

  private val bio = TextView(context).apply {
    textSize = 16f
    text = "Nicolas Kim Coppola, known professionally as Nicolas Cage, is an American actor" +
        "and filmmaker. Cage has been nominated for numerous major cinematic awards, and" +
        "won an Academy Award, a Golden Globe, and Screen Actors Guild Award for his performance" +
        "in Leaving Las Vegas."
    setLineSpacing(0f, 1.33f)
  }

  init {
    background = PaintDrawable(DKGRAY)
    clipToOutline = true
    elevation = 20f.dip
    stateListAnimator = PushOnPressAnimator(this)
    registerBackPressListener()
    toggleCornerRadius(show = true)

    contourHeightOf { available ->
      if (isSelected) available else title.bottom() + 20.ydip
    }

    bio.layoutBy(
        x = matchParentX(marginStart = 20.dip, marginEnd = 20.dip),
        y = topTo { title.bottom() + 20.ydip }
    )
    title.layoutBy(
        x = matchParentX(marginStart = 20.dip, marginEnd = 20.dip),
        y = topTo { imageView.bottom() + 20.ydip }
    )
    imageView.layoutBy(
        x = matchParentX(),
        y = topTo { parent.top() }.heightOf { 200.ydip }
    )
  }

  override fun getBackground() = super.getBackground() as PaintDrawable

  override fun setSelected(selected: Boolean) {
    if (isLaidOut && selected == this.isSelected) return
    super.setSelected(selected)
    toggleCornerRadius(show = !selected)
  }

  private fun toggleCornerRadius(show: Boolean) {
    // No idea why, but 0.0f causes the view to hide on animation end. Using 0.01 instead.
    val fromRadius = if (show) 0.01f else 12f.dip
    val toRadius = if (show) 12f.dip else 0.01f

    if (isLaidOut) {
      ObjectAnimator.ofFloat(fromRadius, toRadius)
          .apply { addUpdateListener { background.setCornerRadius(it.animatedValue as Float) } }
          .setDuration(200)
          .start()
    } else {
      background.setCornerRadius(toRadius)
    }
  }

  private fun registerBackPressListener() {
    isFocusableInTouchMode = true
    requestFocus()
    setOnKeyListener { _, keyCode, event ->
      if (isSelected && keyCode == KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
        performClick()
      } else {
        false
      }
    }
  }
}