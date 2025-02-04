package com.bloodspy.composition.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bloodspy.composition.domain.entity.Level

class GameViewModelFactory(
    private val level: Level,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(level, application) as T
        }

        throw RuntimeException("View model class is unknown $modelClass")
    }
}