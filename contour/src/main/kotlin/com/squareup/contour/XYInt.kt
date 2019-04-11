package com.squareup.contour

interface XYInt {
    fun toInt(): Int
    companion object {
        const val NOT_SET: Int = Int.MIN_VALUE
    }
}

internal inline fun Int.isSet(): Boolean = this != XYInt.NOT_SET
internal typealias XIntLambda = () -> XInt
internal typealias YIntLambda = () -> YInt

inline class XInt(val value: Int) : XYInt {
    operator fun minus(other: Int): XInt = XInt(value - other)
    operator fun plus(other: Int): XInt = XInt(value + other)
    operator fun div(other: Int): XInt = XInt(value / other)
    operator fun times(other: Int): XInt = XInt(value * other)
    fun toY(): YInt = YInt(value)
    override fun toInt(): Int = value
    companion object {
        val ZERO: XInt = XInt(0)
        val NOT_SET: XInt = XInt(Int.MIN_VALUE)
    }
}

inline class YInt(val value: Int) : XYInt {
    operator fun minus(other: Int): YInt = YInt(value - other)
    operator fun plus(other: Int): YInt = YInt(value + other)
    operator fun div(other: Int): YInt = YInt(value / other)
    operator fun times(other: Int): YInt = YInt(value * other)
    fun toX(): XInt = XInt(value)
    override fun toInt(): Int = value
    companion object {
        val ZERO: YInt = YInt(0)
        val NOT_SET: YInt = YInt(Int.MIN_VALUE)
    }
}

inline fun Int.toXInt(): XInt = XInt(this)
inline fun Float.toXInt(): XInt = XInt(toInt())
inline fun Int.toYInt(): YInt = YInt(this)
inline fun Float.toYInt(): YInt = YInt(toInt())