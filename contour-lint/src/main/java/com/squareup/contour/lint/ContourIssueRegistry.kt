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

package com.squareup.contour.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.google.auto.service.AutoService

@Suppress("UnstableApiUsage", "unused")
@AutoService(value = [IssueRegistry::class])
class ContourIssueRegistry : IssueRegistry() {
  override val issues = listOf(NestedContourLayoutsDetector.ISSUE)

  override val api = CURRENT_API

  /**
   * works with Studio 4.0 or later; see
   * [com.android.tools.lint.detector.api.describeApi]
   */
  override val minApi = 7

  override val vendor = Vendor(
    vendorName = "cashapp/contour",
    identifier = "app.cash.contour:contour:{version}",
    feedbackUrl = "https://github.com/cashapp/contour/issues",
  )
}
