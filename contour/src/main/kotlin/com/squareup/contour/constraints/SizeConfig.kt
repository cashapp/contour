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

package com.squareup.contour.constraints

internal class SizeConfig {
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

  fun clear() {
    result = Int.MIN_VALUE
  }
}