package com.squareup.contour.sample

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import com.squareup.contour.*
import com.squareup.picasso.Picasso
import kotlin.contracts.ExperimentalContracts


@SuppressLint("SetTextI18n")
@ExperimentalContracts
class SampleView(context: Context) : ContourLayout(context) {

    private fun siskoWisdom(): String =
        "The Bajorans who have lived with us on this station, who have worked with us for months, who helped us move " +
                "this station to protect the wormhole, who joined us to explore the Gamma Quadrant, who have begun " +
                "to build the future of Bajor with us. These people know that we are neither the enemy nor the " +
                "devil. We don't always agree. We have some damn good fights, in fact. But we always come away from " +
                "them with a little better understanding and appreciation of the other."

    private val avatar =
        AvatarImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/en/9/92/BenSisko.jpg")
                .into(this)
            layoutOf(
                leftTo {
                    parent.left() + 15.dip
                }.widthOf {
                    name.width()
                },
                topTo {
                    parent.top() + 15.dip
                }.heightOf {
                    name.width().toY()
                }
            )
        }


    private val name: TextView =
        TextView(context).apply {
            text = "Ben Sisko"
            setTextColor(White)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
            layoutOf(
                leftTo { avatar.left() },
                topTo { avatar.bottom() + 5.dip }

            )
        }

    private val description =
        TextView(context).apply {
            text = siskoWisdom()
            setTextColor(White)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
            layoutOf(
                leftTo {
                    name.right() + 15.dip
                }.rightTo {
                    parent.right() - 15.dip
                },
                topTo {
                    parent.top() + 15.dip
                }
            )
        }

    init {
        heightOf { description.bottom() + 14.dip }

        addView(name)
        addView(avatar)
        addView(description)

        setBackgroundColor(Blue)
        setOnClickListener {

        }
    }
}
