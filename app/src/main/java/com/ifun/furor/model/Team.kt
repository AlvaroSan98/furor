package com.ifun.furor.model

import kotlin.random.Random

class Team(private val name: String, private val players: List<String>) {

    private var points = 0
    fun getName(): String {
        return name
    }
    fun getRandomPlayer(): String {
        return players[Random.nextInt(players.size)]
    }

    fun getPoints(): Int {
        return this.points
    }

    fun addPoints(points: Int): Int {
        this.points += points
        return this.points
    }

    fun removePoints(points: Int): Int {
        this.points -= points
        return this.points
    }

    fun divideHalfPoints(): Int {
        this.points = this.points / 2
        return this.points
    }

    fun doublePoints(): Int {
        this.points = this.points * 2
        return this.points
    }

}