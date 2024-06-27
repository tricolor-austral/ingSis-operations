@file:Suppress("ktlint:standard:no-wildcard-imports")

package ingsis.tricolor.operations.controller

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.ShareSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.dto.permissions.UserResourcePermission
import ingsis.tricolor.operations.entity.Snippet
import ingsis.tricolor.operations.error.HttpException
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("/snippets")
class SnippetController(
    @Autowired val snippetService: SnippetService,
) {
    @ExceptionHandler(HttpException::class)
    fun handleException(exception: HttpException): ResponseEntity<String> {
        return ResponseEntity(exception.message, exception.status)
    }

    @PostMapping()
    fun createSnippet(
        @RequestBody snippetDto: SnippetCreateDto,
        exchange: ServerWebExchange,
    ): Snippet {
        val correlationId = exchange.getAttribute<String>(CorrelationIdFilter.CORRELATION_ID_KEY) ?: "default-correlation-id"
        return snippetService.createSnippet(snippetDto, correlationId)
    }

    @GetMapping()
    fun getSnippets(
        @RequestParam userId: String,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
    ): Page<GetSnippetDto> {
        println("getSnippets")
        return snippetService.getSnippets(userId, pageNumber, pageSize)
    }

    @GetMapping("/byId")
    fun getSnippetById(
        @RequestParam userId: String,
        @RequestParam snippetId: String,
    ): GetSnippetDto {
        return snippetService.getSnippetById(userId, snippetId.toLong())
    }

    @PutMapping()
    fun updateSnippet(
        @RequestParam userId: String,
        @RequestBody updateSnippetDto: UpdateSnippetDto,
        exchange: ServerWebExchange,
    ): GetSnippetDto {
        val correlationId = exchange.getAttribute<String>(CorrelationIdFilter.CORRELATION_ID_KEY) ?: "default-correlation-id"
        return snippetService.updateSnippet(userId, updateSnippetDto, correlationId)
    }

    @DeleteMapping("")
    fun deleteSnippet(
        @RequestParam userId: String,
        @RequestParam snippetId: String,
    ) {
        println("user: $userId, snippet: $snippetId")
        snippetService.deleteSnippet(userId, snippetId.toLong())
    }

    @PostMapping("/share")
    fun shareSnippet(
        @RequestParam userId: String,
        @RequestBody snippetFriend: ShareSnippetDto,
    ): UserResourcePermission {
        return snippetService.shareSnippet(userId, snippetFriend.friendId, snippetFriend.snippetId.toLong())
    }

    @GetMapping("users")
    fun getUsers(
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
    ): Page<String> {
        return snippetService.getUsers(pageNumber, pageSize)
    }
}
