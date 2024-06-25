package ingsis.tricolor.operations.dto.execution

data class ExecutionResponseDto(
    val correlationId: String,
    val snippetId: String,
    val snippet: String,
)
