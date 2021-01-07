package com.squareup.contour

import android.app.Activity
import android.view.View
import com.google.common.truth.Truth.assertThat
import com.squareup.contour.utils.FakeTextView
import com.squareup.contour.utils.contourLayout
import com.squareup.contour.utils.forceRelayout
import com.squareup.contour.utils.toXInt
import com.squareup.contour.utils.toYInt
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ContourTests {

  private val activity = Robolectric.buildActivity(Activity::class.java).get()

  @Test
  fun `simple single child layout`() {
    val plainOldView = View(activity)

    contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      plainOldView.layoutBy(
          leftTo { parent.left() }.rightTo { parent.right() },
          topTo { parent.top() }.bottomTo { parent.bottom() }
      )
    }

    assertThat(plainOldView.left).isEqualTo(0)
    assertThat(plainOldView.top).isEqualTo(0)
    assertThat(plainOldView.right).isEqualTo(200)
    assertThat(plainOldView.bottom).isEqualTo(50)
    assertThat(plainOldView.width).isEqualTo(200)
    assertThat(plainOldView.height).isEqualTo(50)
  }

  @Test
  fun `simple layout, with padding`() {
    val plainOldView = View(activity)
    val centeredView = View(activity)

    val leftPadding = 1
    val topPadding = 2
    val rightPadding = 4
    val bottomPadding = 8

    contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
      plainOldView.layoutBy(
          leftTo { parent.left() }.rightTo { parent.right() },
          topTo { parent.top() }.bottomTo { parent.bottom() }
      )
      centeredView.layoutBy(
          centerHorizontallyTo { parent.centerX() }.widthOf { 10.xdip },
          centerVerticallyTo { parent.centerY() }.heightOf { 10.ydip }
      )
    }

    assertThat(plainOldView.left).isEqualTo(leftPadding)
    assertThat(plainOldView.top).isEqualTo(topPadding)
    assertThat(plainOldView.right).isEqualTo(200 - rightPadding)
    assertThat(plainOldView.bottom).isEqualTo(50 - bottomPadding)
    assertThat(plainOldView.width).isEqualTo(200 - leftPadding - rightPadding)
    assertThat(plainOldView.height).isEqualTo(50 - topPadding - bottomPadding)

    assertThat(centeredView.left).isEqualTo(200 / 2 - 5)
    assertThat(centeredView.top).isEqualTo(50 / 2 - 5)
    assertThat(centeredView.right).isEqualTo(200 / 2 + 5)
    assertThat(centeredView.bottom).isEqualTo(50 / 2 + 5)
    assertThat(centeredView.width).isEqualTo(10)
    assertThat(centeredView.height).isEqualTo(10)
  }

  @Test
  fun `simple layout, with padding, respectsPadding disable`() {
    val plainOldView = View(activity)
    val centeredView = View(activity)

    val leftPadding = 1
    val topPadding = 2
    val rightPadding = 4
    val bottomPadding = 8

    val layout = contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      respectPadding = false
      setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
      plainOldView.layoutBy(
          leftTo { parent.left() }.rightTo { parent.right() },
          topTo { parent.top() }.bottomTo { parent.bottom() }
      )
      centeredView.layoutBy(
          centerHorizontallyTo { parent.centerX() }.widthOf { 10.xdip },
          centerVerticallyTo { parent.centerY() }.heightOf { 10.ydip }
      )
    }

    assertThat(plainOldView.left).isEqualTo(0)
    assertThat(plainOldView.top).isEqualTo(0)
    assertThat(plainOldView.right).isEqualTo(200)
    assertThat(plainOldView.bottom).isEqualTo(50)
    assertThat(plainOldView.width).isEqualTo(200)
    assertThat(plainOldView.height).isEqualTo(50)

    assertThat(centeredView.left).isEqualTo(200 / 2 - 5)
    assertThat(centeredView.top).isEqualTo(50 / 2 - 5)
    assertThat(centeredView.right).isEqualTo(200 / 2 + 5)
    assertThat(centeredView.bottom).isEqualTo(50 / 2 + 5)
    assertThat(centeredView.width).isEqualTo(10)
    assertThat(centeredView.height).isEqualTo(10)

    // Now re-enable respectPadding
    layout.respectPadding = true
    layout.forceRelayout()

    assertThat(plainOldView.left).isEqualTo(leftPadding)
    assertThat(plainOldView.top).isEqualTo(topPadding)
    assertThat(plainOldView.right).isEqualTo(200 - rightPadding)
    assertThat(plainOldView.bottom).isEqualTo(50 - bottomPadding)
    assertThat(plainOldView.width).isEqualTo(200 - leftPadding - rightPadding)
    assertThat(plainOldView.height).isEqualTo(50 - topPadding - bottomPadding)

    assertThat(centeredView.left).isEqualTo(200 / 2 - 5)
    assertThat(centeredView.top).isEqualTo(50 / 2 - 5)
    assertThat(centeredView.right).isEqualTo(200 / 2 + 5)
    assertThat(centeredView.bottom).isEqualTo(50 / 2 + 5)
    assertThat(centeredView.width).isEqualTo(10)
    assertThat(centeredView.height).isEqualTo(10)
  }

  @Test
  fun `child can be aligned to another child`() {
    val fakeImageView = View(activity)
    val fakeTextView = View(activity)

    contourLayout(
        context = activity,
        width = 200,
        height = 50
    ) {
      fakeImageView.layoutBy(
          leftTo { parent.left() }.widthOf {
            parent.height()
                .toX()
          },
          topTo { parent.top() }.heightOf { parent.height() }
      )
      fakeTextView.layoutBy(
          leftTo { fakeImageView.right() }.rightTo { parent.right() },
          topTo { parent.top() }.heightOf { parent.height() }
      )
    }

    assertThat(fakeImageView.width).isEqualTo(50)
    assertThat(fakeImageView.height).isEqualTo(50)

    assertThat(fakeTextView.top).isEqualTo(0)
    assertThat(fakeTextView.left).isEqualTo(50)
    assertThat(fakeTextView.width).isEqualTo(150)
    assertThat(fakeTextView.height).isEqualTo(50)
  }

  @Test
  fun `unspecified size will fallback to views preferred size`() {
    val fakeTextView = FakeTextView(activity, "Test", 10)

    contourLayout(activity) {
      fakeTextView.layoutBy(
          leftTo { parent.left() },
          topTo { parent.top() }
      )
    }

    assertThat(fakeTextView.width).isEqualTo(40)
    assertThat(fakeTextView.height).isEqualTo(10)
  }

  @Test
  fun `minOf maxOf on x axis`() {
    val view0 = View(activity)
    val view1 = View(activity)

    var x0 = 10.toXInt()
    val x1 = 20.toXInt()

    val layout = contourLayout(activity) {
      view0.layoutBy(
          maxOf(leftTo { x0 }, leftTo { x1 }),
          topTo { parent.top() }
      )
      view1.layoutBy(
          minOf(leftTo { x0 }, leftTo { x1 }),
          topTo { parent.top() }
      )
    }

    assertThat(view0.left).isEqualTo(20)
    assertThat(view1.left).isEqualTo(10)

    x0 = 30.toXInt()
    layout.forceRelayout()

    assertThat(view0.left).isEqualTo(30)
    assertThat(view1.left).isEqualTo(20)
  }

  @Test
  fun `minOf maxOf on y axis`() {
    val view0 = View(activity)
    val view1 = View(activity)

    var y0 = 10.toYInt()
    val y1 = 20.toYInt()

    val layout = contourLayout(activity) {
      view0.layoutBy(
          leftTo { parent.left() },
          maxOf(topTo { y0 }, topTo { y1 })
      )
      view1.layoutBy(
          leftTo { parent.left() },
          minOf(topTo { y0 }, topTo { y1 })
      )
    }

    assertThat(view0.top).isEqualTo(20)
    assertThat(view1.top).isEqualTo(10)

    y0 = 30.toYInt()
    layout.forceRelayout()

    assertThat(view0.top).isEqualTo(30)
    assertThat(view1.top).isEqualTo(20)
  }

  @Test
  fun `width as fraction of parents width`() {
    val view = View(activity)

    var amount = 0.4f

    val layout = contourLayout(
        activity,
        width = 50
    ) {
      view.layoutBy(
          leftTo { parent.left() }.widthOfFloat { parent.width() * amount },
          centerVerticallyTo { parent.centerY() }
      )
    }

    assertThat(view.width).isEqualTo(20) // 50 * 0.4

    amount = 0.51f
    layout.forceRelayout()
    assertThat(view.width).isEqualTo(25) // 50 * 0.51 = 25.5 ~= 25 floored
  }

  @Test
  fun `height as fraction of parents height`() {
    val view = View(activity)

    var amount = 0.1f

    val layout = contourLayout(
        activity,
        width = 260
    ) {
      view.layoutBy(
          leftTo { parent.left() }.widthOfFloat { parent.width() * amount },
          centerVerticallyTo { parent.centerY() }
      )
    }

    assertThat(view.width).isEqualTo(26) // 260 * 0.1

    amount = 0.13f
    layout.forceRelayout()
    assertThat(view.width).isEqualTo(33) // 260 * 0.13 = 33.8 ~= 33 floored
  }

  @Test
  fun `conversion of XFloat to XInt and YFloat to YInt`() {
    val view = View(activity)
    contourLayout(activity, width = 260, height = 260) {
      view.layoutBy(
          leftTo { parent.left() }.widthOf { (parent.width() * 0.42f).toInt() },
          topTo { parent.top() }.heightOf { (parent.height() * 0.99f).toInt() }
      )
    }
    assertThat(view.width).isEqualTo(109)   // 260 * 0.42 = 109.2 ~= 109 floored
    assertThat(view.height).isEqualTo(257)  // 260 * 0.99 = 257.4 ~= 257 floored
  }

  @Test
  fun `view set to GONE does not get laid out and is considered to have position and size 0`() {
    val view = View(activity)
    view.visibility = View.GONE

    val layout = contourLayout(activity) {
      view.layoutBy(
          leftTo { parent.centerX() },
          topTo { parent.centerY() }
      )
    }

    assertThat(view.left).isEqualTo(0)
    assertThat(view.right).isEqualTo(0)
    assertThat(view.top).isEqualTo(0)
    assertThat(view.bottom).isEqualTo(0)
    assertThat(view.width).isEqualTo(0)
    assertThat(view.height).isEqualTo(0)

    // Now make it visible.
    view.visibility = View.VISIBLE
    layout.forceRelayout()

    assertThat(view.left).isEqualTo(100)
    assertThat(view.right).isEqualTo(100)
    assertThat(view.top).isEqualTo(25)
    assertThat(view.bottom).isEqualTo(25)
    assertThat(view.width).isEqualTo(0)
    assertThat(view.height).isEqualTo(0)
  }

  @Test
  fun `reference to height and width of view set to GONE should evaluate to 0`() {
    val viewThatIsGone = View(activity).apply { visibility = View.GONE }
    val otherView = View(activity)

    val layout = contourLayout(activity, width = 200, height = 50) {
      viewThatIsGone.layoutBy(
          leftTo { parent.centerX() }
              .widthOf { 10.xdip },
          topTo { parent.centerY() }
              .heightOf { 15.ydip }
      )

      otherView.layoutBy(
          leftTo { parent.left() + 5 }
              .widthOf { viewThatIsGone.width() + 1 },
          topTo { parent.top() + 10 }
              .heightOf { viewThatIsGone.height() + 2 }
      )
    }

    assertThat(otherView.left).isEqualTo(5)
    assertThat(otherView.right).isEqualTo(5 + 1)
    assertThat(otherView.top).isEqualTo(10)
    assertThat(otherView.bottom).isEqualTo(10 + 2)
    assertThat(otherView.width).isEqualTo(1)
    assertThat(otherView.height).isEqualTo(2)

    // Now make the view that was GONE visible.
    viewThatIsGone.visibility = View.VISIBLE
    layout.forceRelayout()

    assertThat(otherView.left).isEqualTo(5)
    assertThat(otherView.right).isEqualTo(5 + 10 + 1)
    assertThat(otherView.top).isEqualTo(10)
    assertThat(otherView.bottom).isEqualTo(10 + 15 + 2)
    assertThat(otherView.width).isEqualTo(10 + 1)
    assertThat(otherView.height).isEqualTo(15 + 2)
  }

  @Test
  fun `using other axis width constraint`() {
    val view = View(activity)

    contourLayout(context = activity, width = 200, height = 200) {
      view.layoutBy(
              leftTo { parent.left() }.rightTo { parent.right() - 50.dip },
              topTo { parent.top() }.heightOf { view.width().toY() }
      )
    }

    assertThat(view.left).isEqualTo(0)
    assertThat(view.top).isEqualTo(0)
    assertThat(view.right).isEqualTo(150)
    assertThat(view.bottom).isEqualTo(150)
    assertThat(view.width).isEqualTo(150)
    assertThat(view.height).isEqualTo(150)
  }

  @Test
  fun `using other axis height constraint`() {
    val view = View(activity)

    contourLayout(context = activity, width = 200, height = 200) {
      view.layoutBy(
              leftTo { parent.left() }.rightTo { view.height().toX() },
              topTo { parent.top() }.bottomTo { 125.ydip }
      )
    }

    assertThat(view.left).isEqualTo(0)
    assertThat(view.top).isEqualTo(0)
    assertThat(view.right).isEqualTo(125)
    assertThat(view.bottom).isEqualTo(125)
    assertThat(view.width).isEqualTo(125)
    assertThat(view.height).isEqualTo(125)
  }
}
