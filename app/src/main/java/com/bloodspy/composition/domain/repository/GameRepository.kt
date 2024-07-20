package com.bloodspy.composition.domain.repository

import androidx.resourceinspection.annotation.Attribute.IntMap
import com.bloodspy.composition.domain.entity.GameSettings
import com.bloodspy.composition.domain.entity.Level
import com.bloodspy.composition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(maxExampleValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}