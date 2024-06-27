package ingsis.tricolor.operations.dto.testCase

import ingsis.tricolor.operations.entity.TestCase

class TestCaseReturnDto {
    var id: String = ""
    var snippetId: Long = 0
    var name: String = ""
    var input: String = ""
    var output: List<String> = mutableListOf()
    var envVars: String = ""

    companion object {
        fun from(testCase: TestCase): TestCaseReturnDto {
            val testCase1 = TestCaseReturnDto()
            testCase1.input = testCase.input
            testCase1.output = testCase.output
            testCase1.name = testCase.name
            testCase1.snippetId = testCase.snippetId
            testCase1.envVars = testCase.envVars
            return testCase1
        }
    }
}
