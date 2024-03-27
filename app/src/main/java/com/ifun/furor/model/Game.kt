package com.ifun.furor.model

import android.content.Context
import android.os.CountDownTimer
import com.ifun.furor.model.enums.Operations
import com.ifun.furor.model.exceptions.Exceptions
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import com.ifun.furor.model.tests.TestWithQuestionAnswerAndOptions
import kotlin.random.Random

class Game(private val team1: Team, private val team2: Team) {

    private var tests: ArrayList<Test> = arrayListOf()

    private lateinit var currentTest: Test
    private var currentTeam = team1

    fun startGame(context: Context) {
        tests = TestsProvider(context).getTests()
    }

    fun nextTest(): Test {
        if (this::currentTest.isInitialized) {
            removeCurrentTest()
        }
        getNextTest()
        return currentTest
    }

    fun nextTeam(): Team {
        currentTeam = if (currentTeam == team1) {
            team2
        } else {
            team1
        }
        return currentTeam
    }

    fun answer(correct: Boolean) {
        finalAnswer(correct)
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
        if (tests.size > 0) {
            currentTest = tests[Random.nextInt(tests.size)]
        } else {
            throw Exceptions.NotMoreTestsException("There is no test to provide")
        }
    }

    private fun removeCurrentTest() {
        tests.remove(currentTest)
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