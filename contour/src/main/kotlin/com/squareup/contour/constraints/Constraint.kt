package com.squareup.contour.constraints

import com.squareup.contour.IntProvider
import com.squareup.contour.LayoutContext
import com.squareup.contour.SizeMode
import com.squareup.contour.SizeMode.Exact
import com.squareup.contour.errors.CircularReferenceDetected
import com.squareup.contour.resolvers.SimpleScalarResolver.Point

internal open class Constraint {
  private var isResolving: Boolean = false
  private var layoutContext: LayoutContext? = null
  private var value: Int = Int.MIN_VALUE
  var mode: SizeMode = Exact
  var lambda: (IntProvider)? = null

  val isSet: Boolean get() = lambda != null

  fun onAttachContext(layoutContext: LayoutContext) {
    this.layoutContext = layoutContext
  }

  fun resolve(): Int {
    if (value == Int.MIN_VALUE) {
      val context =
        checkNotNull(layoutContext) { "Constraint called before LayoutContext attached" }
      val lambda = checkNotNull(lambda) { "Constraint not set" }

      try {
        if (isResolving) throw CircularReferenceDetected()

        isResolving = true
        value = lambda(context)
      } finally {
        isResolving = false
      }
    }
    return value
  }

  fun clear() {
    value = Int.MIN_VALUE
  }
}

internal class PositionConstraint(
  var point: Point = Point.Min,
  lambda: IntProvider? = null
) : Constraint() {
  init {
    this.lambda = lambda
  }
}