package ingsis.tricolor.operations.dto.testCase

class TestCaseCreateDto(
    var name: String,
    var input: MutableList<String>,
    var output: MutableList<String>,
    var id: Long,
    var envVars: String,
)
