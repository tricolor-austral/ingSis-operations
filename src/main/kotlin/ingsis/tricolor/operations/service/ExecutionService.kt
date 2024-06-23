package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.execution.Rules
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
    ): List<Rules>

    fun getLintRules(
        userId: String,
        correlationId: UUID,
    ): List<Rules>

    fun changeFormatRules(
        userId: String,
        rules: List<Rules>,
        correlationId: UUID,
    )

    fun changeLintRules(
        userId: String,
        rules: List<Rules>,
        correlationId: UUID,
    )
}
