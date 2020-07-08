package com.squareup.contour.sample.widget

import android.annotation.SuppressLint
import android.graphics.Rect
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import com.squareup.contour.ContourLayout
import com.squareup.contour.sample.SampleActivity

@SuppressLint("ViewConstructor")
internal class PaddingAdjusterWidget(
    context: SampleActivity,
    private val onPaddingAdjusted: (Rect) -> Unit
): ContourLayout(context) {

    private val onChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onPaddingAdjusted(Rect(
                leftSeek.progress,
                topSeek.progress,
                rightSeek.progress,
                bottomSeek.progress
            ))
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
        override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
    }

    private val leftLabel = AppCompatTextView(context).apply {
        text = "Left"
    }
    private val topLabel = AppCompatTextView(context).apply {
        text = "Top"
    }
    private val rightLabel = AppCompatTextView(context).apply {
        text = "Right"
    }
    private val bottomLabel = AppCompatTextView(context).apply {
        text = "Bottom"
    }

    private val leftSeek: AppCompatSeekBar = AppCompatSeekBar(context).apply {
        setOnSeekBarChangeListener(onChangeListener)
    }
    private val topSeek: AppCompatSeekBar  = AppCompatSeekBar(context).apply {
        setOnSeekBarChangeListener(onChangeListener)
    }
    private val rightSeek: AppCompatSeekBar  = AppCompatSeekBar(context).apply {
        setOnSeekBarChangeListener(onChangeListener)
    }
    private val bottomSeek: AppCompatSeekBar  = AppCompatSeekBar(context).apply {
        setOnSeekBarChangeListener(onChangeListener)
    }

    init {
        setPadding(
            15.dip,
            10.dip,
            15.dip,
            10.dip
        )

        leftLabel.layoutBy(
            x = leftTo { parent.left() },
            y = topTo { parent.top() }
        )

        topLabel.layoutBy(
            x = leftTo { parent.left() },
            y = topTo { leftLabel.bottom() + 10.dip }
        )

        rightLabel.layoutBy(
            x = leftTo { parent.left() },
            y = topTo { topLabel.bottom() + 10.dip }
        )

        bottomLabel.layoutBy(
            x = leftTo { parent.left() },
            y = topTo { rightLabel.bottom() + 10.dip }
        )

        leftSeek.layoutBy(
            x = leftTo { leftLabel.right() + 20.dip }
                .rightTo { parent.right() },
            y = centerVerticallyTo { leftLabel.centerY() }
        )

        topSeek.layoutBy(
            x = leftTo { topLabel.right() + 20.dip }
                .rightTo { parent.right() },
            y = centerVerticallyTo { topLabel.centerY() }
        )

        rightSeek.layoutBy(
            x = leftTo { rightLabel.right() + 20.dip }
                .rightTo { parent.right() },
            y = centerVerticallyTo { rightLabel.centerY() }
        )

        bottomSeek.layoutBy(
            x = leftTo { bottomLabel.right() + 20.dip }
                .rightTo { parent.right() },
            y = centerVerticallyTo { bottomLabel.centerY() }
        )

        contourHeightOf { maxOf(bottomSeek.bottom(), bottomLabel.bottom()) + paddingBottom }
    }
}