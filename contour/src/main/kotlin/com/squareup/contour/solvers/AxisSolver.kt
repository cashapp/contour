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

package com.squareup.contour.solvers

import com.squareup.contour.ContourLayout.LayoutSpec

/**
 * [AxisSolver] represents a strategy for solving points on an axis.
 * Implementations of this interface should be as lazy as possible. Invokers of this interface
 * use the least-knowledge fields as possible. This helps solve certain types of layouts where axis dimension and
 * position need to be calculated separately.
 *
 * Calling into the [min], [mid], [baseline], [max], [range] methods will trigger other [AxisSolver]s in your
 * layout configuration if this Solver is not fully resolved.
 *
 * Pairs of scalar resolvers create a bounding box for a laid-out view. The [XAxisSolver] computes the
 * left and right edges, and the [YAxisSolver] computes the top and bottom edges.
 *
 * The [YAxisSolver] may also compute the baseline.
 *
 * Most applications shouldn't need to implement this type.
 */

interface AxisSolver {

  /**
   * Represents the left or top point of a component in pixels relative to the parent layout's top / left
   * Calling this method may trigger work.
   */
  fun min(): Int

  /**
   * Represents the center x or y point of a component in pixels relative to the parent layout's top / left
   * Calling this method may trigger work.
   */
  fun mid(): Int

  /**
   * Represents the text baseline of a component with text on the y axis relative parent layout's top.
   * Calling this method may trigger work.
   *
   * If component does not have text - or is representing the x axis this will resolve to 0. This will probably change.
   */
  fun baseline(): Int

  /**
   * Represents the right or bottom point of a component in pixels relative to the parent layout's top / left
   * Calling this method may trigger work.
   */
  fun max(): Int

  /**
   * Represents the width or height of a component in pixels. This may be solved without any of the axis point being
   * solved or vice-versa
   * Calling this method may trigger work.
   */
  fun range(): Int

  fun onAttach(parent: LayoutSpec)
  fun onRangeResolved(range: Int, baselineRange: Int)

  fun measureSpec(): Int
  fun clear()
}

interface XAxisSolver : AxisSolver
interface YAxisSolver : AxisSolver