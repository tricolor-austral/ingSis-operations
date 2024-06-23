@file:Suppress("ktlint:standard:no-wildcard-imports")

package ingsis.tricolor.operations.controller

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.ShareSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.dto.apicalls.UserResourcePermission
import ingsis.tricolor.operations.entity.Snippet
import ingsis.tricolor.operations.error.HttpException
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/snippets")
class SnippetController(val snippetService: SnippetService) {
    @ExceptionHandler(HttpException::class)
    fun handleException(exception: HttpException): ResponseEntity<String> {
        return ResponseEntity(exception.message, exception.status)
    }

    @PostMapping()
    fun createSnippet(
        @RequestBody snippetDto: SnippetCreateDto,
    ): Snippet {
        return snippetService.createSnippet(snippetDto)
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
    ): GetSnippetDto {
        return snippetService.updateSnippet(userId, updateSnippetDto)
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
}
