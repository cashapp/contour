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

class LeftRightXIntTest {

  @Test
  fun `plus operator`() {
    assertThat(LeftRightXInt(10) + 5).isEqualTo(LeftRightXInt(15))
    assertThat(LeftRightXInt(10) + ScalarXInt(5)).isEqualTo(LeftRightXInt(15))
    assertThat(LeftRightXInt(10) + LeftRightXInt(5)).isEqualTo(LeftRightXInt(15))

    assertThat(LeftRightXInt(10) + 5f).isEqualTo(LeftRightXFloat(15f))
    assertThat(LeftRightXInt(10) + ScalarXFloat(5f)).isEqualTo(LeftRightXFloat(15f))
    assertThat(LeftRightXInt(10) + LeftRightXFloat(5f)).isEqualTo(LeftRightXFloat(15f))
  }

  @Test
  fun `minus operator`() {
    assertThat(LeftRightXInt(10) - 5).isEqualTo(LeftRightXInt(5))
    assertThat(LeftRightXInt(10) - ScalarXInt(5)).isEqualTo(LeftRightXInt(5))
    assertThat(LeftRightXInt(10) - LeftRightXInt(5)).isEqualTo(LeftRightXInt(5))

    assertThat(LeftRightXInt(10) - 5f).isEqualTo(LeftRightXFloat(5f))
    assertThat(LeftRightXInt(10) - ScalarXFloat(5f)).isEqualTo(LeftRightXFloat(5f))
    assertThat(LeftRightXInt(10) - LeftRightXFloat(5f)).isEqualTo(LeftRightXFloat(5f))
  }

  @Test
  fun `times operator`() {
    assertThat(LeftRightXInt(10) * 5).isEqualTo(LeftRightXInt(50))
    assertThat(LeftRightXInt(10) * ScalarXInt(5)).isEqualTo(LeftRightXInt(50))
    assertThat(LeftRightXInt(10) * LeftRightXInt(5)).isEqualTo(LeftRightXInt(50))

    assertThat(LeftRightXInt(10) * 5f).isEqualTo(LeftRightXFloat(50f))
    assertThat(LeftRightXInt(10) * ScalarXFloat(5f)).isEqualTo(LeftRightXFloat(50f))
    assertThat(LeftRightXInt(10) * LeftRightXFloat(5f)).isEqualTo(LeftRightXFloat(50f))
  }

  @Test
  fun `div operator`() {
    assertThat(LeftRightXInt(10) / 5).isEqualTo(LeftRightXInt(2))
    assertThat(LeftRightXInt(10) / ScalarXInt(5)).isEqualTo(LeftRightXInt(2))
    assertThat(LeftRightXInt(10) / LeftRightXInt(5)).isEqualTo(LeftRightXInt(2))

    assertThat(LeftRightXInt(10) / 5f).isEqualTo(LeftRightXFloat(2f))
    assertThat(LeftRightXInt(10) / ScalarXFloat(5f)).isEqualTo(LeftRightXFloat(2f))
    assertThat(LeftRightXInt(10) / LeftRightXFloat(5f)).isEqualTo(LeftRightXFloat(2f))
  }

  @Test
  fun `compareTo operator`() {
    assertThat(LeftRightXInt(10).compareTo(5)).isEqualTo(1)
    assertThat(LeftRightXInt(10).compareTo(10)).isEqualTo(0)
    assertThat(LeftRightXInt(10).compareTo(15)).isEqualTo(-1)

    assertThat(LeftRightXInt(10).compareTo(ScalarXInt(5))).isEqualTo(1)
    assertThat(LeftRightXInt(10).compareTo(ScalarXInt(10))).isEqualTo(0)
    assertThat(LeftRightXInt(10).compareTo(ScalarXInt(15))).isEqualTo(-1)

    assertThat(LeftRightXInt(10).compareTo(LeftRightXInt(5))).isEqualTo(1)
    assertThat(LeftRightXInt(10).compareTo(LeftRightXInt(10))).isEqualTo(0)
    assertThat(LeftRightXInt(10).compareTo(LeftRightXInt(15))).isEqualTo(-1)

    assertThat(LeftRightXInt(10).compareTo(5f)).isEqualTo(1)
    assertThat(LeftRightXInt(10).compareTo(10f)).isEqualTo(0)
    assertThat(LeftRightXInt(10).compareTo(15f)).isEqualTo(-1)

    assertThat(LeftRightXInt(10).compareTo(ScalarXFloat(5f))).isEqualTo(1)
    assertThat(LeftRightXInt(10).compareTo(ScalarXFloat(10f))).isEqualTo(0)
    assertThat(LeftRightXInt(10).compareTo(ScalarXFloat(15f))).isEqualTo(-1)

    assertThat(LeftRightXInt(10).compareTo(LeftRightXFloat(5f))).isEqualTo(1)
    assertThat(LeftRightXInt(10).compareTo(LeftRightXFloat(10f))).isEqualTo(0)
    assertThat(LeftRightXInt(10).compareTo(LeftRightXFloat(15f))).isEqualTo(-1)
  }

  @Test
  fun `toY method`() {
    assertThat(LeftRightXInt(10).toY()).isEqualTo(YInt(10))
  }

  @Test
  fun `toFloat method`() {
    assertThat(LeftRightXInt(10).toFloat()).isEqualTo(LeftRightXFloat(10f))
  }
}
