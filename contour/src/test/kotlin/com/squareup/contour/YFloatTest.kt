/*
 * Copyright 2021 Square Inc.
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

class YFloatTest {

  @Test
  fun `plus operator`() {
    assertThat(YFloat(10f) + 10).isEqualTo(YFloat(20f))
    assertThat(YFloat(10f) + YInt(10)).isEqualTo(YFloat(20f))
    assertThat(YFloat(10f) + 10f).isEqualTo(YFloat(20f))
    assertThat(YFloat(10f) + YFloat(10f)).isEqualTo(YFloat(20f))
  }

  @Test
  fun `minus operator`() {
    assertThat(YFloat(20f) - 10).isEqualTo(YFloat(10f))
    assertThat(YFloat(20f) - YInt(10)).isEqualTo(YFloat(10f))
    assertThat(YFloat(20f) - 10f).isEqualTo(YFloat(10f))
    assertThat(YFloat(20f) - YFloat(10f)).isEqualTo(YFloat(10f))
  }

  @Test
  fun `times operator`() {
    assertThat(YFloat(10f) * 10).isEqualTo(YFloat(100f))
    assertThat(YFloat(10f) * YInt(10)).isEqualTo(YFloat(100f))
    assertThat(YFloat(10f) * 10f).isEqualTo(YFloat(100f))
    assertThat(YFloat(10f) * YFloat(10f)).isEqualTo(YFloat(100f))
  }

  @Test
  fun `div operator`() {
    assertThat(YFloat(10f) / 10).isEqualTo(YFloat(1f))
    assertThat(YFloat(10f) / YInt(10)).isEqualTo(YFloat(1f))
    assertThat(YFloat(10f) / 10f).isEqualTo(YFloat(1f))
    assertThat(YFloat(10f) / YFloat(10f)).isEqualTo(YFloat(1f))
  }

  @Test
  fun `compareTo operator`() {
    assertThat(YFloat(10f).compareTo(10)).isEqualTo(0)
    assertThat(YFloat(20f).compareTo(10)).isEqualTo(1)
    assertThat(YFloat(10f).compareTo(20)).isEqualTo(-1)

    assertThat(YFloat(10f).compareTo(YInt(10))).isEqualTo(0)
    assertThat(YFloat(20f).compareTo(YInt(10))).isEqualTo(1)
    assertThat(YFloat(10f).compareTo(YInt(20))).isEqualTo(-1)

    assertThat(YFloat(10f).compareTo(10f)).isEqualTo(0)
    assertThat(YFloat(20f).compareTo(10f)).isEqualTo(1)
    assertThat(YFloat(10f).compareTo(20f)).isEqualTo(-1)

    assertThat(YFloat(10f).compareTo(YFloat(10f))).isEqualTo(0)
    assertThat(YFloat(20f).compareTo(YFloat(10f))).isEqualTo(1)
    assertThat(YFloat(10f).compareTo(YFloat(20f))).isEqualTo(-1)
  }

  @Test
  fun `toY method`() {
    assertThat(YFloat(10f).toX()).isEqualTo(ScalarXFloat(10f))
  }

  @Test
  fun `toInt method`() {
    assertThat(YFloat(10f).toInt()).isEqualTo(YInt(10))
  }
}
