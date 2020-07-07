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