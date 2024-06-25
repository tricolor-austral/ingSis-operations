package ingsis.tricolor.operations.entity

import ingsis.tricolor.operations.dto.testCase.TestCaseCreateDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull

@Entity
class TestCase {
    @Id()
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    var snippetId: Long = 0

    @Column
    var name: String = ""

    @Column
    var input: String = ""

    @Column
    var expectedOutput: String = ""

    @Column
    var envVariable: String = ""

    companion object {
        fun from(testCaseDto: TestCaseCreateDto): TestCase {
            val testCase = TestCase()
            testCase.input = testCaseDto.input
            testCase.expectedOutput = testCaseDto.output
            testCase.name = testCaseDto.name
            testCase.snippetId = testCaseDto.snippetId
            testCase.envVariable = testCaseDto.envVariable
            return testCase
        }
    }
}
