package com.bloodspy.composition.domain.entity

data class GameSettings(
    val maxExampleValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)
