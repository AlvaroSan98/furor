package com.ifun.furor.model.tests

import com.ifun.furor.model.enums.DifficultType
import com.ifun.furor.model.enums.TestType
import com.ifun.furor.model.tests.Test

open class TestWithQuestion(type: TestType,difficulty: DifficultType, val question: String, val resourced: Boolean = false): Test(type, difficulty){


}