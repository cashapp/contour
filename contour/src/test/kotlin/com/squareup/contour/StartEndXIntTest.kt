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
import com.squareup.contour.StartEndXFloat.LtrFloat
import com.squareup.contour.StartEndXFloat.RtlFloat
import com.squareup.contour.StartEndXInt.LtrInt
import com.squareup.contour.StartEndXInt.RtlInt
import org.junit.Test

class StartEndXIntTest {

  @Test
  fun `plus operator`() {
    assertThat(LtrInt(10) + 5).isEqualTo(LtrInt(15))
    assertThat(LtrInt(10) + ScalarXInt(5)).isEqualTo(LtrInt(15))
    assertThat(LtrInt(10) + 5f).isEqualTo(LtrFloat(15f))
    assertThat(LtrInt(10) + ScalarXFloat(5f)).isEqualTo(LtrFloat(15f))

    assertThat(RtlInt(10) + 5).isEqualTo(RtlInt(5))
    assertThat(RtlInt(10) + ScalarXInt(5)).isEqualTo(RtlInt(5))
    assertThat(RtlInt(10) + 5f).isEqualTo(RtlFloat(5f))
    assertThat(RtlInt(10) + ScalarXFloat(5f)).isEqualTo(RtlFloat(5f))
  }

  @Test
  fun `minus operator`() {
    assertThat(LtrInt(10) - 5).isEqualTo(LtrInt(5))
    assertThat(LtrInt(10) - ScalarXInt(5)).isEqualTo(LtrInt(5))
    assertThat(LtrInt(10) - 5f).isEqualTo(LtrFloat(5f))
    assertThat(LtrInt(10) - ScalarXFloat(5f)).isEqualTo(LtrFloat(5f))

    assertThat(RtlInt(10) - 5).isEqualTo(RtlInt(15))
    assertThat(RtlInt(10) - ScalarXInt(5)).isEqualTo(RtlInt(15))
    assertThat(RtlInt(10) - 5f).isEqualTo(RtlFloat(15f))
    assertThat(RtlInt(10) - ScalarXFloat(5f)).isEqualTo(RtlFloat(15f))
  }

  @Test
  fun `toFloat method`() {
    assertThat(LtrInt(10).toFloat()).isEqualTo(LtrFloat(10f))
    assertThat(RtlInt(10).toFloat()).isEqualTo(RtlFloat(10f))
  }
}