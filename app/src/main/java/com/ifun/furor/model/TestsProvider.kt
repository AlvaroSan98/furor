package com.ifun.furor.model

import android.content.Context
import android.os.Environment
import android.util.Log
import com.ifun.furor.R
import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestion
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import com.ifun.furor.model.tests.TestWithQuestionAnswerAndOptions
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.lang.Exception

class TestsProvider(private val context: Context) {

    fun getTests(): List<Test> {
        return getCsvTests()
    }

    private fun getCsvTests(): List<Test> {
        try {
            val inputStream = context.resources.openRawResource(R.raw.tests_csv)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            return readCsvFile(bufferedReader)
        } catch (e: Exception) {
            Log.e("TestsProvider", "getCsvTests error reading file")
            return emptyList()
        }
    }

    private fun readCsvFile(bufferedReader: BufferedReader): List<Test> {
        var testsList = ArrayList<Test>()

        val iterator = bufferedReader.lineSequence().iterator()
        while (iterator.hasNext()) {
            val line = iterator.next()
            var auxArray = line.split(";")
            fillTest(auxArray, testsList)
        }

        return testsList
    }

    private fun fillTest(array: List<String>, tests: ArrayList<Test>) {
        when (array[0].toInt()) {
            TestType.CONTINUE_SONG_TEXT.value,
            TestType.TITLE_SONG_TEXT.value,
            TestType.AUTHOR_SONG_TEXT.value -> {
                tests.add(
                    TestWithQuestionAndAnswer(
                    TestType.fromInt(array[0].toInt()),
                    DifficultType.fromInt(array[2].toInt()),
                    array[1],
                    array[3])
                )
            }
            TestType.SONGS_OF_AUTHOR.value,
            TestType.MIME.value,
            TestType.POTPURRI.value -> {
                tests.add(
                    TestWithQuestion(
                        TestType.fromInt(array[0].toInt()),
                        DifficultType.fromInt(array[2].toInt()),
                        array[1])
                )
            }
            TestType.SONG_SOUND.value,
            TestType.TITLE_SONG_EMOJIS.value -> {
                tests.add(
                    TestWithQuestionAndAnswer(
                        TestType.fromInt(array[0].toInt()),
                        DifficultType.fromInt(array[2].toInt()),
                        array[1],
                        array[3],
                        true
                    )
                )
            }
            TestType.THE_STRANGE_ONE.value,
            TestType.CURIOSITY.value,
            TestType.THE_OLDEST.value -> {
                tests.add(
                    TestWithQuestionAnswerAndOptions(
                    TestType.fromInt(array[0].toInt()),
                    DifficultType.fromInt(array[2].toInt()),
                    array[1],
                    array[3],
                    listOf(array[4], array[5], array[6], array[7]))
                )
            }
        }
    }
}