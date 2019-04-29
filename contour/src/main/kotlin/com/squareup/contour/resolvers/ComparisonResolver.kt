package com.squareup.contour.resolvers

import android.view.View
import com.squareup.contour.constraints.Constraint
import com.squareup.contour.ContourLayoutParams
import com.squareup.contour.XResolver
import com.squareup.contour.YResolver
import com.squareup.contour.resolvers.ComparisonResolver.CompareBy.MaxOf
import com.squareup.contour.resolvers.ComparisonResolver.CompareBy.MinOf

internal class ComparisonResolver(
  private val p0: ScalarResolver,
  private val p1: ScalarResolver,
  private val compareBy: CompareBy
) : ScalarResolver, XResolver, YResolver {

  internal enum class CompareBy {
    MaxOf,
    MinOf
  }

  private val size = Constraint()
  private var found: ScalarResolver? = null
  private lateinit var parent: ContourLayoutParams
  private var range: Int = Int.MIN_VALUE

  private fun findWinner(): ScalarResolver {
    val found = found
    if (found != null) return found
    else {
      when (compareBy) {
        MaxOf -> {
          val max = if (p0.min() >= p1.min()) p0 else p1
          this.found = max
          return max
        }
        MinOf -> {
          val min = if (p0.min() <= p1.min()) p0 else p1
          this.found = min
          return min
        }
      }

    }
  }

  override fun min(): Int = findWinner().min()
  override fun mid(): Int = findWinner().mid()
  override fun max(): Int = findWinner().max()

  override fun range(): Int {
    if (range == Int.MIN_VALUE) {
      parent.measureSelf()
    }
    return range
  }

  override fun onAttach(parent: ContourLayoutParams) {
    this.parent = parent
    p0.onAttach(parent)
    p1.onAttach(parent)
  }

  override fun onRangeResolved(value: Int) {
    range = value
    p0.onRangeResolved(value)
    p1.onRangeResolved(value)
  }

  override fun measureSpec(): Int {
    return if (size.isSet) {
      View.MeasureSpec.makeMeasureSpec(size.resolve(), View.MeasureSpec.EXACTLY)
    } else {
      0
    }
  }

  override fun clear() {
    p0.clear()
    p1.clear()
    found = null
    range = Int.MIN_VALUE
  }
}
