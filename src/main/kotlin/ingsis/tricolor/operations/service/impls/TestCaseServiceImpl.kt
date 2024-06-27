package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.testCase.TestCaseCreateDto
import ingsis.tricolor.operations.dto.testCase.TestCaseReturnDto
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

        override fun getTestCase(snippetId: Long): List<TestCaseReturnDto> {
            var testCasedtos: MutableList<TestCase> = this.testCaseRepository.findBySnippetId(snippetId)
            return testCasedtos.map { TestCaseReturnDto.from(it) }
        }

        override fun runTestCase(testCaseId: Long): String {
            var testCase: TestCase = this.testCaseRepository.findById(testCaseId).get()
            println("2," + testCase)
            var snippetId: Long = testCase.snippetId
            println("3," + snippetId)
            var snippetContent: String = apiCalls.getSnippet(snippetId.toString())
            print(snippetContent)
            print(testCase.input)
            print(testCase.output)
            return apiCalls.runTest(snippetContent, testCase.input, testCase.output)
        }
    }
