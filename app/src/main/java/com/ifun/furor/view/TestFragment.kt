package com.ifun.furor.view

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ifun.furor.R
import com.ifun.furor.databinding.TestFragmentBinding
import com.ifun.furor.model.Team
import com.ifun.furor.model.enums.TestState
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestion
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import com.ifun.furor.model.tests.TestWithQuestionAnswerAndOptions
import com.ifun.furor.model.enums.GameState
import com.ifun.furor.viewmodel.GameViewModel

class TestFragment: Fragment() {

    private lateinit var binding: TestFragmentBinding
    private lateinit var gameViewModel: GameViewModel
    private val args: TestFragmentArgs by navArgs()
    private lateinit var countDownTimer: CountDownTimer
    private var isRunning = false

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

        gameViewModel.getGameState().observe(viewLifecycleOwner, Observer {
            onStateChanged()
        })

        setUpView()
    }

    private fun onStateChanged() {
        if (gameViewModel.getGameState().value == GameState.LOADING) {
            gameViewModel.setTeams(args.team1Name, args.team1Players.toList(), args.team2Name, args.team2Players.toList())
            gameViewModel.startGame(requireActivity().applicationContext)
        }
    }

    private fun setUpView() {
        gameViewModel.getTestState().observe(viewLifecycleOwner, Observer {
            setUpBottomToolbar(it)
            setUpPointsView(it)
            if (it == TestState.ANSWER) {
                cancelTimerIfIsRunning()
            }
        })

        gameViewModel.getTest().observe(viewLifecycleOwner, Observer {
            cancelTimerIfIsRunning()
            setUpTestViews(it)
        })

        gameViewModel.getTeam().observe(viewLifecycleOwner, Observer {
            setUpTeamViews(it)
        })
    }

    private fun setUpPointsView(state: TestState?) {
        if (state == TestState.QUESTION) {
            binding.testPointsTv.visibility = View.VISIBLE
            binding.ovalPointsIv.visibility = View.VISIBLE
        } else {
            binding.testPointsTv.visibility = View.INVISIBLE
            binding.ovalPointsIv.visibility = View.INVISIBLE
        }
    }

    private fun setUpTestViews(test: Test) {
        setUpTitle(test)
        setUpQuestion(test)
        setUpOptionsViews(test)
        startTimer(test)

        binding.testAnswerTv.visibility = View.INVISIBLE
        if (test is TestWithQuestionAndAnswer) {
            binding.testAnswerTv.text = test.answer
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

        val color: Int = if (team.getName() == args.team1Name) {
            R.color.team_1_color
        } else {
            R.color.team_2_color
        }
        binding.mainTestLayout.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, color))
    }

    private fun initListeners(test: Test) {
        binding.ovalLensIv.setOnClickListener {
            binding.ovalLensIv.visibility = View.INVISIBLE
            binding.lensIv.visibility = View.INVISIBLE
            binding.testAnswerTv.visibility = View.VISIBLE
            gameViewModel.changeTestState(TestState.ANSWER)
        }

        binding.bottomToolbar.testCheckIv.setOnClickListener {
            cancelTimerIfIsRunning()
            startPointsAddedAnimation(test, true)
        }

        binding.bottomToolbar.testFailedIv.setOnClickListener {
            cancelTimerIfIsRunning()
            startPointsAddedAnimation(test, false)
        }

        binding.testInfoIv.setOnClickListener {
            showInfoDialog(test)
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

        binding.option1Tv.setOnClickListener(onOptionClickListener(0, binding.option1Tv, test))
        binding.option2Tv.setOnClickListener(onOptionClickListener(1, binding.option2Tv, test))
        binding.option3Tv.setOnClickListener(onOptionClickListener(2, binding.option3Tv, test))
        binding.option4Tv.setOnClickListener(onOptionClickListener(3, binding.option4Tv, test))
    }

    private fun showInfoDialog(test: Test) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.test_info_dialog)

        dialog.window?.setLayout(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        val infoText = dialog.findViewById<TextView>(R.id.test_info_tv)
        infoText.text = getTestInformation(test)

        dialog.setCanceledOnTouchOutside(true)

        dialog.show()
    }

    private fun getTestInformation(test: Test): String {
        return when (test.type) {
            TestType.CONTINUE_SONG_TEXT -> getString(R.string.continue_song_text_info)
            TestType.TITLE_SONG_TEXT -> getString(R.string.title_song_text_info)
            TestType.AUTHOR_SONG_TEXT -> getString(R.string.author_song_text_info)
            TestType.SONGS_OF_AUTHOR -> getString(R.string.songs_of_author_info)
            TestType.MIME -> getString(R.string.mime_info)
            TestType.THE_STRANGE_ONE -> getString(R.string.the_strange_one_info)
            TestType.THE_OLDEST -> getString(R.string.the_oldest_info)
            TestType.SONG_SOUND -> getString(R.string.song_sound_info)
            TestType.POTPURRI -> getString(R.string.potpurri_info)
            TestType.TITLE_SONG_EMOJIS -> getString(R.string.title_song_emojis)
            TestType.CURIOSITY -> getString(R.string.curiosity_info)
        }
    }

    private fun onOptionClickListener(optionSelected: Int, view: View, test: Test): OnClickListener {
        return OnClickListener {
            val correctOption = gameViewModel.getCorrectOption()
            var selectedIsCorrect = false
            if (optionSelected == correctOption) {
                view.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.correct_option))
                selectedIsCorrect = true
            } else {
                paintAllOptions(correctOption)
            }
            cancelTimerIfIsRunning()
            startPointsAddedAnimation(test, selectedIsCorrect)
        }
    }

    private fun paintAllOptions(correctOption: Int) {
        when (correctOption) {
            0 -> {
                binding.option1Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.correct_option))
                binding.option2Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option3Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option4Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
            }
            1 -> {
                binding.option1Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option2Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.correct_option))
                binding.option3Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option4Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
            }
            2 -> {
                binding.option1Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option2Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option3Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.correct_option))
                binding.option4Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
            }
            3 -> {
                binding.option1Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option2Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option3Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.incorrect_option))
                binding.option4Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.correct_option))
            }
            else -> {
                binding.option1Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.option_not_selected))
                binding.option2Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.option_not_selected))
                binding.option3Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.option_not_selected))
                binding.option4Tv.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.option_not_selected))
            }
        }
    }

    private fun setUpBottomToolbar(state: TestState?) {
        if (state == TestState.QUESTION) {
            binding.bottomToolbar.testFailedIv.visibility = View.GONE
            binding.bottomToolbar.ovalFailedIv.visibility = View.GONE
            binding.bottomToolbar.testCheckIv.visibility = View.GONE
            binding.bottomToolbar.ovalCorrectIv.visibility = View.GONE
        } else {
            binding.bottomToolbar.testFailedIv.visibility = View.VISIBLE
            binding.bottomToolbar.testCheckIv.visibility = View.VISIBLE
            binding.bottomToolbar.ovalCorrectIv.visibility = View.VISIBLE
            binding.bottomToolbar.ovalFailedIv.visibility = View.VISIBLE
        }
    }

    private fun setUpOptionsViews(test: Test) {
        if (test is TestWithQuestionAnswerAndOptions) {
            binding.bottomToolbar.mainBottomToolbarLayout.visibility = View.GONE
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
                    binding.testResourceIv.scaleType = ImageView.ScaleType.CENTER_INSIDE
                } else {
                    val id = resources.getIdentifier(test.question, "drawable", activity?.applicationContext?.packageName)
                    binding.testResourceIv.setImageResource(id)
                    binding.testResourceIv.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }
            if (test !is TestWithQuestionAnswerAndOptions) {
                binding.ovalLensIv.visibility = View.VISIBLE
                binding.lensIv.visibility = View.VISIBLE
            } else {
                binding.ovalLensIv.visibility = View.INVISIBLE
                binding.lensIv.visibility = View.INVISIBLE
            }
        } else if (test is TestWithQuestion) {
            binding.testQuestionTv.text = test.question
            binding.testResourceIv.visibility = View.INVISIBLE
            binding.testQuestionTv.visibility = View.VISIBLE
            binding.ovalLensIv.visibility = View.INVISIBLE
            binding.lensIv.visibility = View.INVISIBLE
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

    private fun startTimer(test: Test) {
        if (test.getTimeMillis().toInt() != 0) {
            binding.testTimePb.visibility = View.VISIBLE
            countDownTimer = object : CountDownTimer(test.getTimeMillis(), 50) {
                override fun onTick(millisUntilFinished: Long) {
                    isRunning = true
                    binding.testTimePb.progress =
                        ((millisUntilFinished * 100) / test.getTimeMillis()).toInt()
                }

                override fun onFinish() {
                    binding.testTimePb.progress = 0
                    isRunning = false
                }

            }
            countDownTimer.start()
        }
    }

    private fun cancelTimerIfIsRunning() {
        if (isRunning) {
            countDownTimer.cancel()
            binding.testTimePb.visibility = View.INVISIBLE
            isRunning = false
        }
    }
    private fun startPointsAddedAnimation(test: Test, correct: Boolean) {
        val animation = AnimationUtils.loadAnimation(requireActivity().applicationContext, R.anim.fade_out)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                if (correct) {
                    binding.topToolbar.addedPointsTv.text = "+${test.points}"
                    binding.topToolbar.addedPointsTv.visibility = View.VISIBLE

                    val currentPoints = binding.topToolbar.teamPointsTv.text.toString().toInt()
                    binding.topToolbar.teamPointsTv.text = (currentPoints + test.points).toString()
                } else {
                    binding.topToolbar.addedPointsTv.text = "+0"
                }
                binding.bottomToolbar.testCheckIv.visibility = View.INVISIBLE
                binding.bottomToolbar.ovalCorrectIv.visibility = View.INVISIBLE
                binding.bottomToolbar.testFailedIv.visibility = View.INVISIBLE
                binding.bottomToolbar.ovalFailedIv.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.topToolbar.addedPointsTv.visibility = View.INVISIBLE
                gameViewModel.answer(correct)

                if (test is TestWithQuestionAnswerAndOptions) {
                    paintAllOptions(-1)
                    binding.bottomToolbar.mainBottomToolbarLayout.visibility = View.VISIBLE
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {
                Log.e("TestFragment", "Repeat animation error")
            }

        })
        binding.topToolbar.addedPointsTv.startAnimation(animation)
    }
}