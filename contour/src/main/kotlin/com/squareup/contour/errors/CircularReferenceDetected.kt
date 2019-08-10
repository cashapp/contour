/*
 * Copyright 2019 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squareup.contour.errors

import android.view.View

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
