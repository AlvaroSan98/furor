package com.ifun.furor.model.enums

enum class DifficultType(val value: Int) {
    EASY(1),
    MEDIUM(2),
    DIFFICULT(3),
    EXPERT(4);

    companion object {
        fun fromInt(value: Int) = DifficultType.values().first {it.value == value}
    }
}