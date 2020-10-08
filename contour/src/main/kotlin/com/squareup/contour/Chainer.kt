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

package com.squareup.contour

import android.view.View
import android.view.View.GONE
import com.squareup.contour.Chainer.Orientation.HORIZONTAL
import com.squareup.contour.Chainer.Orientation.VERTICAL
import com.squareup.contour.Chainer.Style.PACKED
import com.squareup.contour.Chainer.Style.SPREAD
import com.squareup.contour.Chainer.Style.SPREAD_INSIDE
import com.squareup.contour.ContourLayout.LayoutSpec

class Chainer(
  private val orientation: Orientation = VERTICAL,
  private val style: Style = SPREAD,
  private val elements: List<Element>,
  private val start: LayoutContainer.() -> Int,
  private val end: LayoutContainer.() -> Int
) {
  enum class Orientation {
    HORIZONTAL,
    VERTICAL
  }

  enum class Style {
    SPREAD,
    SPREAD_INSIDE,
    PACKED
  }

  class Element(
    internal val view: View,
    internal val marginStart: Int = 0,
    private val marginEnd: Int = 0
  ) {
    internal fun size(
      orientation: Orientation,
      contour: ContourLayout
    ): Int {
      if (view.visibility != GONE) {
        with(contour) {
          val viewSize = when (orientation) {
            HORIZONTAL -> view.width().value
            VERTICAL -> view.height().value
          }

          return viewSize + marginStart + marginEnd
        }
      }

      return 0
    }
  }

  fun start(
    contour: ContourLayout,
    view: View
  ): Int {
    // TODO: Potential optimization to only do most of the calculations for the first view.
    val start = with(view.layoutParams as LayoutSpec) { start() }
    val available = with(view.layoutParams as LayoutSpec) { end() } - start

    val chainedSize = elements
        .map { it.size(orientation, contour) }
        .sum()

    val surplus = available - chainedSize

    val viewIndex = elements.indexOfFirst { it.view === view }

    val elementOffset = when (style) {
      SPREAD -> {
        val numVisible = elements.count { it.view.visibility != GONE }
        val marginPerElement = surplus / (numVisible + 1)

        marginPerElement * (viewIndex + 1)
      }
      SPREAD_INSIDE -> {
        val numVisible = elements.count { it.view.visibility != GONE }
        if (numVisible == 0 || numVisible == 1) {
          0
        } else {
          val marginPerElement = surplus / (numVisible - 1)
          marginPerElement * viewIndex
        }
      }
      PACKED -> surplus / 2
    }

    return elementOffset +
        elements[viewIndex].marginStart +
        elements.subList(0, viewIndex)
            .map { it.size(orientation, contour) }
            .sum()
  }
}
