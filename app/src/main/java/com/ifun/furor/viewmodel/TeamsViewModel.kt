package com.ifun.furor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamsViewModel: ViewModel() {

    private val team_1_list = MutableLiveData<List<String>>()
    private val team_2_list = MutableLiveData<List<String>>()

    private var team_1_players = ArrayList<String>()
    private var team_2_players = ArrayList<String>()

    fun getTeam1(): LiveData<List<String>> {
        return team_1_list
    }

    fun getTeam2(): LiveData<List<String>> {
        return team_2_list
    }

    fun addPlayer(team1: Boolean, name: String) {
        if (team1) {
            team_1_players.add(name)
            team_1_list.postValue(team_1_players)
        } else {
            team_2_players.add(name)
            team_2_list.postValue(team_2_players)
        }
    }

}