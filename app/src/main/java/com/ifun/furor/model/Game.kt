package com.ifun.furor.model

import android.os.CountDownTimer
import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.Operations
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test
import kotlin.random.Random

class Game(private val team1: Team, private val team2: Team) {

    private lateinit var tests: List<Test>

    private lateinit var currentTest: Test
    private var currentTeam = team1
    private var currentTestTime: Long = 0
    private var currentTestPoints = 0
    private lateinit var currentTestOperation: Operations

    private lateinit var timer: CountDownTimer

    fun startGame() {
        tests = TestsProvider().getTests()
        getNextTeam()
        getNextTest()
    }

    fun nextTest() {
        getNextTeam()
        getNextTest()
    }

    fun answer(correct: Boolean) {
        finalAnswer(correct)
    }

    private fun getNextTest() {
        currentTest = tests[Random.nextInt(tests.size)]
        getCurrentTestPoints()
        getCurrentTestTimeMillis()
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
        timer = object: CountDownTimer(getCurrentTestTimeMillis(), 1000) {
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

    private fun getCurrentTestTimeMillis(): Long {
        return when (currentTest.type) {
            TestType.TITLE_SONG_TEXT,
            TestType.AUTHOR_SONG_TEXT,
            TestType.CONTINUE_SONG_TEXT,
            TestType.SONG_SOUND,
            TestType.TITLE_SONG_EMOJIS,
            TestType.CURIOSITY -> 30_000

            TestType.SONGS_OF_AUTHOR,
            TestType.THE_STRANGE_ONE,
            TestType.THE_OLDEST -> 20_000

            TestType.MIME -> 60_000

            else -> 0
        }
    }

    private fun finalAnswer(correct: Boolean) {
        if (currentTestOperation == Operations.ADD) {
            if (!correct) {
                if (currentTeam == team1) {
                    team2.addPoints(currentTestPoints)
                } else {
                    team1.addPoints(currentTestPoints)
                }
            } else {
                currentTeam.addPoints(currentTestPoints)
            }
        } else {
            if (!correct) {
                currentTeam.removePoints(currentTestPoints)
            } else {
                if (currentTeam == team1) {
                    team2.removePoints(currentTestPoints)
                } else {
                    team1.removePoints(currentTestPoints)
                }
            }
        }
    }

    private fun getCurrentTestPoints() {
        currentTestPoints = when (currentTest.difficulty) {
            DifficultType.EASY -> 5
            DifficultType.MEDIUM -> 10
            DifficultType.DIFFICULT -> 20
            DifficultType.EXPERT -> 40
        }

        val randomOperation = Random.nextInt(10)
        currentTestOperation = if (randomOperation in 0..4) {
            Operations.ADD
        } else {
            Operations.REMOVE
        }
    }
}