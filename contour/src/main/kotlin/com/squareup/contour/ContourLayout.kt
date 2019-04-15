    @file:Suppress("unused")

package com.squareup.contour

import android.content.Context
import android.view.View
import android.view.ViewGroup

open class ContourLayout(context: Context) : ViewGroup(context) {

    private val widthConfig = SizeConfig()
    private val heightConfig = SizeConfig()
    private val geometryProvider = ParentGeometryProvider(widthConfig, heightConfig)

    private inline fun <T> View.withParams(block: ContourLayoutParams.() -> T): T {
        val params = layoutParams as ContourLayoutParams
        if(parent !== this@ContourLayout) {
                throw IllegalArgumentException("Referencing view outside of ViewGroup.")
        }
        return params.block()
    }

    fun View.left(): XInt = withParams { left() }
    fun View.top(): YInt = withParams { top() }
    fun View.right(): XInt = withParams { right() }
    fun View.bottom(): YInt = withParams { bottom() }
    fun View.centerX(): XInt = withParams { centerX() }
    fun View.centerY(): YInt = withParams { centerY() }
    fun View.width(): XInt = withParams { width() }
    fun View.height(): YInt = withParams { height() }
    fun View.preferredHeight(): YInt = withParams { preferredHeight() }

    fun widthOf(config: (available: XInt) -> XInt) {
        widthConfig.lambda = unwrapXIntToXInt(config)
    }

    fun heightOf(config: (available: YInt) -> YInt) {
        heightConfig.lambda = unwrapYIntToYInt(config)
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
        val recurseParams = child?.layoutParams as? ContourLayoutParams
        recurseParams?.parent = geometryProvider
        super.addView(child, index, params)
    }

    override fun requestLayout() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            (child.layoutParams as? ContourLayoutParams)?.clear()
        }
        super.requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthConfig.available = MeasureSpec.getSize(widthMeasureSpec)
        heightConfig.available = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthConfig.resolve(), heightConfig.resolve())
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val params = child.layoutParams as ContourLayoutParams
            child.measure(params.x.measureSpec(), params.y.measureSpec())
            child.layout(params.left().value, params.top().value, params.right().value, params.bottom().value)
        }
    }
}
