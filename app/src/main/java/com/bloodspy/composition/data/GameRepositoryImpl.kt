package com.bloodspy.composition.data

import com.bloodspy.composition.domain.entity.GameSettings
import com.bloodspy.composition.domain.entity.Level
import com.bloodspy.composition.domain.entity.Question
import com.bloodspy.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_EXAMPLE_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1
    private const val MIN_VISIBLE_NUMBERS = 1

    override fun generateQuestion(maxExampleValue: Int, countOfOptions: Int): Question {
        val example = Random.nextInt(MIN_EXAMPLE_VALUE, maxExampleValue + 1)
        val visibleNumbers = Random.nextInt(MIN_VISIBLE_NUMBERS, example)
        val rightAnswer = example - visibleNumbers
        val options = HashSet<Int>()
        options.add(rightAnswer)

        val fromForRandom = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val toForRandom = min(maxExampleValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(fromForRandom, toForRandom))
        }

        return Question(example, visibleNumbers, options)
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }

            Level.EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }

            Level.NORMAL -> {
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }

            Level.HARD -> {
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }
}