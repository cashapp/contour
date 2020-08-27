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

@SuppressLint("ViewConstructor")
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

  private val name: TextView =
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
    contourHeightOf { avatar.height() + 30.dip }

    avatar.layoutBy(
        x = leftTo { parent.left() + 15.dip }.widthOf { 50.xdip },
        y = topTo { parent.top() + 15.dip }.heightOf { 50.ydip }
    )
    name.layoutBy(
        x = leftTo { avatar.right() + 15.dip }
            .rightTo(AtMost) { parent.width() - checkmark.width() - 30.dip },
        y = centerVerticallyTo { parent.centerY() }
    )
    checkmark.layoutBy(
        x = minOf(
            leftTo { name.right() + 15.dip },
            rightTo { parent.width() - 15.dip }
        ),
        y = centerVerticallyTo { name.centerY() }
    )

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
              }
            }
            .start()
      }
    }
  }
}
