package com.ifun.furor.view

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ifun.furor.R
import com.ifun.furor.databinding.TestFragmentBinding
import com.ifun.furor.model.Team
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestion
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import com.ifun.furor.model.tests.TestWithQuestionAnswerAndOptions
import com.ifun.furor.viewmodel.GameViewModel

class TestFragment: Fragment() {

    private lateinit var binding: TestFragmentBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestFragmentBinding.inflate(layoutInflater)
        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        gameViewModel.startGame(requireActivity().applicationContext)
    }

    private fun setUpView() {
        gameViewModel.setTeams("POLLOSHERMANOS", listOf("Alvaro", "Javi", "Sergio"), "SUPERMEMAS", listOf("Sara", "Marta", "Lara"))

        gameViewModel.getTest().observe(viewLifecycleOwner, Observer {
            setUpTestViews(it)
        })

        gameViewModel.getTeam().observe(viewLifecycleOwner, Observer {
            setUpTeamViews(it)
        })
    }

    private fun setUpTestViews(test: Test) {
        binding.testTitleTv.visibility = View.VISIBLE
        when (test.type) {
            TestType.TITLE_SONG_TEXT -> {
                binding.testTitleTv.text = getString(R.string.title_song_text)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
            }
            TestType.AUTHOR_SONG_TEXT -> {
                binding.testTitleTv.text = getString(R.string.author_song_text)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
            }
            TestType.CONTINUE_SONG_TEXT -> {
                binding.testTitleTv.text = getString(R.string.continue_song_text)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
            }
            TestType.SONGS_OF_AUTHOR -> {
                binding.testTitleTv.text = getString(R.string.songs_of_author)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.VISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.VISIBLE
            }
            TestType.MIME -> {
                binding.testTitleTv.text = getString(R.string.mime)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.VISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.VISIBLE
            }
            TestType.THE_STRANGE_ONE -> {
                binding.testTitleTv.text = getString(R.string.the_strange_one)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.VISIBLE
                binding.option2Tv.visibility = View.VISIBLE
                binding.option3Tv.visibility = View.VISIBLE
                binding.option4Tv.visibility = View.VISIBLE
                binding.bottomToolbar.nextToolbarIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.VISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.VISIBLE
            }
            TestType.THE_OLDEST -> {
                binding.testTitleTv.text = getString(R.string.the_oldest)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.VISIBLE
                binding.option2Tv.visibility = View.VISIBLE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
            }
            TestType.POTPURRI -> {
                binding.testTitleTv.text = getString(R.string.potpurri)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.VISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.VISIBLE
            }
            TestType.TITLE_SONG_EMOJIS -> {
                binding.testTitleTv.text = getString(R.string.title_song_emojis)
                binding.testQuestionTv.visibility = View.INVISIBLE
                binding.testResourceIv.visibility = View.VISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
            }
            TestType.SONG_SOUND -> {
                binding.testTitleTv.text = getString(R.string.song_sound)
                binding.testQuestionTv.visibility = View.INVISIBLE
                binding.testResourceIv.setImageResource(R.drawable.icon_play)
                binding.testResourceIv.visibility = View.VISIBLE
                binding.option1Tv.visibility = View.GONE
                binding.option2Tv.visibility = View.GONE
                binding.option3Tv.visibility = View.GONE
                binding.option4Tv.visibility = View.GONE
                binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
            }
            TestType.CURIOSITY -> {
                binding.testTitleTv.text = getString(R.string.curiosity)
                binding.testQuestionTv.visibility = View.VISIBLE
                binding.testResourceIv.visibility = View.INVISIBLE
                binding.option1Tv.visibility = View.VISIBLE
                binding.option2Tv.visibility = View.VISIBLE
                binding.option3Tv.visibility = View.VISIBLE
                binding.option4Tv.visibility = View.VISIBLE
                binding.bottomToolbar.nextToolbarIv.visibility = View.VISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
            }
        }

        if (test is TestWithQuestionAndAnswer) {
            if (!test.resourced) {
                binding.testQuestionTv.text = test.question
            } else {
                if (test.question.endsWith("sec")) {
                    binding.testResourceIv.setImageResource(R.drawable.icon_play)
                } else {
                    val id = resources.getIdentifier(test.question, "drawable", activity?.applicationContext?.packageName)
                    binding.testResourceIv.setImageResource(id)
                }
            }
        } else if (test is TestWithQuestion) {
            binding.testQuestionTv.text = test.question
        }

        if (test is TestWithQuestionAnswerAndOptions) {
            binding.option1Tv.text = test.options[0]
            binding.option2Tv.text = test.options[1]
            if (test.type == TestType.CURIOSITY || test.type == TestType.THE_STRANGE_ONE) {
                binding.option3Tv.text = test.options[2]
                binding.option4Tv.text = test.options[3]
            }
        }

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
            getAnswer(test)
        }

        binding.bottomToolbar.testCheckIv.setOnClickListener {
            gameViewModel.answer(true)
        }

        binding.bottomToolbar.testFailedIv.setOnClickListener {
            gameViewModel.answer(false)
        }

        if (binding.testResourceIv.visibility == View.VISIBLE) {
            test as TestWithQuestionAndAnswer
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
    }

    private fun getAnswer(test: Test) {
        test as TestWithQuestionAndAnswer
        binding.testQuestionTv.text = test.answer

        binding.testTitleTv.visibility = View.INVISIBLE
        binding.testResourceIv.visibility = View.INVISIBLE
        binding.testPointsTv.visibility = View.INVISIBLE
        binding.testQuestionTv.visibility = View.VISIBLE
        binding.bottomToolbar.nextToolbarIv.visibility = View.INVISIBLE
        binding.bottomToolbar.testFailedIv.visibility = View.VISIBLE
        binding.bottomToolbar.testCheckIv.visibility = View.VISIBLE
        binding.option1Tv.visibility = View.GONE
        binding.option2Tv.visibility = View.GONE
        binding.option3Tv.visibility = View.GONE
        binding.option4Tv.visibility = View.GONE
    }
}