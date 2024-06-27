package ingsis.tricolor.operations.dto.execution

import java.util.UUID

data class FormatFileDto(
    val correlationId: UUID,
    val snippetId: String,
    val language: String,
    val version: String,
    val input: String,
    val userId: String,
)
