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

class XIntTest {

  @Test
  fun `plus operator`() {
    assertThat(XInt(10) + 10).isEqualTo(XInt(20))
    assertThat(XInt(10) + XInt(10)).isEqualTo(XInt(20))
    assertThat(XInt(10) + 10f).isEqualTo(XFloat(20f))
    assertThat(XInt(10) + XFloat(10f)).isEqualTo(XFloat(20f))
  }

  @Test
  fun `minus operator`() {
    assertThat(XInt(20) - 10).isEqualTo(XInt(10))
    assertThat(XInt(20) - XInt(10)).isEqualTo(XInt(10))
    assertThat(XInt(20) - 10f).isEqualTo(XFloat(10f))
    assertThat(XInt(20) - XFloat(10f)).isEqualTo(XFloat(10f))
  }

  @Test
  fun `times operator`() {
    assertThat(XInt(10) * 10).isEqualTo(XInt(100))
    assertThat(XInt(10) * XInt(10)).isEqualTo(XInt(100))
    assertThat(XInt(10) * 10f).isEqualTo(XFloat(100f))
    assertThat(XInt(10) * XFloat(10f)).isEqualTo(XFloat(100f))
  }

  @Test
  fun `div operator`() {
    assertThat(XInt(10) / 10).isEqualTo(XInt(1))
    assertThat(XInt(10) / XInt(10)).isEqualTo(XInt(1))
    assertThat(XInt(10) / 10f).isEqualTo(XFloat(1f))
    assertThat(XInt(10) / XFloat(10f)).isEqualTo(XFloat(1f))
  }

  @Test
  fun `compareTo operator`() {
    assertThat(XInt(10).compareTo(10)).isEqualTo(0)
    assertThat(XInt(20).compareTo(10)).isEqualTo(1)
    assertThat(XInt(10).compareTo(20)).isEqualTo(-1)

    assertThat(XInt(10).compareTo(XInt(10))).isEqualTo(0)
    assertThat(XInt(20).compareTo(XInt(10))).isEqualTo(1)
    assertThat(XInt(10).compareTo(XInt(20))).isEqualTo(-1)

    assertThat(XInt(10).compareTo(10f)).isEqualTo(0)
    assertThat(XInt(20).compareTo(10f)).isEqualTo(1)
    assertThat(XInt(10).compareTo(20f)).isEqualTo(-1)

    assertThat(XInt(10).compareTo(XFloat(10f))).isEqualTo(0)
    assertThat(XInt(20).compareTo(XFloat(10f))).isEqualTo(1)
    assertThat(XInt(10).compareTo(XFloat(20f))).isEqualTo(-1)
  }

  @Test
  fun `toY method`() {
    assertThat(XInt(10).toY()).isEqualTo(YInt(10))
  }
}
