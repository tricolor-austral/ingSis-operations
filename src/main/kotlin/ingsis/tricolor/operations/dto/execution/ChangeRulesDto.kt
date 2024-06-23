package ingsis.tricolor.operations.dto.execution

import java.util.*

data class ChangeRulesDto(
    val userId: String,
    val rules: List<Rules>,
    val snippets: List<ExecutionDataDto>,
    val correlationId: UUID,
)
