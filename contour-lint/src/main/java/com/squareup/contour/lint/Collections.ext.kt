/*
 * Copyright 2020 Square Inc.
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

package com.squareup.contour.lint

/** Reorders items in `indices` to `to`. */
internal fun <E> List<E>.reorder(indices: IntRange, to: Int): List<E> {
  return sortedByIndex { index ->
    when (index) {
      to -> 2
      in indices -> 1
      else -> 0
    }
  }
}

internal inline fun <E, R> List<E>.sortedByIndex(crossinline selector: (i: Int) -> R): List<E> where R : Comparable<R> {
  return mapIndexed { i, item -> i to item }
      .sortedBy { (i) -> selector(i) }
      .map { (_, item) -> item }
}

/** Like split("\n"), but doesn't throw away trailing line breaks. */
internal fun String.splitLines(): MutableList<String> {
  return split("\n").map { it + "\n" }.toMutableList().apply {
    mapAt(lastIndex) { it.dropLast(1) }  // drop extra '\n'.
  }
}

/** Modifies an item at [index] in place. */
internal fun <E> MutableList<E>.mapAt(index: Int, run: (E) -> E): MutableList<E> {
  this[index] = run(this[index])
  return this
}
