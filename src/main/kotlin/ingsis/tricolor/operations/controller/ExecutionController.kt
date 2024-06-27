package ingsis.tricolor.operations.controller

import ingsis.tricolor.operations.dto.SnippetContext
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.dto.execution.ObjectRules
import ingsis.tricolor.operations.dto.execution.Rule
import ingsis.tricolor.operations.service.ExecutionService
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import java.util.UUID

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/run")
class ExecutionController(
    @Autowired private val snippetService: SnippetService,
    @Autowired private val execService: ExecutionService,
) {
    @PutMapping("/format")
    fun formatOneSnippet(
        @RequestParam userId: String,
        @RequestBody snippetContext: SnippetContext,
    ): String {
        val uuid = UUID.randomUUID()
        return execService.formatSnippet(userId, snippetContext.snippetId, snippetContext.language, uuid)
    }

    @GetMapping("/format-rules")
    fun getFormatRules(
        @RequestParam userId: String,
    ): List<Rule> = execService.getFormatRules(userId, UUID.randomUUID())

    @GetMapping("/lint-rules")
    fun getLintRules(
        @RequestParam userId: String,
    ): List<Rule> = execService.getLintRules(userId, UUID.randomUUID())

    @PutMapping("/format-rules")
    fun changeFormatRules(
        @RequestParam userId: String,
        @RequestBody rules: ObjectRules,
    ): List<Rule> {
        println("Changing format rules")
        execService.changeFormatRules(userId, rules.rules, UUID.randomUUID())
        return rules.rules
    }

    @PutMapping("/lint-rules")
    fun changeLinRules(
        @RequestParam userId: String,
        @RequestBody rules: ObjectRules,
    ): List<Rule> {
        execService.changeFormatRules(userId, rules.rules, UUID.randomUUID())
        return rules.rules
    }

    @PutMapping("/update-snippet")
    fun updateSnippet(
        @RequestBody snippet: UpdateSnippetDto,
        exchange: ServerWebExchange,
    ) {
        val correlationId = exchange.getAttribute<String>(CorrelationIdFilter.CORRELATION_ID_KEY) ?: "default-correlation-id"
        snippetService.updateFormattedLintedSnippet(snippet.id, snippet.content, correlationId)
    }
}
