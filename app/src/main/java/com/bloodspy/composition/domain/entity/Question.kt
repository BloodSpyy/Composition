package com.bloodspy.composition.domain.entity

data class Question(
    val example: Int,
    val visibleNumber: Int,
    val options: Set<Int>
)
