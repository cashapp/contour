package com.squareup.contour.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.squareup.contour.ContourLayout

fun <T : ViewGroup> T.layoutSizeOf(width: Int, height: Int): T {
    measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )
    layout(0, 0, measuredWidth, measuredHeight)
    return this
}

fun contourLayout(
    context: Context,
    width: Int = 200,
    height: Int = 50,
    initializeLayout: ContourLayout.() -> Unit
): ContourLayout =
    object : ContourLayout(context) {
        override fun onInitializeLayout() {
            initializeLayout()
        }
    }.layoutSizeOf(width, height)

fun ViewGroup.forceRelayout() {
    requestLayout()
    layoutSizeOf(measuredWidth, measuredHeight)
}