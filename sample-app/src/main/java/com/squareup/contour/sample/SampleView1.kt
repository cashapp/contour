package com.squareup.contour.sample

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import com.squareup.contour.ContourLayout
import com.squareup.contour.sample.widget.PaddingAdjusterWidget
import com.squareup.picasso.Picasso
import kotlin.contracts.ExperimentalContracts

@SuppressLint("SetTextI18n", "ViewConstructor")
@ExperimentalContracts
class SampleView1(context: SampleActivity) : ContourLayout(context) {

  private val siskoWisdom: String =
    "The Bajorans who have lived with us on this station, who have worked with us for months, " +
        "who helped us move this station to protect the wormhole, who joined us to explore the " +
        "Gamma Quadrant, who have begun to build the future of Bajor with us. These people know " +
        "that we are neither the enemy nor the devil. We don't always agree. We have some damn " +
        "good fights, in fact. But we always come away from them with a little better " +
        "understanding and appreciation of the other."

  private fun siskoWisdom(amount: Float): String =
    siskoWisdom.substring(0, (siskoWisdom.length * amount.coerceIn(0f, 1f)).toInt())

  private val avatar =
    AvatarImageView(context).apply {
      Picasso.get()
          .load("https://upload.wikimedia.org/wikipedia/en/9/92/BenSisko.jpg")
          .into(this)
      scaleType = ImageView.ScaleType.CENTER_CROP
      paint.strokeWidth = 3f.dip
    }

  private val name =
    TextView(context).apply {
      text = "Ben Sisko"
      setTextColor(White)
      setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
    }

  private val description =
    TextView(context).apply {
      text = siskoWisdom(0.25f)
      setTextColor(White)
      setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
    }

  private val starDate =
    TextView(context).apply {
      text = "Stardate: 23634.1"
      setTextColor(White)
      setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
    }

  private val paddingAdjusterWidget = PaddingAdjusterWidget(context) {
      setPadding(it.left * 2, it.top * 2, it.right * 2, it.bottom * 2)
  }

  init {
    contourHeightOf { paddingAdjusterWidget.bottom() + paddingBottom }

    setBackgroundColor(Blue)
    var animated = false
    setOnClickListener {
      if (animated) {
        context.showNext()
      } else {
        animated = true
        ValueAnimator.ofFloat(0.25f, 1f)
            .apply {
              duration = 1000
              addUpdateListener {
                description.text = siskoWisdom(it.animatedValue as Float)
              }
            }
            .start()
      }
    }
  }

  override fun onInitializeLayout() {
    avatar.layoutBy(
        leftTo {
          parent.left()
        }.widthOf {
          name.width()
        },
        topTo {
          parent.top()
        }.heightOf {
          name.width().toY()
        }
    )
    name.layoutBy(
        leftTo { avatar.left() },
        topTo { avatar.bottom() + 5.dip }
    )
    description.layoutBy(
        leftTo {
          name.right() + 15.dip
        }.rightTo {
          parent.right()
        },
        topTo {
          parent.top()
        }
    )
    starDate.layoutBy(
        rightTo { parent.right() },
        maxOf(
            topTo { description.bottom() + 5.dip },
            bottomTo { name.bottom() }
        )
    )

    paddingAdjusterWidget.layoutBy(
        leftTo { parent.left() }.rightTo { parent.right() },
        topTo { starDate.bottom() + 30.dip }
    )
  }
}
