package com.squareup.contour

import android.view.View

class MaxOfResolver(
    private val p0: ScalarResolver,
    private val p1: ScalarResolver
) : ScalarResolver, YResolver {

    private val size = Constraint()
    private var found: ScalarResolver? = null
    private lateinit var parent: ContourLayoutParams
    private var range: Int = Int.MIN_VALUE

    private fun findMax(): ScalarResolver {
        val found = found
        if (found != null) return found
        else {
            val max = if (p0.min() > p1.min()) p0 else p1
            this.found = max
            return max
        }
    }

    override fun min(): Int = findMax().min()
    override fun mid(): Int = findMax().mid()
    override fun max(): Int = findMax().max()

    override fun range(): Int {
        if (range == Int.MIN_VALUE) {
            parent.measureSelf()
        }
        return range
    }

    override fun onAttach(parent: ContourLayoutParams) {
        this.parent = parent
        p0.onAttach(parent)
        p1.onAttach(parent)
    }

    override fun onRangeResolved(value: Int) {
        range = value
        p0.onRangeResolved(value)
        p1.onRangeResolved(value)
    }

    override fun measureSpec(): Int {
        return if (size.isSet) {
            View.MeasureSpec.makeMeasureSpec(size.resolve(), View.MeasureSpec.EXACTLY)
        } else {
            0
        }
    }

    override fun clear() {
        p0.clear()
        p1.clear()
        found = null
        range = Int.MIN_VALUE
    }
}
