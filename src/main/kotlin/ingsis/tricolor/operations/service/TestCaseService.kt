package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.testCase.TestCaseCreateDto
import ingsis.tricolor.operations.dto.testCase.TestCaseReturnDto
import ingsis.tricolor.operations.entity.TestCase

interface TestCaseService {
    fun createTestCase(testCaseCreateDto: TestCaseCreateDto): TestCase

    fun deleteTestCase(testId: Long)

    fun getTestCase(snippetId: Long): List<TestCaseReturnDto>

    fun runTestCase(
        testCaseId: Long,
        envVars: String,
    ): String
}
