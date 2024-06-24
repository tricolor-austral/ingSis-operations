package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.execution.Rule
import java.util.UUID

interface ExecutionService {
    fun formatSnippet(
        userId: String,
        snippetId: String,
        language: String,
        correlationId: UUID,
    ): String

    fun getFormatRules(
        userId: String,
        correlationId: UUID,
    ): List<Rule>

    fun getLintRules(
        userId: String,
        correlationId: UUID,
    ): List<Rule>

    fun changeFormatRules(
        userId: String,
        rules: List<Rule>,
        correlationId: UUID,
    )

    fun changeLintRules(
        userId: String,
        rules: List<Rule>,
        correlationId: UUID,
    )
}
