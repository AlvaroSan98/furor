package com.ifun.furor.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import com.ifun.furor.R
import com.ifun.furor.databinding.AnswerFragmentBinding
import com.ifun.furor.viewmodel.GameViewModel

class AnswerFragment: Fragment() {

    private lateinit var binding: AnswerFragmentBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AnswerFragmentBinding.inflate(layoutInflater)
        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.bottomToolbarAnswer.testCheckIv.visibility = View.VISIBLE
        binding.bottomToolbarAnswer.testCheckIv.setOnClickListener {
            gameViewModel.answer(true)
            findNavController().navigate(R.id.action_answerFragment_to_testFragment)
        }

        binding.bottomToolbarAnswer.testFailedIv.visibility = View.VISIBLE
        binding.bottomToolbarAnswer.testFailedIv.setOnClickListener {
            gameViewModel.answer(false)
            findNavController().navigate(R.id.action_answerFragment_to_testFragment)
        }

        binding.bottomToolbarAnswer.nextToolbarIv.visibility = View.INVISIBLE

        binding.answerTv.text = gameViewModel.getCorrectAnswer()
    }

}