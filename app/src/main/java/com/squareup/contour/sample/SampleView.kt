package com.squareup.contour.sample

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.squareup.contour.*
import kotlin.contracts.ExperimentalContracts


@SuppressLint("SetTextI18n")
@ExperimentalContracts
class SampleView(context: Context) : ContourLayout(context) {

    private var animatedWidth = 0.5f

    private val description =
        TextView(context).apply {
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas porttitor nisi lorem, " +
                    "in ultricies justo condimentum a. Aliquam in pellentesque nisl. Phasellus tristique " +
                    "justo ligula, vel sollicitudin orci tempus nec. Quisque quis hendrerit turpis. Mauris " +
                    "vitae lectus eu odio blandit iaculis. Ut massa erat, gravida id magna a, auctor blandit " +
                    "lorem. Maecenas tempor justo quis dolor blandit, non dictum eros pharetra. Maecenas " +
                    "enim elit, molestie et odio quis, tincidunt pulvinar massa. Nulla dapibus porta " +
                    "posuere. Etiam fringilla vulputate erat nec pharetra. Etiam vel vehicula est."
            setBackgroundColor(White)
            setTextColor(Black)
            layoutOf(
                rightTo {
                    parent.right() - 10.dip
                } widthOf {
                    parent.width(animatedWidth)
                },
                topTo {
                    parent.top() + 10.dip
                }
            )
        }.alsoAddTo(this)

    private val stack = verticalStack(verticallyCenterTo { parent.centerY() })

    private val header =
        TextView(context).apply {
            text = "MY HEADER!"
            setTextColor(White)
            setBackgroundColor(Red)
            layoutOf(
                horizontallyCenterTo { description.left() / 2 },
                addToGroup { stack }
            )
        }.alsoAddTo(this)


    private val icon =
        ImageView(context).apply {
            setImageResource(R.drawable.android_logo)
            setBackgroundColor(Yellow)
            setColorFilter(White)
            layoutOf(
                horizontallyCenterTo {
                    description.left() / 2
                } widthOf {
                    header.width()
                },
                addToGroup {
                    stack
                } heightOf {
                    header.width().toY()
                }
            )
        }.alsoAddTo(this)

    init {
        heightOf { description.bottom() + 10.dip }

        setBackgroundColor(Blue)
        setOnClickListener {
            runAnimation(0.5f, 0.7f) {
                animatedWidth = it
                requestLayout()
            }
        }
    }

    private fun runAnimation(from: Float, to: Float, update: (Float) -> Unit) {
        ValueAnimator.ofFloat(from, to).apply {
            interpolator = OvershootInterpolator()
            duration = 1000
            addUpdateListener {
                update(it.animatedValue as Float)
            }
        }.start()
    }
}
