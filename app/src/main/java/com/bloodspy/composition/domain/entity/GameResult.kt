package com.bloodspy.composition.domain.entity

data class GameResult (
    val isWinner: Boolean,
    val countAnswers: Int,
    val countOfRightAnswers: Int,
    val gameSettings: GameSettings
)