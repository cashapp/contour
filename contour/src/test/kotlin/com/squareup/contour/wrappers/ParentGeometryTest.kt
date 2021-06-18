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

package com.squareup.contour.wrappers

import android.graphics.Rect
import com.google.common.truth.Truth.assertThat
import com.squareup.contour.XInt
import com.squareup.contour.YInt
import com.squareup.contour.constraints.SizeConfig
import org.junit.Test

class ParentGeometryTest {

  private val lambda: (Int) -> Int = { it }
  private val paddingConfig = {
    Rect().apply {
      top = 10
      right = 10
      left = 10
      bottom = 10
    }
  }
  private val mockWidthConfig = SizeConfig(available = 40, lambda = lambda)
  private val mockHeightConfig = SizeConfig(available = 40, lambda = lambda)
  private val parentGeometry = ParentGeometry(mockWidthConfig, mockHeightConfig, paddingConfig)

  @Test
  fun `left method`() {
    assertThat(parentGeometry.left()).isEqualTo(XInt(10))
  }

  @Test
  fun `right method`() {
    assertThat(parentGeometry.right()).isEqualTo(XInt(30))
  }

  @Test
  fun `width method`() {
    assertThat(parentGeometry.width()).isEqualTo(XInt(40))
  }

  @Test
  fun `centerX method`() {
    assertThat(parentGeometry.centerX()).isEqualTo(XInt(20))
  }

  @Test
  fun `top method`() {
    assertThat(parentGeometry.top()).isEqualTo(YInt(10))
  }

  @Test
  fun `bottom method`() {
    assertThat(parentGeometry.bottom()).isEqualTo(YInt(30))
  }

  @Test
  fun `height method`() {
    assertThat(parentGeometry.height()).isEqualTo(YInt(40))
  }

  @Test
  fun `centerY method`() {
    assertThat(parentGeometry.centerY()).isEqualTo(YInt(20))
  }

  @Test
  fun `padding method`() {
    assertThat(parentGeometry.padding().top).isEqualTo(10)
    assertThat(parentGeometry.padding().right).isEqualTo(10)
    assertThat(parentGeometry.padding().bottom).isEqualTo(10)
    assertThat(parentGeometry.padding().left).isEqualTo(10)
  }
}
