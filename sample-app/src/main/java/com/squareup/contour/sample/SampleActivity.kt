package com.squareup.contour.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.LazyThreadSafetyMode.NONE

class SampleActivity : AppCompatActivity() {

  private var i = 0

  private val views by lazy(NONE) {
    listOf<() -> View>(
        { SampleView1(this) },
        { SampleView2(this) }
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(views[i]())
  }

  fun showNext() {
    i = (i + 1) % views.size
    setContentView(views[i]())
  }
}
