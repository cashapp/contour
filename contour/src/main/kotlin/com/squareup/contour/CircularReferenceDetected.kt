package com.squareup.contour

import android.view.View
import java.lang.Exception

class CircularReferenceDetected : Exception() {

  class TraceElement(
    val view: View,
    val seenAt: StackTraceElement?,
    val referencedFrom: StackTraceElement?
  )

  private val list = ArrayList<TraceElement>()

  fun add(trace: TraceElement) {
    list += trace
  }

  override val message: String?
    get() = buildString {
      val count = list.size
      appendln()
      appendln()
      appendln("Circular reference detected through the following calls:")
      list.forEachIndexed { i, t ->
        val bullet = "${count - i}) "
        val indent = " ".repeat(bullet.length)
        append(bullet).appendln("Calling ${t.seenAt?.methodName}() on ${t.view} from:")
        append(indent).appendln(t.referencedFrom.toString())
      }
    }
}
