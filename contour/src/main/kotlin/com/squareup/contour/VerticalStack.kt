package com.squareup.contour

interface YGroup {
    fun add(params: ContourLayoutParams)
    fun resolve()
    fun clear()
}

class VerticalStack(layout: ContourLayout, provider: YProvider) : YGroup,
    HasParentGeometry {

    private val params = ArrayList<Any>()

    private val t = Constraint(this)
    private val cY = Constraint(this)
    private val b = Constraint(this)

    override lateinit var parent: ContourLayout.ParentGeometryProvider

    init {
        provider as YProviders
        parent = layout.geometryProvider
        t.configuration = provider.t
        cY.configuration = provider.cY
        b.configuration = provider.b
    }

    override fun add(params: ContourLayoutParams) {
        this.params += params
    }

    fun add(space: Int) {
        params += space.toYInt()
    }

    override fun resolve() {
        val height = params.sumBy {
            when(it) {
                is ContourLayoutParams -> it.height().toInt()
                is YInt -> it.value
                else -> TODO()
            }
        }

        val topY =
            if (t.resolve()) {
                t.value
            } else if (cY.resolve()) {
                cY.value - height / 2
            } else if (b.resolve()) {
                b.value - height
            } else {
                TODO()
            }

        var y = topY
        for (p in params) {
            when(p) {
                is ContourLayoutParams -> {
                    p.t.value = y
                    p.b.value = y + p.h.value
                    y = p.b.value
                }
                is YInt -> {
                    y += p.value
                }
                else -> TODO()
            }
        }
    }

    override fun clear() {
        t.clear()
        cY.clear()
        b.clear()
    }
}
