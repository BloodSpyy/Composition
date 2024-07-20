package com.bloodspy.composition.domain.entity

data class GameSettings(
    val gameTimeInSeconds: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val maxExampleValue: Int,
)
