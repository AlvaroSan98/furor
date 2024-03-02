package com.ifun.furor.model

import android.os.Environment
import android.util.Log
import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestion
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer
import com.ifun.furor.model.tests.TestWithQuestionAnswerAndOptions
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Exception

class TestsProvider {

    private var tests = ArrayList<Test>()

    fun getTests(): List<Test> {
        return tests
    }

    fun getCsvTests() {
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/tests_csv.txt")
        if (!folder.exists()) {
            Log.e("TestsProvider", "getCsvTests folder does not exist")
        } else {
            try {
                val fileReader = FileReader(folder)
                val bufferedReader = BufferedReader(fileReader)
                readCsvFile(bufferedReader)
            } catch (e: Exception) {
                Log.e("TestsProvider", "getCsvTests error reading file")
            }
        }
    }

    private fun readCsvFile(bufferedReader: BufferedReader) {
        var auxString: String

        auxString = bufferedReader.readLine()
        while (auxString != null) {
            var auxArray = auxString.split(";")
            fillTest(auxArray)

            auxString = bufferedReader.readLine()
        }
    }

    private fun fillTest(array: List<String>) {
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
            TestType.POTPURRI.value,
            TestType.SONG_SOUND.value,
            TestType.TITLE_SONG_EMOJIS.value -> {
                tests.add(
                    TestWithQuestion(
                    TestType.fromInt(array[0].toInt()),
                    DifficultType.fromInt(array[2].toInt()),
                    array[1])
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