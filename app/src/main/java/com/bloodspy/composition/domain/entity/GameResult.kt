package com.bloodspy.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameResult (
    val isWinner: Boolean,
    val countAnswers: Int,
    val countOfRightAnswers: Int,
    val gameSettings: GameSettings
) : Parcelable