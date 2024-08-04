package com.bloodspy.composition.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bloodspy.composition.R
import com.bloodspy.composition.databinding.FragmentGameFinishedBinding
import com.bloodspy.composition.domain.entity.GameResult
import java.util.Locale

class GameFinishedFragment : Fragment() {
    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListeners()
        bindViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun bindViews() {
        binding.gameResult = args.gameResult
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    private fun setupOnClickListeners() {
        binding.buttonTryAgain.setOnClickListener {
            retryGame()
        }
    }
}