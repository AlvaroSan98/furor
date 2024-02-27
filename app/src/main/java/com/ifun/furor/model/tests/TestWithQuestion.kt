package com.ifun.furor.model.tests

import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test

open class TestWithQuestion(type: TestType, difficulty: DifficultType, question: String): Test(type, difficulty){


}