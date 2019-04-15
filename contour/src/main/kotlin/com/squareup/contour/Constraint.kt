package com.squareup.contour

class Constraint {

    internal lateinit var parent: HasParentGeometry

    var edge: Point = Point.Min
    var isRange: Boolean = false

    var configuration: ((HasParentGeometry) -> XYInt)? = null
    var value: Int = XYInt.NOT_SET

    fun isConfigured(): Boolean = configuration != null

    fun resolveAndGet(): Int =
        if(resolve()) value
        else throw IllegalStateException("Could not resolve $this")

    fun resolve(): Boolean {
        if (value.isSet()) {
            return true
        }

        val config = configuration
        if (config == null) {
            return false
        } else {
            value = config(parent).toInt()
            return true
        }
    }

    fun clear() {
        value = XYInt.NOT_SET
    }
}