package com.ifun.furor.view

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ifun.furor.R
import com.ifun.furor.databinding.TestFragmentBinding
import com.ifun.furor.model.Team
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestion
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import com.ifun.furor.model.tests.TestWithQuestionAnswerAndOptions
import com.ifun.furor.viewmodel.GameState
import com.ifun.furor.viewmodel.GameViewModel

class TestFragment: Fragment() {

    private lateinit var binding: TestFragmentBinding
    private lateinit var gameViewModel: GameViewModel
    private val args: TestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestFragmentBinding.inflate(layoutInflater)
        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.getState().observe(viewLifecycleOwner, Observer {
            onStateChanged()
        })

        setUpView()
    }

    private fun onStateChanged() {
        if (gameViewModel.getState().value == GameState.LOADING) {
            gameViewModel.setTeams(args.team1Name, args.team1Players.toList(), args.team2Name, args.team2Players.toList())
            gameViewModel.startGame(requireActivity().applicationContext)
        }
    }

    private fun setUpView() {

        gameViewModel.getTest().observe(viewLifecycleOwner, Observer {
            setUpTestViews(it)
        })

        gameViewModel.getTeam().observe(viewLifecycleOwner, Observer {
            setUpTeamViews(it)
        })
    }

    private fun setUpTestViews(test: Test) {
        setUpTitle(test)
        setUpQuestion(test)
        setUpOptionsViews(test)
        setUpBottomToolbar(test)

        binding.testPointsTv.text = getString(R.string.points, test.points)
        binding.testPointsTv.visibility = View.VISIBLE

        initListeners(test)
    }

    private fun setUpTeamViews(team: Team) {
        binding.topToolbar.currentTeamTv.text = team.getName()
        binding.topToolbar.teamPointsTv.text = team.getPoints().toString()
        if (gameViewModel.isCurrentTeamWinning()) {
            binding.topToolbar.gameRankingTv.text = "1º"
        } else {
            binding.topToolbar.gameRankingTv.text = "2º"
        }
    }

    private fun initListeners(test: Test) {
        binding.bottomToolbar.nextToolbarIv.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment_to_answerFragment2)
        }

        binding.bottomToolbar.testCheckIv.setOnClickListener {
            gameViewModel.answer(true)
        }

        binding.bottomToolbar.testFailedIv.setOnClickListener {
            gameViewModel.answer(false)
        }

        if (test is TestWithQuestionAndAnswer) {
            if (test.question.endsWith("un_sec")) {
                val id = resources.getIdentifier(test.question, "raw", activity?.applicationContext?.packageName)
                val mediaPlayer = MediaPlayer.create(activity?.applicationContext, id)
                binding.testResourceIv.setOnClickListener {
                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                    }
                }
            }
        }

        binding.option1Tv.setOnClickListener(onOptionClickListener(0, test, binding.option1Tv))
        binding.option2Tv.setOnClickListener(onOptionClickListener(1, test, binding.option2Tv))
        binding.option3Tv.setOnClickListener(onOptionClickListener(2, test, binding.option3Tv))
        binding.option4Tv.setOnClickListener(onOptionClickListener(3, test, binding.option4Tv))
    }

    private fun onOptionClickListener(optionSelected: Int, test: Test, view: View): OnClickListener {
        return object: OnClickListener {
            override fun onClick(v: View?) {
                test as TestWithQuestionAnswerAndOptions
                if (test.options[optionSelected] == test.answer) {
                    view.setBackgroundColor(resources.getColor(R.color.correct_option))
                }
                else {
                    view.setBackgroundColor(resources.getColor(R.color.incorrect_option))
                }
            }
        }
    }

    private fun setUpBottomToolbar(test: Test) {
        if (test is TestWithQuestionAndAnswer) {
            binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
            binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
            binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
        } else {
            binding.bottomToolbar.nextToolbarIv.visibility = View.INVISIBLE
            binding.bottomToolbar.testFailedIv.visibility = View.VISIBLE
            binding.bottomToolbar.testCheckIv.visibility = View.VISIBLE
        }
    }

    private fun setUpOptionsViews(test: Test) {
        if (test is TestWithQuestionAnswerAndOptions) {
            binding.option1Tv.visibility = View.VISIBLE
            binding.option2Tv.visibility = View.VISIBLE
            binding.option1Tv.text = test.options[0]
            binding.option2Tv.text = test.options[1]
            if (test.options.size > 2) {
                binding.option3Tv.visibility = View.VISIBLE
                binding.option4Tv.visibility = View.VISIBLE
                binding.option3Tv.text = test.options[2]
                binding.option4Tv.text = test.options[3]
            } else {
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
            }
        } else {
            binding.option1Tv.visibility = View.GONE
            binding.option2Tv.visibility = View.GONE
            binding.option3Tv.visibility = View.GONE
            binding.option4Tv.visibility = View.GONE
        }
    }

    private fun setUpQuestion(test: Test) {
        if (test is TestWithQuestionAndAnswer) {
            if (!test.resourced) {
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.testQuestionTv.text = test.question
            } else {
                binding.testQuestionTv.visibility = View.INVISIBLE
                binding.testResourceIv.visibility = View.VISIBLE
                if (test.question.endsWith("sec")) {
                    binding.testResourceIv.setImageResource(R.drawable.icon_play)
                } else {
                    val id = resources.getIdentifier(test.question, "drawable", activity?.applicationContext?.packageName)
                    binding.testResourceIv.setImageResource(id)
                    binding.testResourceIv.scaleX
                }
            }
        } else if (test is TestWithQuestion) {
            binding.testQuestionTv.text = test.question
            binding.testResourceIv.visibility = View.INVISIBLE
            binding.testQuestionTv.visibility = View.VISIBLE
        }
    }

    private fun setUpTitle(test: Test) {
        binding.testTitleTv.visibility = View.VISIBLE
        when (test.type) {
            TestType.TITLE_SONG_TEXT -> {
                binding.testTitleTv.text = getString(R.string.title_song_text)
            }
            TestType.AUTHOR_SONG_TEXT -> {
                binding.testTitleTv.text = getString(R.string.author_song_text)
            }
            TestType.CONTINUE_SONG_TEXT -> {
                binding.testTitleTv.text = getString(R.string.continue_song_text)
            }
            TestType.SONGS_OF_AUTHOR -> {
                binding.testTitleTv.text = getString(R.string.songs_of_author)
            }
            TestType.MIME -> {
                binding.testTitleTv.text = getString(R.string.mime)
            }
            TestType.THE_STRANGE_ONE -> {
                binding.testTitleTv.text = getString(R.string.the_strange_one)
            }
            TestType.THE_OLDEST -> {
                binding.testTitleTv.text = getString(R.string.the_oldest)
            }
            TestType.POTPURRI -> {
                binding.testTitleTv.text = getString(R.string.potpurri)
            }
            TestType.TITLE_SONG_EMOJIS -> {
                binding.testTitleTv.text = getString(R.string.title_song_emojis)
            }
            TestType.SONG_SOUND -> {
                binding.testTitleTv.text = getString(R.string.song_sound)
            }
            TestType.CURIOSITY -> {
                binding.testTitleTv.text = getString(R.string.curiosity)
            }
        }
    }
}