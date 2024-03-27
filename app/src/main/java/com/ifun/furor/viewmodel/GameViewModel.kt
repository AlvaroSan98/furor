package com.ifun.furor.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifun.furor.model.Game
import com.ifun.furor.model.Team
import com.ifun.furor.model.enums.GameState
import com.ifun.furor.model.enums.TestState
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer

class GameViewModel: ViewModel() {

    private val test = MutableLiveData<Test>()
    private val team = MutableLiveData<Team>()
    private val gameState = MutableLiveData<GameState>()
    private val testState = MutableLiveData<TestState>()

    private lateinit var game: Game

    init {
        gameState.postValue(GameState.LOADING)
    }

    fun setTeams(team1name: String, team1players: List<String>, team2name: String, team2players: List<String>) {
        val team1 = Team(team1name, team1players)
        val team2 = Team(team2name, team2players)
        game = Game(team1, team2)
    }

    fun startGame(context: Context) {
        game.startGame(context)
        test.postValue(game.nextTest())
        team.postValue(game.nextTeam())
        gameState.postValue(GameState.STARTED)
        testState.postValue(chooseTestState())
    }

    fun answer(correct: Boolean) {
        game.answer(correct)
        test.postValue(game.nextTest())
        team.postValue(game.nextTeam())
        testState.postValue(chooseTestState())
    }

    fun getTest(): MutableLiveData<Test> {
        return test
    }

    fun getTeam(): MutableLiveData<Team> {
        return team
    }

    fun isCurrentTeamWinning(): Boolean {
        return game.isCurrentTeamWinning()
    }

    fun getGameState(): LiveData<GameState> {
        return gameState
    }

    fun getTestState(): LiveData<TestState> {
        return testState
    }

    fun changeTestState(state: TestState) {
        testState.postValue(state)
    }

    fun getCorrectOption(): Int {
        return game.getCorrectOption()
    }

    private fun chooseTestState(): TestState {
        return if (game.isTestWithAnswer()) {
            TestState.QUESTION
        } else {
            TestState.ANSWER
        }
    }

}