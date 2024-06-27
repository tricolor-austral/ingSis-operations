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
    constructor(val testCaseRepository: TestCaseRepository) : TestCaseService {
        override fun createTestCase(testCaseCreateDto: TestCaseCreateDto): TestCase {
            val testCase = TestCase.from(testCaseCreateDto)
            this.testCaseRepository.save(testCase)
            return testCase
        }

        override fun deleteTestCase(testId: Long)  {
            this.testCaseRepository.deleteById(testId)
        }

        override fun getTestCase(): MutableIterable<TestCase> {
            return this.testCaseRepository.findAll()
        }
    }
