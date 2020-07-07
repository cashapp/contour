package com.squareup.contour

import android.app.Activity
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.google.common.truth.Truth.assertThat
import com.squareup.contour.utils.contourLayout
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ContourSizeConfigTests {
  private val activity = Robolectric.buildActivity(Activity::class.java).get()

  @Test
  fun `match parent size config, no subviews`() {
    val layout = contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      setPadding(1, 2, 4, 8)
      contourWidthMatchParent()
      contourHeightMatchParent()
    }

    assertThat(layout.left).isEqualTo(0)
    assertThat(layout.top).isEqualTo(0)
    assertThat(layout.right).isEqualTo(200)
    assertThat(layout.bottom).isEqualTo(50)
    assertThat(layout.width).isEqualTo(200)
    assertThat(layout.height).isEqualTo(50)
  }

  @Test
  fun `match parent size config, multiple subviews`() {
    val view1 = View(activity).apply {
      visibility = VISIBLE
    }
    val view2 = View(activity).apply {
      visibility = VISIBLE
    }
    val layout = contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      setPadding(1, 2, 4, 8)
      contourWidthMatchParent()
      contourHeightMatchParent()

      view1.layoutBy(
          x = leftTo { parent.left() }.widthOf { 100.toXInt() },
          y = topTo { parent.top() }.heightOf { 25.toYInt() }
      )

      view2.layoutBy(
          x = leftTo { parent.left() }.widthOf { 201.toXInt() },
          y = topTo { parent.top() }.heightOf { 51.toYInt() }
      )
    }

    assertThat(layout.left).isEqualTo(0)
    assertThat(layout.top).isEqualTo(0)
    assertThat(layout.right).isEqualTo(200)
    assertThat(layout.bottom).isEqualTo(50)
    assertThat(layout.width).isEqualTo(200)
    assertThat(layout.height).isEqualTo(50)
  }

  @Test
  fun `wrap content size config, no subviews`() {
    val paddingLeft = 1
    val paddingTop = 2
    val paddingRight = 4
    val paddingBottom = 8
    val layout = contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
      contourWidthWrapContent()
      contourHeightWrapContent()
    }

    assertThat(layout.left).isEqualTo(0)
    assertThat(layout.top).isEqualTo(0)
    assertThat(layout.right).isEqualTo(paddingLeft + paddingRight)
    assertThat(layout.bottom).isEqualTo(paddingTop + paddingBottom)
    assertThat(layout.width).isEqualTo(paddingLeft + paddingRight)
    assertThat(layout.height).isEqualTo(paddingTop + paddingBottom)
  }

  @Test
  fun `wrap content size config, multiple subviews`() {
    val view1 = View(activity).apply {
      visibility = VISIBLE
    }
    val view2 = View(activity).apply {
      visibility = VISIBLE
    }
    val invisibleView = View(activity).apply {
      visibility = INVISIBLE
    }
    val goneView = View(activity).apply {
      visibility = GONE
    }

    val paddingLeft = 1
    val paddingTop = 2
    val paddingRight = 4
    val paddingBottom = 8
    val layout = contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
      contourWidthWrapContent()
      contourHeightWrapContent()

      view1.layoutBy(
          x = leftTo { parent.left() }.widthOf { 100.toXInt() },
          y = topTo { parent.top() }.heightOf { 25.toYInt() }
      )

      view2.layoutBy(
          x = leftTo { parent.left() }.widthOf { 200.toXInt() },
          y = topTo { parent.top() }.heightOf { 50.toYInt() }
      )

      invisibleView.layoutBy(
          x = leftTo { parent.left() }.widthOf { 300.toXInt() },
          y = topTo { parent.top() }.heightOf { 60.toYInt() }
      )

      goneView.layoutBy(
          x = leftTo { parent.left() }.widthOf { 400.toXInt() },
          y = topTo { view1.bottom() }.heightOf { 70.toYInt() }
      )
    }

    assertThat(layout.left).isEqualTo(0)
    assertThat(layout.top).isEqualTo(0)
    assertThat(layout.right).isEqualTo(paddingLeft + 300 + paddingRight)
    assertThat(layout.bottom).isEqualTo(paddingTop + 60 + paddingBottom)
    assertThat(layout.width).isEqualTo(paddingLeft + 300 + paddingRight)
    assertThat(layout.height).isEqualTo(paddingTop + 60 + paddingBottom)
  }

  @Test
  fun `exact size config, no subviews`() {
    val paddingLeft = 1
    val paddingTop = 2
    val paddingRight = 4
    val paddingBottom = 8
    val layout = contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
      contourWidthOf { 16.toXInt() }
      contourHeightOf { 32.toYInt() }
    }

    assertThat(layout.left).isEqualTo(0)
    assertThat(layout.top).isEqualTo(0)
    assertThat(layout.right).isEqualTo(16)
    assertThat(layout.bottom).isEqualTo(32)
    assertThat(layout.width).isEqualTo(16)
    assertThat(layout.height).isEqualTo(32)
  }

  @Test
  fun `exact size config, multiple subviews`() {
    val view1 = View(activity).apply {
      visibility = VISIBLE
    }
    val view2 = View(activity).apply {
      visibility = VISIBLE
    }
    val invisibleView = View(activity).apply {
      visibility = INVISIBLE
    }
    val goneView = View(activity).apply {
      visibility = GONE
    }

    val paddingLeft = 1
    val paddingTop = 2
    val paddingRight = 4
    val paddingBottom = 8
    val layout = contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
      contourWidthOf { 16.toXInt() }
      contourHeightOf { 32.toYInt() }

      view1.layoutBy(
          x = leftTo { parent.left() }.widthOf { 100.toXInt() },
          y = topTo { parent.top() }.heightOf { 25.toYInt() }
      )

      view2.layoutBy(
          x = leftTo { parent.left() }.widthOf { 200.toXInt() },
          y = topTo { parent.top() }.heightOf { 50.toYInt() }
      )

      invisibleView.layoutBy(
          x = leftTo { parent.left() }.widthOf { 300.toXInt() },
          y = topTo { parent.top() }.heightOf { 60.toYInt() }
      )

      goneView.layoutBy(
          x = leftTo { parent.left() }.widthOf { 400.toXInt() },
          y = topTo { view1.bottom() }.heightOf { 70.toYInt() }
      )
    }

    assertThat(layout.left).isEqualTo(0)
    assertThat(layout.top).isEqualTo(0)
    assertThat(layout.right).isEqualTo(16)
    assertThat(layout.bottom).isEqualTo(32)
    assertThat(layout.width).isEqualTo(16)
    assertThat(layout.height).isEqualTo(32)
  }
}