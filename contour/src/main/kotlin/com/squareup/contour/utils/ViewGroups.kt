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

package com.squareup.contour.utils

import android.view.View
import android.view.ViewGroup

/**
 * Returns a [MutableIterator] over the views in this view group.
 * @note @carranca: This was pulled from ktx 1.3.0 so the whole lib didn't have to get pulled in.
 * */
operator fun ViewGroup.iterator() = object : MutableIterator<View> {
  private var index = 0
  override fun hasNext() = index < childCount
  override fun next() = getChildAt(index++) ?: throw IndexOutOfBoundsException()
  override fun remove() = removeViewAt(--index)
}

/**
 * Returns a [Sequence] over the child views in this view group.
 * @note @carranca: This was pulled from ktx 1.3.0 so the whole lib didn't have to get pulled in.
 * */
val ViewGroup.children: Sequence<View>
  get() = object : Sequence<View> {
    override fun iterator() = this@children.iterator()
  }