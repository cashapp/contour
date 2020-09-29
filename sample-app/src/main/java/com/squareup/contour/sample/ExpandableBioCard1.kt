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
import android.graphics.Color.DKGRAY
import android.graphics.Color.WHITE
import android.graphics.drawable.PaintDrawable
import android.text.TextUtils.TruncateAt.END
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView.ScaleType.CENTER_CROP
import android.widget.TextView
import androidx.core.view.updatePadding
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.squareup.contour.ContourLayout
import com.squareup.picasso.Picasso

@SuppressLint("SetTextI18n")
class ExpandableBioCard1(context: Context, attributeSet: AttributeSet? = null) : ContourLayout(context) {
  private val avatar = CircularImageView(context).apply {
    scaleType = CENTER_CROP

    //image will appear on emulator and devices
    if (!isInEditMode)
    Picasso.get()
            .load("https://upload.wikimedia.org/wikipedia/en/9/92/BenSisko.jpg")
            .into(this)
  }

  private val bio = TextView(context).apply {
    textSize = 14f
    text = "The Bajorans who have lived with us on this station, who have worked with us for months, " +
        "who helped us move this station to protect the wormhole, who joined us to explore the " +
        "Gamma Quadrant, who have begun to build the future of Bajor with us. These people know " +
        "that we are neither the enemy nor the devil. We don't always agree. We have some damn " +
        "good fights, in fact. But we always come away from them with a little better " +
        "understanding and appreciation of the other."
    ellipsize = END
    maxLines = 2
    setLineSpacing(0f, 1.33f)
    setTextColor(WHITE)
  }

  init {
    background = PaintDrawable(DKGRAY).also { it.setCornerRadius(32f) }
    clipToOutline = true
    elevation = 20f.dip
    stateListAnimator = PushOnPressAnimator(this)
    updatePadding(left = 16.dip, right = 16.dip)

    contourHeightOf { maxOf(avatar.bottom() + 24.ydip, bio.bottom() + 16.ydip) }

    avatar.layoutBy(
        x = leftTo { parent.left() }.widthOf { 60.xdip },
        y = topTo { parent.top() + 24.ydip }.heightOf { 60.ydip }
    )
    bio.layoutBy(
        x = leftTo { avatar.right() + 16.xdip }.rightTo { parent.right() },
        y = topTo {
          when {
            isSelected -> parent.top() + 16.ydip
            else -> avatar.centerY() - bio.preferredHeight() / 2
          }
        }
    )

    val collapsedLines = bio.maxLines
    setOnClickListener {
      TransitionManager.beginDelayedTransition(parent as ViewGroup, AutoTransition()
          .setInterpolator(OvershootInterpolator())
          .setDuration(400)
      )

      isSelected = !isSelected
      bio.maxLines = if (isSelected) Int.MAX_VALUE else collapsedLines
    }
  }
}