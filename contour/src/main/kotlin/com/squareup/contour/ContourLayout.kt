@file:Suppress("unused")

package com.squareup.contour

import android.content.Context
import android.view.View
import android.view.ViewGroup

class WidthProvider(var availableWidth: XInt = XInt.NOT_SET)
class HeightProvider(var availableHeight: YInt = YInt.NOT_SET)


open class ContourLayout(context: Context) : ViewGroup(context) {
    inner class ParentGeometryProvider {
        fun left(): XInt = XInt.ZERO
        fun right(): XInt = widthConfig(widthProvider)
        fun width(): XInt = widthConfig(widthProvider)
        fun width(amount: Float): XInt = (widthConfig(widthProvider).toInt() * amount).toXInt()
        fun centerX(): XInt = widthConfig(widthProvider) / 2

        fun top(): YInt = YInt.ZERO
        fun bottom(): YInt = heightConfig(heightProvider)
        fun height(): YInt = heightConfig(heightProvider)
        fun centerY(): YInt = heightConfig(heightProvider) / 2
    }

    fun <T : View> T.layoutBy(config: ContourLayoutParams.() -> Unit): T {
        layoutParams = ContourLayoutParams(ViewDimensions(this)).also {
            it.parent = geometryProvider
            it.config()
        }
        return this
    }

    internal inline fun <T> View.withRecurseParams(block: ContourLayoutParams.() -> T): T {
        val params = layoutParams as ContourLayoutParams
//    if(parent !== this@ContourLayout) {
//            throw IllegalArgumentException("Referencing view outside of ViewGroup.")
//    }
        return params.block()
    }

    fun View.left(): XInt = withRecurseParams { left() }
    fun View.top(): YInt = withRecurseParams { top() }
    fun View.right(): XInt = withRecurseParams { right() }
    fun View.bottom(): YInt = withRecurseParams { bottom() }
    fun View.centerX(): XInt = withRecurseParams { centerX() }
    fun View.centerY(): YInt = withRecurseParams { centerY() }
    fun View.width(): XInt = withRecurseParams { width() }
    fun View.height(): YInt = withRecurseParams { height() }
    fun View.preferredHeight(): YInt = withRecurseParams { preferredHeight() }

    private var widthConfig: WidthProvider.() -> XInt = { availableWidth }
    private var heightConfig: HeightProvider.() -> YInt = { availableHeight }
    internal val geometryProvider = ParentGeometryProvider()
    private var widthProvider = WidthProvider()
    private var heightProvider = HeightProvider()

    fun widthOf(config: WidthProvider.() -> XInt) {
        widthConfig = config
    }

    fun heightOf(config: HeightProvider.() -> YInt) {
        heightConfig = config
    }

    fun verticalStack(provider: FromYPositionedContext) = VerticalStack(this, provider)
    fun verticalStack(provider: FromBottomContext) = VerticalStack(this, provider)
    fun verticalStack(provider: FromTopContext) = VerticalStack(this, provider)

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
        widthProvider.availableWidth = MeasureSpec.getSize(widthMeasureSpec).toXInt()
        heightProvider.availableHeight = MeasureSpec.getSize(heightMeasureSpec).toYInt()
        setMeasuredDimension(widthConfig(widthProvider).toInt(), heightConfig(heightProvider).toInt())
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val params = child.layoutParams as ContourLayoutParams
            child.measure(params.widthSpec(), params.heightSpec())
            child.layout(params.left().value, params.top().value, params.right().value, params.bottom().value)
        }
    }
}
