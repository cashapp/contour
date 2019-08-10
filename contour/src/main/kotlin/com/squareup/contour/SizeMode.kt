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

package com.squareup.contour

import android.view.View.MeasureSpec

/**
 * Corresponds with the [MeasureSpec] modes [MeasureSpec.EXACTLY] & [MeasureSpec.AT_MOST].
 * Majority of the time [SizeMode.Exact] is what should be used. A common use-case for [SizeMode.AtMost] is if you
 * are laying out text and want the corresponding view to size itself based on the text width, but ellipsize if the
 * once the view hits the specified maximum value.
 */
enum class SizeMode(val mask: Int) {
  Exact(MeasureSpec.EXACTLY),
  AtMost(MeasureSpec.AT_MOST)
}