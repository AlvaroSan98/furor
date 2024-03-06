package com.ifun.furor.viewmodel

import androidx.lifecycle.ViewModel

class TeamsViewModel: ViewModel() {

    private var team_1_players = ArrayList<String>()
    private var team_2_players = ArrayList<String>()

    fun addPlayer(team1: Boolean, name: String) {
        if (team1) {
            team_1_players.add(name)
        } else {
            team_2_players.add(name)
        }
    }

}