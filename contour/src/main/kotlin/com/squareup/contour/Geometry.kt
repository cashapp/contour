/*
 * Copyright 2019 Square Inc.
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

@file:Suppress("unused")

package com.squareup.contour

/**
 * Represents a rectangle in your layout.
 * The methods obey a contract that they will either return the correct laid out or throw a
 * [com.squareup.contour.errors.CircularReferenceDetected] if the [com.squareup.contour.ContourLayout]
 * is configured incorrectly.
 */
interface Geometry {
  fun left(): XInt
  fun right(): XInt
  fun width(): XInt
  fun centerX(): XInt

  fun top(): YInt
  fun bottom(): YInt
  fun height(): YInt
  fun centerY(): YInt
}

