package com.ifun.furor.model.tests

import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.Operations
import com.ifun.furor.model.enums.TestType
import kotlin.random.Random

open class Test(val type: TestType, val difficulty: DifficultType) {

    val seconds = getTimeMillis() / 1000
    val points = getTestPoints()

    fun getTimeMillis(): Long {
        return when (type) {
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

    private fun getTestPoints(): Int {
        return when (difficulty) {
            DifficultType.EASY -> 5
            DifficultType.MEDIUM -> 10
            DifficultType.DIFFICULT -> 20
            DifficultType.EXPERT -> 40
        }
    }
}