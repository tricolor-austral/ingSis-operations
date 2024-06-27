package ingsis.tricolor.operations.controller

import ingsis.tricolor.operations.dto.testCase.TestCaseCreateDto
import ingsis.tricolor.operations.entity.TestCase
import ingsis.tricolor.operations.service.TestCaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/test-case")
class TestCaseController(
    @Autowired val testCaseService: TestCaseService,
) {
    @PostMapping
    fun createTestCase(
        @RequestBody testCaseCreateDto: TestCaseCreateDto,
    ): TestCase {
        return testCaseService.createTestCase(testCaseCreateDto)
    }

    @DeleteMapping
    fun deleteTestCase(
        @RequestParam testCaseId: Long,
    ) {
        return testCaseService.deleteTestCase(testCaseId)
    }

    @GetMapping
    fun getTestCases(
    ): MutableIterable<TestCase> {
        return testCaseService.getTestCase()
    }
}
