package com.ifun.furor.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.ifun.furor.model.enums.GameState
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GameViewModelIntegratedTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var gameViewModel: GameViewModel? = null

    @Before
    fun setUp() {
        gameViewModel = GameViewModel()
    }

    @After
    fun tearDown() {
        gameViewModel = null
    }


    @Test
    fun `create viewModel and check the initial values`() {
        assertThat(gameViewModel?.getTest()?.value, `is`(nullValue()))
        assertThat(gameViewModel?.getTeam()?.value, `is`(nullValue()))
        assertThat(gameViewModel?.getTestState()?.value, `is`(nullValue()))
        assertThat(gameViewModel?.getGameState()?.value, `is`(GameState.LOADING))
    }

    @Test
    fun `start game and check values`() {
        startGameWithBlankValues()
        assertThat(gameViewModel?.getTest()?.value, `is`(notNullValue()))
        assertThat(gameViewModel?.getTeam()?.value, `is`(notNullValue()))
        assertThat(gameViewModel?.getTestState()?.value, `is`(notNullValue()))
        assertThat(gameViewModel?.getGameState()?.value, `is`(GameState.STARTED))
    }

    @Test
    fun `answer and check next test is not the same test`() {
        startGameWithBlankValues()
        val currentTest = gameViewModel?.getTest()
        val nextTest = gameViewModel?.answer(true)
        assertThat(currentTest, `is`(not(nextTest)))
    }

    @Test
    fun `answer correct and check team points are not equal to zero`() {
        startGameWithBlankValues()
        val currentPoints = gameViewModel?.getTeam()?.value?.getPoints()
        gameViewModel?.answer(true)
        gameViewModel?.answer(false)
        val nextPoints = gameViewModel?.getTeam()?.value?.getPoints()
        assertThat(nextPoints, `is`(not(currentPoints)))
    }

    @Test
    fun `answer incorrect and check team points are equal to zero`() {
        startGameWithBlankValues()
        val currentPoints = gameViewModel?.getTeam()?.value?.getPoints()
        gameViewModel?.answer(false)
        gameViewModel?.answer(false)
        val nextPoints = gameViewModel?.getTeam()?.value?.getPoints()
        assertThat(nextPoints, `is`(currentPoints))
    }

    @Test
    fun `answer correct two teams and check if current team is winning`() {
        startGameWithBlankValues()
        gameViewModel?.answer(true)
        gameViewModel?.answer(true)
        val team1Points = gameViewModel?.getTeam()?.value?.getPoints()
        gameViewModel?.answer(false)
        val team2Points = gameViewModel?.getTeam()?.value?.getPoints()
        gameViewModel?.answer(false)
        val isTeam1Winning = team1Points!! >= team2Points!!
        assertThat(gameViewModel?.isCurrentTeamWinning(), `is`(isTeam1Winning))
    }

    private fun startGameWithBlankValues() {
        gameViewModel?.setTeams("", listOf(), "", listOf())
        gameViewModel?.startGame(ApplicationProvider.getApplicationContext())
    }

}