package com.bloodspy.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bloodspy.composition.R
import com.bloodspy.composition.domain.entity.GameResult
import java.util.Locale

@BindingAdapter("requiredAnswer")
fun bindRequiredAnswer(textView: TextView, minCountOfRightAnswers: Int) {
    textView.text =
        String.format(
            Locale.getDefault(),
            textView.context.getString(R.string.required_answer),
            minCountOfRightAnswers.toString()
        )
}

@BindingAdapter("score")
fun bindScore(textView: TextView, countOfRightAnswers: Int) {
    textView.text =
        String.format(
            Locale.getDefault(),
            textView.context.getString(R.string.your_score),
            countOfRightAnswers.toString()
        )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, minPercentOfRightAnswers: Int) {
    textView.text =
        String.format(
            Locale.getDefault(),
            textView.context.getString(R.string.required_percent),
            minPercentOfRightAnswers.toString()
        )
}

@BindingAdapter("percent")
fun bindPercent(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        Locale.getDefault(),
        textView.context.getString(R.string.your_percent),
        calculatePercentOfRightAnswer(gameResult).toString()
    )
}

@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, isWinner: Boolean) {
    imageView.setImageResource(getSmileResId(isWinner))
}

@BindingAdapter("answerProgressColor")
fun bindAnswerProgressColor(textView: TextView, isEnoughCount: Boolean) {
    val color = getColorByState(textView.context, isEnoughCount)

    textView.setTextColor(color)
}

@BindingAdapter("progressBarColor")
fun bindProgressBarColor(progressBar: ProgressBar, isEnoughPercent: Boolean) {
    val color = getColorByState(progressBar.context, isEnoughPercent)

    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberIntoText")
fun numberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}


private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }

    return ContextCompat.getColor(
        context,
        colorId
    )
}

private fun calculatePercentOfRightAnswer(gameResult: GameResult): Int {
    with(gameResult) {
        return if (countAnswers == 0) {
            0
        } else {
            ((countOfRightAnswers / countAnswers.toDouble()) * 100).toInt()
        }
    }
}

private fun getSmileResId(isWinner: Boolean): Int {
    return if (isWinner) {
        R.drawable.ic_funny_smile
    } else {
        R.drawable.ic_sad_smile
    }
}
