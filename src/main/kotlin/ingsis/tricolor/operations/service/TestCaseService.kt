package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.testCase.TestCaseCreateDto
import ingsis.tricolor.operations.entity.TestCase

interface TestCaseService {
    fun createTestCase(testCaseCreateDto: TestCaseCreateDto): TestCase

    fun deleteTestCase(testId: Long)

    fun getTestCase(): MutableIterable<TestCase>

    fun runTestCase(testCaseId: Long): String
}
