package ingsis.tricolor.operations.dto.execution

import java.util.UUID

data class ChangeRulesDto(
    val userId: String,
    val rules: List<Rule>,
    val snippets: List<ExecutionDataDto>,
    val correlationId: UUID,
)
