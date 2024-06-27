package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.testCase.TestCaseCreateDto
import ingsis.tricolor.operations.entity.TestCase
import ingsis.tricolor.operations.repository.TestCaseRepository
import ingsis.tricolor.operations.service.TestCaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TestCaseServiceImpl
    @Autowired
    constructor(val testCaseRepository: TestCaseRepository, val apiCalls: DefaultApiCalls) : TestCaseService {
        override fun createTestCase(testCaseCreateDto: TestCaseCreateDto): TestCase {
            val testCase = TestCase.from(testCaseCreateDto)
            this.testCaseRepository.save(testCase)
            return testCase
        }

        override fun deleteTestCase(testId: Long) {
            this.testCaseRepository.deleteById(testId)
        }

        override fun getTestCase(): MutableIterable<TestCase> {
            return this.testCaseRepository.findAll()
        }

        override fun runTestCase(testCaseId: Long): String {
            var testCase: TestCase = this.testCaseRepository.findById(testCaseId).get()
            var snippetId: Long = testCase.snippetId
            var snippetContent: String = apiCalls.getSnippet(snippetId.toString())
            return apiCalls.runTest(snippetContent, testCase.input, testCase.expectedOutput)
        }
    }
