package com.ifun.furor.view

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
        binding.testTitleTv.text = when (test.type) {
            TestType.TITLE_SONG_TEXT -> getString(R.string.title_song_text)
            TestType.AUTHOR_SONG_TEXT -> getString(R.string.author_song_text)
            TestType.CONTINUE_SONG_TEXT -> getString(R.string.continue_song_text)
            TestType.SONGS_OF_AUTHOR -> getString(R.string.songs_of_author)
            TestType.MIME -> getString(R.string.mime)
            TestType.THE_STRANGE_ONE -> getString(R.string.the_strange_one)
            TestType.THE_OLDEST -> getString(R.string.the_oldest)
            TestType.POTPURRI -> getString(R.string.potpurri)
            TestType.TITLE_SONG_EMOJIS -> getString(R.string.title_song_emojis)
            TestType.SONG_SOUND -> getString(R.string.song_sound)
            TestType.CURIOSITY -> getString(R.string.curiosity)
        }
        if (test is TestWithQuestion) {
            if (!test.resourced) {
                binding.testQuestionTv.text = test.question
            }
        }
        binding.testPointsTv.text = getString(R.string.points, test.points)
    }

    private fun setUpTeamViews(team: Team) {
        binding.topToolbar.currentTeamTv.text = team.getName()
        binding.topToolbar.teamPointsTv.text = team.getPoints().toString()
    }

}