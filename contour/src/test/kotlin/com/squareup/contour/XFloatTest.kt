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

class XFloatTest {

  @Test
  fun `plus operator`() {
    assertThat(XFloat(10f) + 10).isEqualTo(XFloat(20f))
    assertThat(XFloat(10f) + XInt(10)).isEqualTo(XFloat(20f))
    assertThat(XFloat(10f) + 10f).isEqualTo(XFloat(20f))
    assertThat(XFloat(10f) + XFloat(10f)).isEqualTo(XFloat(20f))
  }

  @Test
  fun `minus operator`() {
    assertThat(XFloat(20f) - 10).isEqualTo(XFloat(10f))
    assertThat(XFloat(20f) - XInt(10)).isEqualTo(XFloat(10f))
    assertThat(XFloat(20f) - 10f).isEqualTo(XFloat(10f))
    assertThat(XFloat(20f) - XFloat(10f)).isEqualTo(XFloat(10f))
  }

  @Test
  fun `times operator`() {
    assertThat(XFloat(10f) * 10).isEqualTo(XFloat(100f))
    assertThat(XFloat(10f) * XInt(10)).isEqualTo(XFloat(100f))
    assertThat(XFloat(10f) * 10f).isEqualTo(XFloat(100f))
    assertThat(XFloat(10f) * XFloat(10f)).isEqualTo(XFloat(100f))
  }

  @Test
  fun `div operator`() {
    assertThat(XFloat(10f) / 10).isEqualTo(XFloat(1f))
    assertThat(XFloat(10f) / XInt(10)).isEqualTo(XFloat(1f))
    assertThat(XFloat(10f) / 10f).isEqualTo(XFloat(1f))
    assertThat(XFloat(10f) / XFloat(10f)).isEqualTo(XFloat(1f))
  }

  @Test
  fun `compareTo operator`() {
    assertThat(XFloat(10f).compareTo(10)).isEqualTo(0)
    assertThat(XFloat(20f).compareTo(10)).isEqualTo(1)
    assertThat(XFloat(10f).compareTo(20)).isEqualTo(-1)

    assertThat(XFloat(10f).compareTo(XInt(10))).isEqualTo(0)
    assertThat(XFloat(20f).compareTo(XInt(10))).isEqualTo(1)
    assertThat(XFloat(10f).compareTo(XInt(20))).isEqualTo(-1)

    assertThat(XFloat(10f).compareTo(10f)).isEqualTo(0)
    assertThat(XFloat(20f).compareTo(10f)).isEqualTo(1)
    assertThat(XFloat(10f).compareTo(20f)).isEqualTo(-1)

    assertThat(XFloat(10f).compareTo(XFloat(10f))).isEqualTo(0)
    assertThat(XFloat(20f).compareTo(XFloat(10f))).isEqualTo(1)
    assertThat(XFloat(10f).compareTo(XFloat(20f))).isEqualTo(-1)
  }

  @Test
  fun `toY method`() {
    assertThat(XFloat(10f).toY()).isEqualTo(YFloat(10f))
  }

  @Test
  fun `toInt method`() {
    assertThat(XFloat(10f).toInt()).isEqualTo(XInt(10))
  }
}
