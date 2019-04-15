package com.squareup.contour

import android.view.View
import kotlin.math.abs

interface YProvider
interface XProvider

interface ScalarProvider {
    fun min(): Int
    fun mid(): Int
    fun max(): Int
    fun range(): Int

    fun onAttach(parent: ContourLayoutParams)
    fun onRangeResolved(value: Int)

    fun measureSpec(): Int
    fun clear()
}

class SimpleScalarProvider : ScalarProvider,
    XProvider, FromLeftContext, FromRightContext, FromHorizontalCenterContext,
    YProvider, FromTopContext, FromBottomContext, FromYPositionedContext {

    internal lateinit var parent: ContourLayoutParams

    internal val c0 = Constraint()
    internal val c1 = Constraint()

    private var min = Int.MIN_VALUE
    private var mid = Int.MIN_VALUE
    private var max = Int.MIN_VALUE
    private var range = Int.MIN_VALUE

    override fun min(): Int {
        if(min == Int.MIN_VALUE) {
            if (c0.edge == Point.Min) {
                min = c0.resolveAndGet()
            } else {
                parent.measureSelf()
            }
        }
        return min
    }

    override fun mid(): Int {
        if(mid == Int.MIN_VALUE) {
            if (c0.edge == Point.Mid) {
                mid = c0.resolveAndGet()
            } else {
                parent.measureSelf()
            }
        }
        return mid
    }

    override fun max(): Int {
        if(max == Int.MIN_VALUE) {
            if (c0.edge == Point.Max) {
                max = c0.resolveAndGet()
            } else {
                parent.measureSelf()
            }
        }
        return max
    }

    override fun range(): Int {
        if (range == Int.MIN_VALUE) {
            parent.measureSelf()
        }
        return range
    }

    override fun onAttach(parent: ContourLayoutParams) {
        this.parent = parent
        c0.parent = parent
        c1.parent = parent
    }

    override fun onRangeResolved(value: Int) {
        range = value
        val hV = value / 2
        when(c0.edge) {
            Point.Min -> {
                min = c0.resolveAndGet()
                mid = min + hV
                max = min + value
            }
            Point.Mid -> {
                mid = c0.resolveAndGet()
                min = mid - hV
                max = mid + hV
            }
            Point.Max -> {
                max = c0.resolveAndGet()
                mid = max - hV
                min = max - value
            }
        }
    }

    override fun measureSpec(): Int {
        return if (c1.isConfigured()) {
            val value = c1.resolveAndGet()
            if (c1.isRange) {
                View.MeasureSpec.makeMeasureSpec(value, View.MeasureSpec.EXACTLY)
            } else {
                View.MeasureSpec.makeMeasureSpec(abs(c0.resolveAndGet() - value), View.MeasureSpec.EXACTLY)
            }
        } else {
            0
        }
    }

    override fun clear() {
        min = Int.MIN_VALUE
        mid = Int.MIN_VALUE
        max = Int.MIN_VALUE
        c0.clear()
        c1.clear()
    }

    override fun topTo(config: YConfig): YProvider {
        c1.isRange = false
        c1.edge = Point.Min
        c1.configuration = config
        return this
    }

    override fun bottomTo(config: YConfig): YProvider {
        c1.isRange = false
        c1.edge = Point.Mid
        c1.configuration = config
        return this
    }

    override fun heightOf(config: YConfig): YProvider {
        c1.isRange = true
        c1.configuration = config
        return this
    }

    override fun leftTo(config: XConfig): XProvider {
        c1.isRange = false
        c1.edge = Point.Min
        c1.configuration = config
        return this
    }

    override fun rightTo(config: XConfig): XProvider {
        c1.isRange = false
        c1.edge = Point.Max
        c1.configuration = config
        return this
    }

    override fun widthOf(config: XConfig): XProvider {
        c1.isRange = true
        c1.configuration = config
        return this
    }
}