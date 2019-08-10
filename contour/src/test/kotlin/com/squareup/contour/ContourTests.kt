package com.squareup.contour

import android.app.Activity
import android.view.View
import com.google.common.truth.Truth.assertThat
import com.squareup.contour.utils.*
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
            plainOldView.applyLayout(
                leftTo { parent.left() }.rightTo { parent.right() },
                topTo { parent.top() }.bottomTo { parent.bottom() }
            )
        }

        assertThat(plainOldView.width).isEqualTo(200)
        assertThat(plainOldView.height).isEqualTo(50)
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
            fakeImageView.applyLayout(
                leftTo { parent.left() }.widthOf { parent.height().toX() },
                topTo { parent.top() }.heightOf { parent.height() }
            )
            fakeTextView.applyLayout(
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
            fakeTextView.applyLayout(
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
            view0.applyLayout(
                maxOf(leftTo { x0 }, leftTo { x1 }),
                topTo { parent.top() }
            )
            view1.applyLayout(
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
            view0.applyLayout(
                leftTo { parent.left() },
                maxOf(topTo { y0 }, topTo { y1 })
            )
            view1.applyLayout(
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
            view.applyLayout(
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
            view.applyLayout(
                leftTo { parent.left() }.widthOfFloat { parent.width() * amount },
                centerVerticallyTo { parent.centerY() }
            )
        }

        assertThat(view.width).isEqualTo(26) // 260 * 0.1

        amount = 0.13f
        layout.forceRelayout()
        assertThat(view.width).isEqualTo(33) // 260 * 0.13 = 33.8 ~= 33 floored
    }
}