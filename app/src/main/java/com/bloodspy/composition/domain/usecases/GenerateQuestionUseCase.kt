package com.bloodspy.composition.domain.usecases

import com.bloodspy.composition.domain.entity.Question
import com.bloodspy.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(maxExampleValue: Int): Question = gameRepository.generateQuestion(
        maxExampleValue,
        COUNT_OF_OPTIONS
    )

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}