package com.ifun.furor

import com.ifun.furor.model.TestsProvider
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestsTest {

    @Spy
    private lateinit var testProvider: TestsProvider

    private lateinit var tests: List<com.ifun.furor.model.tests.Test>

    @Before
    fun setUp() {
        testProvider = TestsProvider()
        testProvider.getCsvTests()
        tests = testProvider.getTests()
    }

    @After
    fun tearDown() {
        testProvider = TestsProvider()
        tests = emptyList()
    }

    @Test
    fun checkTestsNumber_registerTests_completeRegistration_returnSuccess() {
        assertEquals(210, tests.size)
    }
    
    @Test
    fun checkTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkContinueSongTextTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkContinueSongTextTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkTitleSongTextTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkTitleSongTextTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkAuthorSongTextTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkAuthorSongTextTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkSongsOfAuthorTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkSongsOfAuthorTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkMimeTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkMimeTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkTheStrangeOneTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkTheStrangeOneTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkTheOldestTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkTheOldestTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkSongSoundTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkSongSoundTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkPotpurriTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkPotpurriTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkTitleSongEmojisTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkTitleSongEmojisTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
    
    @Test
    fun checkCuriosityTestsNumber_registerTests_completeRegistration_returnSuccess() {
        
    }
    
    @Test
    fun checkCuriosityTestsNumber_registerTests_wrongRegistration_returnInvalidTestException() {
        
    }
}