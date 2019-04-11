package com.squareup.contour

import android.view.View
import android.view.ViewGroup

interface HasDimensions {
    fun measure(w: Int, h: Int)
    val width: Int
    val height: Int
}

class ViewDimensions(private val view: View): HasDimensions {
    override fun measure(w: Int, h: Int) {
        view.measure(w, h)
    }
    override val width: Int
        get() = view.measuredWidth
    override val height: Int
        get() = view.measuredHeight
}


class ContourLayoutParams(private val dimen: HasDimensions) : ViewGroup.LayoutParams(0, 0), HasParentGeometry {

    override lateinit var parent: ContourLayout.ParentGeometryProvider

    internal var yGroup: YGroup? = null

    internal val l = Constraint(this)
    internal val r = Constraint(this)
    internal val t = Constraint(this)
    internal val b = Constraint(this)

    internal val w = Constraint(this)
    internal val h = Constraint(this)

    internal val cX = Constraint(this)
    internal val cY = Constraint(this)

    fun left(): XInt = resolveX(l)
    fun right(): XInt = resolveX(r)
    fun top(): YInt = resolveY(t)
    fun bottom(): YInt = resolveY(b)

    fun centerX(): XInt = resolveX(cX)
    fun centerY(): YInt = resolveY(cY)

    fun width(): XInt = resolveRange(w).toXInt()
    fun height(): YInt = resolveRange(h).toYInt()

    fun preferredHeight(): YInt {
        dimen.measure(widthSpec(), 0)
        return dimen.height.toYInt()
    }

    private fun resolveX(constraint: Constraint): XInt {
        if (!constraint.resolve()) {
            resolveAxis(l, cX, r, w)
        }
        return constraint.value.toXInt()
    }

    private fun resolveY(constraint: Constraint): YInt {
        val yGroup = yGroup
        if (yGroup != null) {
            yGroup.resolve()
        } else if (!constraint.resolve()) {
            resolveAxis(t, cY, b, h)
        }
        return constraint.value.toYInt()
    }

    private fun resolveRange(range: Constraint): Int {
        if (!range.resolve()) {
            dimen.measure(widthSpec(), heightSpec())
            w.value = dimen.width
            h.value = dimen.height
        }
        return range.value
    }

    private fun resolveAxis(min: Constraint, center: Constraint, max: Constraint, range: Constraint) {
        resolveRange(range)

        val hR = range.value / 2
        if (max.resolve()) {
            min.value = max.value - range.value
            center.value = max.value - hR
        } else if (min.resolve()) {
            max.value = min.value + range.value
            center.value = min.value + hR
        } else if (center.resolve()) {
            min.value = center.value - hR
            max.value = center.value + hR
        }
    }

    internal fun clear() {
        yGroup?.clear()
        l.clear(); t.clear(); r.clear(); b.clear()
        w.clear(); h.clear()
        cX.clear(); cY.clear()
    }

    internal fun widthSpec(): Int = measureSpecFor(l, r, w)
    internal fun heightSpec(): Int = measureSpecFor(t, b, h)
}