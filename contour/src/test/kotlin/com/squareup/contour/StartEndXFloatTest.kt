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

class StartEndXFloatTest {

  @Test
  fun `plus operator`() {
    assertThat(LtrFloat(10f) + 5).isEqualTo(LtrFloat(15f))
    assertThat(LtrFloat(10f) + ScalarXInt(5)).isEqualTo(LtrFloat(15f))
    assertThat(LtrFloat(10f) + 5f).isEqualTo(LtrFloat(15f))
    assertThat(LtrFloat(10f) + ScalarXFloat(5f)).isEqualTo(LtrFloat(15f))

    assertThat(RtlFloat(10f) + 5).isEqualTo(RtlFloat(5f))
    assertThat(RtlFloat(10f) + ScalarXInt(5)).isEqualTo(RtlFloat(5f))
    assertThat(RtlFloat(10f) + 5f).isEqualTo(RtlFloat(5f))
    assertThat(RtlFloat(10f) + ScalarXFloat(5f)).isEqualTo(RtlFloat(5f))
  }

  @Test
  fun `minus operator`() {
    assertThat(LtrFloat(10f) - 5).isEqualTo(LtrFloat(5f))
    assertThat(LtrFloat(10f) - ScalarXInt(5)).isEqualTo(LtrFloat(5f))
    assertThat(LtrFloat(10f) - 5f).isEqualTo(LtrFloat(5f))
    assertThat(LtrFloat(10f) - ScalarXFloat(5f)).isEqualTo(LtrFloat(5f))

    assertThat(RtlFloat(10f) - 5).isEqualTo(RtlFloat(15f))
    assertThat(RtlFloat(10f) - ScalarXInt(5)).isEqualTo(RtlFloat(15f))
    assertThat(RtlFloat(10f) - 5f).isEqualTo(RtlFloat(15f))
    assertThat(RtlFloat(10f) - ScalarXFloat(5f)).isEqualTo(RtlFloat(15f))
  }

  @Test
  fun `toInt method`() {
    assertThat(LtrFloat(10f).toInt()).isEqualTo(LtrInt(10))
    assertThat(RtlFloat(10f).toInt()).isEqualTo(RtlInt(10))
  }
}