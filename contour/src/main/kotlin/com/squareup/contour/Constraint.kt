package com.squareup.contour


open class Constraint {
    private var isResolving: Boolean = false
    private var layoutContext: LayoutContext? = null
    private var value: Int = Int.MIN_VALUE
    var lambda: (IntProvider)? = null

    val isSet: Boolean get() = lambda != null

    fun onAttachContext(layoutContext: LayoutContext) {
        this.layoutContext = layoutContext
    }

    fun resolve(): Int {
        if (value == Int.MIN_VALUE) {
            val context = checkNotNull(layoutContext) { "Constraint called before LayoutContext attached" }
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

class PositionConstraint : Constraint() {
    var point: Point = Point.Min
}

fun positionConstraint(point: Point, lambda: IntProvider): PositionConstraint =
    PositionConstraint().apply {
        this.point = point
        this.lambda = lambda
    }