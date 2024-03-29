package com.ifun.furor.model

import android.content.Context
import android.os.CountDownTimer
import com.ifun.furor.model.enums.Operations
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import com.ifun.furor.model.tests.TestWithQuestionAnswerAndOptions
import kotlin.random.Random

class Game(private val team1: Team, private val team2: Team) {

    private lateinit var tests: ArrayList<Test>

    private lateinit var currentTest: Test
    private var currentTeam = team1

    private var timer: CountDownTimer? = null

    fun startGame(context: Context) {
        tests = TestsProvider(context).getTests()
    }

    fun nextTest(): Test {
        getNextTest()
        return currentTest
    }

    fun nextTeam(): Team {
        getNextTeam()
        return currentTeam
    }

    fun answer(correct: Boolean) {
        timer?.cancel()
        finalAnswer(correct)
        tests.remove(currentTest)
    }

    fun isTestWithAnswer(): Boolean {
        return currentTest is TestWithQuestionAndAnswer
    }

    fun getCorrectOption(): Int {
        val testAsOption = currentTest as TestWithQuestionAnswerAndOptions
        var index = 0
        while (index < testAsOption.options.size) {
            if (testAsOption.answer == testAsOption.options[index])
                return index
            index++
        }
        return -1
    }

    private fun getNextTest() {
        if (timer != null)
            timer?.cancel()
        currentTest = tests[Random.nextInt(tests.size)]
        //startTimer()
    }

    private fun getNextTeam() {
        currentTeam = if (currentTeam == team1) {
            team2
        } else {
            team1
        }
    }

    private fun finalAnswer(correct: Boolean) {
        if (correct) {
            currentTeam.addPoints(currentTest.points)
        }
    }

    fun isCurrentTeamWinning(): Boolean {
        if (currentTeam == team1) {
            return team1.getPoints() >= team2.getPoints()
        } else {
            return team2.getPoints() >= team1.getPoints()
        }
    }
}