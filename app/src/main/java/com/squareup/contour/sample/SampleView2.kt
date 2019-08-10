package com.squareup.contour.sample

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.text.TextUtils.TruncateAt
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import com.squareup.contour.ContourLayout
import com.squareup.contour.SizeMode.AtMost
import com.squareup.picasso.Picasso
import kotlin.contracts.ExperimentalContracts

@SuppressLint("ViewConstructor")
@ExperimentalContracts
class SampleView2(context: SampleActivity) : ContourLayout(context) {

  private val names = listOf(
      "Ben Sisko",
      "Captain Ben Sisko",
      "Commanding Officer Captain Ben Sisko"
  )

  private val avatar =
    AvatarImageView(context).apply {
      scaleType = ImageView.ScaleType.CENTER_CROP
      Picasso.get()
          .load("https://upload.wikimedia.org/wikipedia/en/9/92/BenSisko.jpg")
          .into(this)
      paint.strokeWidth = 3f.dip
    }

  private val name =
    TextView(context).apply {
      text = "Ben Sisko"
      setSingleLine()
      ellipsize = TruncateAt.END
      setTextColor(White)
      setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
    }

  private val checkmark =
    ImageView(context).apply {
      setImageResource(R.drawable.check_mark)
    }

  init {
    setBackgroundColor(Blue)
    heightOf { avatar.height() + 30.dip }

    var animated = false
    setOnClickListener {
      if (animated) {
        context.showNext()
      } else {
        animated = true
        ValueAnimator.ofFloat(0f, 1f)
            .apply {
              duration = 1000
              addUpdateListener {
                val t = it.animatedValue as Float
                val size = names.size
                name.text = names[(size * t).toInt().coerceAtMost(size - 1)]
                name.requestLayout()
              }
            }
            .start()
      }
    }
  }

  override fun onInitializeLayout() {
    avatar.applyLayout(
        leftTo {
          parent.left() + 15.dip
        }.widthOf {
          50.xdip
        },
        topTo {
          parent.top() + 15.dip
        }.heightOf {
          50.ydip
        }
    )
    name.applyLayout(
        leftTo {
          avatar.right() + 15.dip
        }.rightTo(AtMost) {
          parent.width() - checkmark.width() - 30.dip
        },
        centerVerticallyTo { parent.centerY() }
    )
    checkmark.applyLayout(
        minOf(
            leftTo { name.right() + 15.dip },
            rightTo { parent.width() - 15.dip }
        ),
        centerVerticallyTo { name.centerY() }
    )
  }
}
