package com.ifun.furor.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ifun.furor.model.exceptions.Exceptions
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows

class TeamsViewModelIntegratedTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var teamsViewModel: TeamsViewModel? = null

    @Before
    fun setUp() {
        teamsViewModel = TeamsViewModel()
    }

    @After
    fun tearDown() {
        teamsViewModel = null
    }

    @Test
    fun `add player to team1 with an empty list of players`() {
        //Given
        val player = "Juan"

        //When
        teamsViewModel?.addPlayer(true, player)
        val team1 = teamsViewModel?.getTeam1()?.value

        //Then
        assertThat(team1, `is`(notNullValue()))
        assertThat(team1, `is`(listOf("Juan")))
    }

    @Test
    fun `add player to team1 with a list of players`() {
        //Given
        teamsViewModel?.addPlayer(true, "Juan")
        teamsViewModel?.addPlayer(true, "Isa")
        val playersList = teamsViewModel?.getTeam1()?.value
        val newList = playersList?.toMutableList()
        val player = "Jorge"
        newList?.add(player)

        //When
        teamsViewModel?.addPlayer(true, player)

        //Then
        assertThat(teamsViewModel?.getTeam1()?.value, `is`(newList))
    }

    @Test
    fun `add player to team1 when team1 has 11 players`() {
        //Given
        teamsViewModel?.addPlayer(true, "1")
        teamsViewModel?.addPlayer(true, "2")
        teamsViewModel?.addPlayer(true, "3")
        teamsViewModel?.addPlayer(true, "4")
        teamsViewModel?.addPlayer(true, "5")
        teamsViewModel?.addPlayer(true, "6")
        teamsViewModel?.addPlayer(true, "7")
        teamsViewModel?.addPlayer(true, "8")
        teamsViewModel?.addPlayer(true, "9")
        teamsViewModel?.addPlayer(true, "10")
        teamsViewModel?.addPlayer(true, "11")
        val playersList = teamsViewModel?.getTeam1()?.value
        val newList = playersList?.toMutableList()
        val player = "Jorge"
        newList?.add(player)

        assertThrows(Exceptions.TeamFullOfPlayers::class.java) {
            teamsViewModel?.addPlayer(true, player)
        }
    }

    @Test
    fun `add player to team2 with an empty list of players`() {
        //Given
        val player = "Juan"

        //When
        teamsViewModel?.addPlayer(false, player)
        val team1 = teamsViewModel?.getTeam2()?.value

        //Then
        assertThat(team1, `is`(notNullValue()))
        assertThat(team1, `is`(listOf("Juan")))
    }

    @Test
    fun `add player to team2 with a list of players`() {
        //Given
        teamsViewModel?.addPlayer(false, "Juan")
        teamsViewModel?.addPlayer(false, "Isa")
        val playersList = teamsViewModel?.getTeam2()?.value
        val newList = playersList?.toMutableList()
        val player = "Jorge"
        newList?.add(player)

        //When
        teamsViewModel?.addPlayer(false, player)

        //Then
        assertThat(teamsViewModel?.getTeam2()?.value, `is`(newList))
    }

    @Test
    fun `add player to team2 when team2 has 11 players`() {
        //Given
        teamsViewModel?.addPlayer(false, "1")
        teamsViewModel?.addPlayer(false, "2")
        teamsViewModel?.addPlayer(false, "3")
        teamsViewModel?.addPlayer(false, "4")
        teamsViewModel?.addPlayer(false, "5")
        teamsViewModel?.addPlayer(false, "6")
        teamsViewModel?.addPlayer(false, "7")
        teamsViewModel?.addPlayer(false, "8")
        teamsViewModel?.addPlayer(false, "9")
        teamsViewModel?.addPlayer(false, "10")
        teamsViewModel?.addPlayer(false, "11")
        val playersList = teamsViewModel?.getTeam2()?.value
        val newList = playersList?.toMutableList()
        val player = "Jorge"
        newList?.add(player)

        assertThrows(Exceptions.TeamFullOfPlayers::class.java) {
            teamsViewModel?.addPlayer(false, player)
        }
    }

    @Test
    fun `check teams are null when init`() {
        assertThat(teamsViewModel?.getTeam1()?.value, `is`(nullValue()))
        assertThat(teamsViewModel?.getTeam2()?.value, `is`(nullValue()))
    }

}