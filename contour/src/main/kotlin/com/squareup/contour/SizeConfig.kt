package com.squareup.contour

class SizeConfig {
    var available: Int = Int.MIN_VALUE
    var result: Int = Int.MIN_VALUE
    var lambda: (Int) -> Int = { it }
    fun resolve(): Int {
        if (result == Int.MIN_VALUE) {
            require(available != Int.MIN_VALUE) { "Triggering layout before parent geometry available" }
            result = lambda(available)
        }
        return result
    }
}