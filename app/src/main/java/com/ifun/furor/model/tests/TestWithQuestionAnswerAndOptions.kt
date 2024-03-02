package com.ifun.furor.model.tests

import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.TestType

class TestWithQuestionAnswerAndOptions(
    type: TestType,
    difficulty: DifficultType,
    question: String,
    answer: String,
    val options: List<String>): TestWithQuestionAndAnswer(type, difficulty, question, answer) {

}