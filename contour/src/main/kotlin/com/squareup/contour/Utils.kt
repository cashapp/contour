package com.squareup.contour

import android.view.View

internal fun measureSpecFor(min: Constraint, max: Constraint, range: Constraint): Int =
    if (max.isConfigured() && min.isConfigured()) {
        View.MeasureSpec.makeMeasureSpec(max.resolveAndGet() - min.resolveAndGet(), View.MeasureSpec.EXACTLY)
    } else if (range.isConfigured()) {
        View.MeasureSpec.makeMeasureSpec(range.resolveAndGet(), View.MeasureSpec.EXACTLY)
    } else {
        0
    }