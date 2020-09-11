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

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.StateListAnimator
import android.view.View
import android.view.View.SCALE_X
import android.view.View.SCALE_Y
import android.view.animation.AccelerateDecelerateInterpolator

/** Plays a subtle push animation when [view] is pressed. */
class PushOnPressAnimator(private val view: View) : StateListAnimator() {
  init {
    addState(
        intArrayOf(android.R.attr.state_pressed),
        createAnimator(toScale = 0.95f)
    )
    addState(
        intArrayOf(-android.R.attr.state_pressed),
        createAnimator(toScale = 1f)
    )
  }

  private fun createAnimator(toScale: Float): Animator {
    val scaleX = PropertyValuesHolder.ofFloat(SCALE_X, toScale)
    val scaleY = PropertyValuesHolder.ofFloat(SCALE_Y, toScale)
    return ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).apply {
      duration = 80
      interpolator = AccelerateDecelerateInterpolator()
    }
  }
}