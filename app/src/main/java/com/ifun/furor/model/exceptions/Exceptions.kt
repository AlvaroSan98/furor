package com.ifun.furor.model.exceptions

class Exceptions {
    class NotMoreTestsException(msg: String? = null) : RuntimeException(msg)
    class TeamFullOfPlayers(msg: String? = null) : RuntimeException(msg)
}