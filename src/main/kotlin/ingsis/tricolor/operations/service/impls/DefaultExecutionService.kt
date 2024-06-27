package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.execution.ExecutionDataDto
import ingsis.tricolor.operations.dto.execution.Rule
import ingsis.tricolor.operations.error.UnauthorizedException
import ingsis.tricolor.operations.service.APICalls
import ingsis.tricolor.operations.service.ExecutionService
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DefaultExecutionService(
    @Autowired private val apiCalls: APICalls,
    @Autowired private val snippetService: SnippetService,
) : ExecutionService {
    override fun formatSnippet(
        userId: String,
        snippetId: String,
        language: String,
        correlationId: UUID,
    ): String {
        val permissions = apiCalls.userCanWrite(userId, snippetId)
        if (!permissions.permissions.contains("WRITE")) throw UnauthorizedException("User cannot format this snippet")
        val content = apiCalls.getSnippet(snippetId)
        val data = ExecutionDataDto(correlationId, snippetId, language, "1.1", content)
        val response = apiCalls.formatSnippet(data)
        return response.snippet
    }

    override fun getFormatRules(
        userId: String,
        correlationId: UUID,
    ): List<Rule> {
        return apiCalls.getFormatRules(userId, correlationId)
    }

    override fun getLintRules(
        userId: String,
        correlationId: UUID,
    ): List<Rule> {
        return apiCalls.getLintRules(userId, correlationId)
    }

    override fun changeFormatRules(
        userId: String,
        rules: List<Rule>,
        correlationId: UUID,
    ) {
        val snippets =
            snippetService.getAllSnippetsByUser(
                userId,
            ).map {
                ExecutionDataDto(correlationId, it.id, it.language, "1.1", it.content)
            }
        rules.map {
            println("rules: ${it.name} ${it.value} ${it.id} ${it.name}")
        }
        snippets.map {
            println(it.input)
        }
        apiCalls.changeFormatRules(userId, rules, snippets, correlationId)
    }

    override fun changeLintRules(
        userId: String,
        rules: List<Rule>,
        correlationId: UUID,
    ) {
        val snippets =
            snippetService.getAllSnippetsByUser(
                userId,
            ).map {
                ExecutionDataDto(correlationId, it.id, it.language, "1.1", it.content)
            }
        apiCalls.changeFormatRules(userId, rules, snippets, correlationId)
    }
}
