package com.ifun.furor.model

import android.content.Context
import android.os.CountDownTimer
import com.ifun.furor.model.enums.Operations
import com.ifun.furor.model.tests.Test
import kotlin.random.Random

class Game(private val team1: Team, private val team2: Team) {

    private lateinit var tests: List<Test>

    private lateinit var currentTest: Test
    private var currentTeam = team1
    private var currentTestTime: Long = 0
    private lateinit var currentTestOperation: Operations

    private lateinit var timer: CountDownTimer

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
        finalAnswer(correct)
    }

    private fun getNextTest() {
        currentTest = tests[Random.nextInt(tests.size)]
        startTimer()
    }

    private fun getNextTeam() {
        currentTeam = if (currentTeam == team1) {
            team2
        } else {
            team1
        }
    }

    private fun startTimer() {
        timer = object: CountDownTimer(currentTest.getTimeMillis(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentTestTime = millisUntilFinished / 1000
            }

            override fun onFinish() {
                currentTestTime = 0
                finalAnswer(false)
            }

        }
        timer.start()
    }

    private fun finalAnswer(correct: Boolean) {
        if (currentTestOperation == Operations.ADD) {
            if (!correct) {
                if (currentTeam == team1) {
                    team2.addPoints(currentTest.points)
                } else {
                    team1.addPoints(currentTest.points)
                }
            } else {
                currentTeam.addPoints(currentTest.points)
            }
        } else {
            if (!correct) {
                currentTeam.removePoints(currentTest.points)
            } else {
                if (currentTeam == team1) {
                    team2.removePoints(currentTest.points)
                } else {
                    team1.removePoints(currentTest.points)
                }
            }
        }
    }
}