package ingsis.tricolor.operations.dto.execution

import java.util.*

data class ExecutionDataDto(
    val correlationId: UUID,
    val snippetId: String,
    val language: String,
    val version: String,
    val input: String,
)
