package com.squareup.contour

import android.content.Context

inline val Int.dip: Int
    get() {
        require(Contour.initialized) { "Contour.initialize must be called first." }
        return (Contour.density * this).toInt()
    }

object Contour {
    @JvmStatic
    var density: Float = 0f
        private set
    @JvmStatic
    var initialized: Boolean = false
        private set

    fun initialize(context: Context) {
        density = context.resources.displayMetrics.density
        initialized = true
    }
}