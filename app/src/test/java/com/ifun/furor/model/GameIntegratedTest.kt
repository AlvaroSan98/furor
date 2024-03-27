package com.ifun.furor.model

import androidx.test.core.app.ApplicationProvider
import com.ifun.furor.model.exceptions.Exceptions
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertThrows

@RunWith(RobolectricTestRunner::class)
class GameIntegratedTest {
    private var game: Game? = null

    @Before
    fun setUp() {
        val team1 = Team("Team1", listOf())
        val team2 = Team("Team2", listOf())
        game = Game(team1, team2)
    }

    @After
    fun tearDown() {
        game = null
    }

    @Test
    fun `next test with data provided is not null`() {
        game?.startGame(ApplicationProvider.getApplicationContext())
        val currentTest = game?.nextTest()
        assertThat(currentTest, `is`(notNullValue()))
    }

    @Test
    fun `next test without data provided is not null`() {
        assertThrows(Exceptions.NotMoreTestsException::class.java) {
            game?.nextTest()
        }
    }

    @Test
    fun `test points added to team`() {
        game?.startGame(ApplicationProvider.getApplicationContext())
        val test = game?.nextTest()
        val team = game?.nextTeam()
        game?.answer(true)
        assertThat(team?.getPoints(), `is`(test?.points))
    }

    @Test
    fun `unit test test points not added to team`() {
        game?.startGame(ApplicationProvider.getApplicationContext())
        val team = game?.nextTeam()
        val previousPoints = team?.getPoints()
        game?.answer(false)
        assertThat(team?.getPoints(), `is`(previousPoints))
    }

    @Test
    fun `if next test is testWithQuestion return is not a test with answer`() {
        var test: com.ifun.furor.model.tests.Test

        game?.startGame(ApplicationProvider.getApplicationContext())
        do {
            test = game?.nextTest()!!
        } while (test is TestWithQuestionAndAnswer)

        assertThat(game?.isTestWithAnswer(), `is`(false))
    }

    @Test
    fun `if next test is a testWithQuestionAndAnswer return is a test with answer`() {
        var test: com.ifun.furor.model.tests.Test

        game?.startGame(ApplicationProvider.getApplicationContext())
        do {
            test = game?.nextTest()!!
        } while (test !is TestWithQuestionAndAnswer)

        assertThat(game?.isTestWithAnswer(), `is`(true))
    }

}