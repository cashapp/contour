/*
 * Copyright 2019 Square Inc.
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

package com.squareup.contour.wrappers

import android.view.View

internal interface HasDimensions {
  fun measure(
    w: Int,
    h: Int
  )

  val width: Int
  val height: Int
  val baseline: Int
}

internal class ViewDimensions(private val view: View) : HasDimensions {
  override fun measure(
    w: Int,
    h: Int
  ) {
    view.measure(w, h)
  }

  override val width: Int
    get() = view.measuredWidth
  override val height: Int
    get() = view.measuredHeight
  override val baseline: Int
    get() = view.baseline
}