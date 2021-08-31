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

package com.squareup.contour

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ScalarXFloatTest {

  @Test
  fun `plus operator`() {
    assertThat(ScalarXFloat(10f) + 5).isEqualTo(ScalarXFloat(15f))
    assertThat(ScalarXFloat(10f) + ScalarXInt(5)).isEqualTo(ScalarXFloat(15f))

    assertThat(ScalarXFloat(10f) + 5f).isEqualTo(ScalarXFloat(15f))
    assertThat(ScalarXFloat(10f) + ScalarXFloat(5f)).isEqualTo(ScalarXFloat(15f))
  }

  @Test
  fun `minus operator`() {
    assertThat(ScalarXFloat(10f) + 5).isEqualTo(ScalarXFloat(15f))
    assertThat(ScalarXFloat(10f) + ScalarXInt(5)).isEqualTo(ScalarXFloat(15f))

    assertThat(ScalarXFloat(10f) - 5f).isEqualTo(ScalarXFloat(5f))
    assertThat(ScalarXFloat(10f) - ScalarXFloat(5f)).isEqualTo(ScalarXFloat(5f))
  }

  @Test
  fun `times operator`() {
    assertThat(ScalarXFloat(10f) * 5).isEqualTo(ScalarXFloat(50f))
    assertThat(ScalarXFloat(10f) * ScalarXInt(5)).isEqualTo(ScalarXFloat(50f))

    assertThat(ScalarXFloat(10f) * 5f).isEqualTo(ScalarXFloat(50f))
    assertThat(ScalarXFloat(10f) * ScalarXFloat(5f)).isEqualTo(ScalarXFloat(50f))
  }

  @Test
  fun `div operator`() {
    assertThat(ScalarXFloat(10f) / 5).isEqualTo(ScalarXFloat(2f))
    assertThat(ScalarXFloat(10f) / ScalarXInt(5)).isEqualTo(ScalarXFloat(2f))

    assertThat(ScalarXFloat(10f) / 5f).isEqualTo(ScalarXFloat(2f))
    assertThat(ScalarXFloat(10f) / ScalarXFloat(5f)).isEqualTo(ScalarXFloat(2f))
  }

  @Test
  fun `compareTo operator`() {
    assertThat(ScalarXFloat(10f).compareTo(5)).isEqualTo(1)
    assertThat(ScalarXFloat(10f).compareTo(10)).isEqualTo(0)
    assertThat(ScalarXFloat(10f).compareTo(15)).isEqualTo(-1)

    assertThat(ScalarXFloat(10f).compareTo(ScalarXInt(5))).isEqualTo(1)
    assertThat(ScalarXFloat(10f).compareTo(ScalarXFloat(10f))).isEqualTo(0)
    assertThat(ScalarXFloat(10f).compareTo(ScalarXInt(15))).isEqualTo(-1)

    assertThat(ScalarXFloat(10f).compareTo(5f)).isEqualTo(1)
    assertThat(ScalarXFloat(10f).compareTo(10f)).isEqualTo(0)
    assertThat(ScalarXFloat(10f).compareTo(15f)).isEqualTo(-1)

    assertThat(ScalarXFloat(10f).compareTo(ScalarXFloat(5f))).isEqualTo(1)
    assertThat(ScalarXFloat(10f).compareTo(ScalarXFloat(10f))).isEqualTo(0)
    assertThat(ScalarXFloat(10f).compareTo(ScalarXFloat(15f))).isEqualTo(-1)
  }

  @Test
  fun `toY method`() {
    assertThat(ScalarXFloat(10f).toY()).isEqualTo(YFloat(10f))
  }

  @Test
  fun `toInt method`() {
    assertThat(ScalarXFloat(10f).toInt()).isEqualTo(ScalarXInt(10))
  }
}