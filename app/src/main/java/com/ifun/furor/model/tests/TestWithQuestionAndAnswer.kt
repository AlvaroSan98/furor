package com.ifun.furor.model.tests

import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.TestType

open class TestWithQuestionAndAnswer(
    type: TestType,
    difficulty: DifficultType,
    question: String,
    val answer: String,
    val resourced: Boolean = false): TestWithQuestion(type, difficulty, question) {

}