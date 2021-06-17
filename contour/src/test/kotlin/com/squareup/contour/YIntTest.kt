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

class YIntTest {

  @Test
  fun `plus operator`() {
    assertThat(YInt(10) + 10).isEqualTo(YInt(20))
    assertThat(YInt(10) + YInt(10)).isEqualTo(YInt(20))
    assertThat(YInt(10) + 10f).isEqualTo(YFloat(20f))
    assertThat(YInt(10) + YFloat(10f)).isEqualTo(YFloat(20f))
  }

  @Test
  fun `minus operator`() {
    assertThat(YInt(20) - 10).isEqualTo(YInt(10))
    assertThat(YInt(20) - YInt(10)).isEqualTo(YInt(10))
    assertThat(YInt(20) - 10f).isEqualTo(YFloat(10f))
    assertThat(YInt(20) - YFloat(10f)).isEqualTo(YFloat(10f))
  }

  @Test
  fun `times operator`() {
    assertThat(YInt(10) * 10).isEqualTo(YInt(100))
    assertThat(YInt(10) * YInt(10)).isEqualTo(YInt(100))
    assertThat(YInt(10) * 10f).isEqualTo(YFloat(100f))
    assertThat(YInt(10) * YFloat(10f)).isEqualTo(YFloat(100f))
  }

  @Test
  fun `div operator`() {
    assertThat(YInt(10) / 10).isEqualTo(YInt(1))
    assertThat(YInt(10) / YInt(10)).isEqualTo(YInt(1))
    assertThat(YInt(10) / 10f).isEqualTo(YFloat(1f))
    assertThat(YInt(10) / YFloat(10f)).isEqualTo(YFloat(1f))
  }

  @Test
  fun `compareTo operator`() {
    assertThat(YInt(10).compareTo(10)).isEqualTo(0)
    assertThat(YInt(20).compareTo(10)).isEqualTo(1)
    assertThat(YInt(10).compareTo(20)).isEqualTo(-1)

    assertThat(YInt(10).compareTo(YInt(10))).isEqualTo(0)
    assertThat(YInt(20).compareTo(YInt(10))).isEqualTo(1)
    assertThat(YInt(10).compareTo(YInt(20))).isEqualTo(-1)

    assertThat(YInt(10).compareTo(10f)).isEqualTo(0)
    assertThat(YInt(20).compareTo(10f)).isEqualTo(1)
    assertThat(YInt(10).compareTo(20f)).isEqualTo(-1)

    assertThat(YInt(10).compareTo(YFloat(10f))).isEqualTo(0)
    assertThat(YInt(20).compareTo(YFloat(10f))).isEqualTo(1)
    assertThat(YInt(10).compareTo(YFloat(20f))).isEqualTo(-1)
  }

  @Test
  fun `toX method`() {
    assertThat(YInt(10).toX()).isEqualTo(XInt(10))
  }
}
