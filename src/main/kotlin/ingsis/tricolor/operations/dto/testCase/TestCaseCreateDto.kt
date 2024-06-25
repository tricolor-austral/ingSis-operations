package ingsis.tricolor.operations.dto.testCase

class TestCaseCreateDto(
    var name: String,
    var input: String,
    var output: String,
    var snippetId: Long,
    var envVariable: String,
)
