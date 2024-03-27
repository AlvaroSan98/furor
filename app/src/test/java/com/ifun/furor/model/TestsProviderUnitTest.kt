package com.ifun.furor.model

import androidx.test.core.app.ApplicationProvider
import com.ifun.furor.model.enums.TestType
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestsProviderUnitTest {

    lateinit var tests: ArrayList<com.ifun.furor.model.tests.Test>

    @Before
    fun setUp() {
        tests = TestsProvider(ApplicationProvider.getApplicationContext()).getTests()
    }

    @After
    fun tearDown() {
        tests = arrayListOf()
    }

    @Test
    fun `check tests size`() {
        assertThat(tests.size, `is`(210))
    }

    @Test
    fun `check number of each test operations`() {
        var titleSongTextCount = 0
        var authorSongTextCount = 0
        var continueSongTextCount = 0
        var songsOfAuthorCount = 0
        var mimeCount = 0
        var theStrangeOneCount = 0
        var theOldestCount = 0
        var songSoundCount = 0
        var potpurriCount = 0
        var titleSongEmojisCount = 0
        var curiosityCount = 0

        for (test in tests) {
            when (test.type) {
                TestType.TITLE_SONG_TEXT -> titleSongTextCount++
                TestType.CONTINUE_SONG_TEXT -> continueSongTextCount++
                TestType.AUTHOR_SONG_TEXT -> authorSongTextCount++
                TestType.SONGS_OF_AUTHOR -> songsOfAuthorCount++
                TestType.MIME -> mimeCount++
                TestType.THE_STRANGE_ONE -> theStrangeOneCount++
                TestType.THE_OLDEST -> theOldestCount++
                TestType.SONG_SOUND -> songSoundCount++
                TestType.POTPURRI -> potpurriCount++
                TestType.TITLE_SONG_EMOJIS -> titleSongEmojisCount++
                TestType.CURIOSITY -> curiosityCount++
            }
        }

        assertThat(tests.size, `is`(210))
        assertThat(titleSongTextCount, `is`(20))
        assertThat(continueSongTextCount, `is`(20))
        assertThat(authorSongTextCount, `is`(20))
        assertThat(songsOfAuthorCount, `is`(12))
        assertThat(mimeCount, `is`(8))
        assertThat(theStrangeOneCount, `is`(10))
        assertThat(theOldestCount, `is`(10))
        assertThat(songSoundCount, `is`(60))
        assertThat(potpurriCount, `is`(10))
        assertThat(titleSongEmojisCount, `is`(20))
        assertThat(curiosityCount, `is`(20))
    }
}