package com.squareup.contour.constraints

import android.view.View
import com.squareup.contour.ContourLayout
import com.squareup.contour.constraints.SizeConfigSmartLambdas.CoordinateAxis.HORIZONTAL
import com.squareup.contour.constraints.SizeConfigSmartLambdas.CoordinateAxis.VERTICAL
import com.squareup.contour.utils.children
import kotlin.math.max

internal object SizeConfigSmartLambdas {
  fun matchParent(): SizeConfigLambda = { it }
  fun wrapContent(view: ContourLayout, axis: CoordinateAxis): SizeConfigLambda = {
    view.run {
      children
          .filter { it.visibility != View.GONE }
          .map {
            when (axis) {
              VERTICAL -> max(it.bottom().value, paddingTop) + paddingBottom
              HORIZONTAL -> max(it.right().value, paddingLeft) + paddingRight
            }
          }
          .max() ?: totalPadding(axis)
    }
  }

  private fun ContourLayout.totalPadding(axis: CoordinateAxis) = when (axis) {
    VERTICAL -> paddingTop + paddingBottom
    HORIZONTAL -> paddingLeft + paddingRight
  }

  enum class CoordinateAxis {
    VERTICAL,
    HORIZONTAL
  }
}