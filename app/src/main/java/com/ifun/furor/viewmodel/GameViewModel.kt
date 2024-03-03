package com.ifun.furor.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifun.furor.model.Game
import com.ifun.furor.model.Team
import com.ifun.furor.model.tests.Test

class GameViewModel: ViewModel() {

    private val test = MutableLiveData<Test>()
    private val team = MutableLiveData<Team>()

    private lateinit var game: Game

    fun setTeams(team1name: String, team1players: List<String>, team2name: String, team2players: List<String>) {
        val team1 = Team(team1name, team1players)
        val team2 = Team(team2name, team2players)
        game = Game(team1, team2)
    }

    fun startGame(context: Context) {
        game.startGame(context)
        test.postValue(game.nextTest())
        team.postValue(game.nextTeam())
    }

    fun nextTest() {
        test.postValue(game.nextTest())
        team.postValue(game.nextTeam())
    }

    fun getTest(): MutableLiveData<Test> {
        return test
    }

    fun getTeam(): MutableLiveData<Team> {
        return team
    }

}