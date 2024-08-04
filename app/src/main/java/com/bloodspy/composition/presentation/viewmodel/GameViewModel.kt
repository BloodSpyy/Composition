package com.bloodspy.composition.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bloodspy.composition.R
import com.bloodspy.composition.data.GameRepositoryImpl
import com.bloodspy.composition.domain.entity.GameResult
import com.bloodspy.composition.domain.entity.GameSettings
import com.bloodspy.composition.domain.entity.Level
import com.bloodspy.composition.domain.entity.Question
import com.bloodspy.composition.domain.usecases.GenerateQuestionUseCase
import com.bloodspy.composition.domain.usecases.GetGameSettingsUseCase
import java.util.Locale

class GameViewModel(
    private val level: Level,
    private val application: Application
) : ViewModel() {
    private lateinit var gameSettings: GameSettings

    private val gameRepositoryImpl = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepositoryImpl)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepositoryImpl)

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer

    private var timer: CountDownTimer? = null
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _isEnoughCount = MutableLiveData<Boolean>()
    val isEnoughCount: LiveData<Boolean>
        get() = _isEnoughCount

    private val _isEnoughPercent = MutableLiveData<Boolean>()
    val isEnoughPercent: LiveData<Boolean>
        get() = _isEnoughPercent

    private var countOfRightAnswers = INITIAL_INT_VALUE
    private var countOfAnswers = INITIAL_INT_VALUE

    init {
        startGame()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    fun startGame() {
        loadGameSettings()
        startTimer()
        updateProgress()
        generateQuestion()
    }

    fun chooseAnswer(answer: Int) {
        checkAnswer(answer)
        updateProgress()
        generateQuestion()
    }

    private fun stopGame() {
        _gameResult.value = GameResult(
            _isEnoughCount.value == true && _isEnoughPercent.value == true,
            countOfAnswers,
            countOfRightAnswers,
            gameSettings
        )
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                stopGame()
            }
        }
        timer?.start()
    }

    private fun checkAnswer(answer: Int) {
        val correctAnswer = _question.value?.correctAnswer
        if (answer == correctAnswer) {
            countOfRightAnswers++
        }
        countOfAnswers++
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS

        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)

        return String.format(Locale.getDefault(),"%02d:%02d", minutes, leftSeconds)
    }

    private fun loadGameSettings() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun updateProgress() {
        _isEnoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers

        val percentOfRightAnswer = calculatePercentOfRightAnswer()
        _percentOfRightAnswer.value = percentOfRightAnswer
        _isEnoughPercent.value = percentOfRightAnswer >= gameSettings.minPercentOfRightAnswers

        _progressAnswer.value = String.format(
            application.resources.getString(R.string.answer_progress_label),
            countOfRightAnswers.toString(),
            gameSettings.minCountOfRightAnswers.toString()
        )
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxExampleValue)
    }

    private fun calculatePercentOfRightAnswer(): Int {
        return if (countOfRightAnswers == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfAnswers.toDouble()) * 100).toInt()
        }
    }

    companion object {
        private const val INITIAL_INT_VALUE = 0

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}