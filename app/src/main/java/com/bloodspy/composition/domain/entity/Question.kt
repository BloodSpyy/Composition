package com.bloodspy.composition.domain.entity

data class Question(
    val example: Int,
    val visibleNumber: Int,
    val options: List<Int>,
) {
    val correctAnswer: Int
        get() = example - visibleNumber
}
