package com.squareup.contour.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.contour.Contour
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Contour.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(SampleView(this))
    }
}
