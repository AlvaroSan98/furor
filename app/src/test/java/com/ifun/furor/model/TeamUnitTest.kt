package com.ifun.furor.model

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class TeamUnitTest {
    @Test
    fun `create team and check name`() {
        val name = "LOS POLLOS HERMANOS"
        val team = Team(name, arrayListOf())
        assertThat(team.getName(), `is`(name))
    }

    @Test
    fun `create team and check zero points`() {
        val team = Team("", arrayListOf())
        assertThat(team.getPoints(), `is`(0))
    }

    @Test
    fun `create team and sum points`() {
        val team = Team("", arrayListOf())
        val points = 20
        team.addPoints(points)
        assertThat(team.getPoints(), `is`(points))
    }
    
}