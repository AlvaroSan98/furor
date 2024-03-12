package com.ifun.furor.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifun.furor.model.Game
import com.ifun.furor.model.Team
import com.ifun.furor.model.tests.Test
import com.ifun.furor.model.tests.TestWithQuestionAndAnswer

class GameViewModel: ViewModel() {

    private val test = MutableLiveData<Test>()
    private val team = MutableLiveData<Team>()
    private val state = MutableLiveData<GameState>()

    private lateinit var game: Game

    init {
        state.postValue(GameState.LOADING)
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
        state.postValue(GameState.STARTED)
    }

    fun answer(correct: Boolean) {
        game.answer(correct)
        test.postValue(game.nextTest())
        team.postValue(game.nextTeam())
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

    fun getCorrectAnswer(): String {
        val test = test.value as TestWithQuestionAndAnswer
        return test.answer
    }

    fun getState(): LiveData<GameState> {
        return state
    }

}