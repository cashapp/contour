@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "NOTHING_TO_INLINE", "unused")
package com.squareup.contour

inline fun unwrapXIntToXInt(crossinline lambda: (XInt) -> XInt): (Int) -> Int = { lambda(it.toXInt()).value }
inline fun unwrapYIntToYInt(crossinline lambda: (YInt) -> YInt): (Int) -> Int = { lambda(it.toYInt()).value }

inline class XInt(val value: Int) {
    operator fun minus(other: Int): XInt = XInt(value - other)
    operator fun plus(other: Int): XInt = XInt(value + other)
    operator fun div(other: Int): XInt = XInt(value / other)
    operator fun times(other: Int): XInt = XInt(value * other)
    fun toY(): YInt = YInt(value)
    companion object {
        val ZERO: XInt = XInt(0)
        val MIN_VALUE: XInt = XInt(Int.MIN_VALUE)
    }
}

inline class YInt(val value: Int) {
    operator fun minus(other: Int): YInt = YInt(value - other)
    operator fun plus(other: Int): YInt = YInt(value + other)
    operator fun div(other: Int): YInt = YInt(value / other)
    operator fun times(other: Int): YInt = YInt(value * other)
    fun toX(): XInt = XInt(value)
    companion object {
        val ZERO: YInt = YInt(0)
        val NOT_SET: YInt = YInt(Int.MIN_VALUE)
    }
}

inline fun Int.toXInt(): XInt = XInt(this)
inline fun Float.toXInt(): XInt = XInt(toInt())
inline fun Int.toYInt(): YInt = YInt(this)
inline fun Float.toYInt(): YInt = YInt(toInt())