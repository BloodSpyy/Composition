package com.bloodspy.composition.domain.usecases

import com.bloodspy.composition.domain.entity.GameSettings
import com.bloodspy.composition.domain.entity.Level
import com.bloodspy.composition.domain.repository.GameRepository

class GetGameSettingsUseCase (
    private val gameRepository: GameRepository
) {
    operator fun invoke(level: Level): GameSettings = gameRepository.getGameSettings(level)
}