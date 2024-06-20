@file:Suppress("ktlint:standard:no-wildcard-imports")

package ingsis.tricolor.operations.controller

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.entity.Snippet
import ingsis.tricolor.operations.error.HttpException
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun getSnippet(
        @RequestParam id: String,
    ): String {
        return snippetService.getSnippet(id)
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

    @PutMapping()
    fun updateSnippet(
        @RequestParam userId: String,
        @RequestBody updateSnippetDto: UpdateSnippetDto,
    ): GetSnippetDto {
        // TODO() Agregar chequeo de permisos para editar una vez que se implemente la autenticación en la UI y el módulo permisos
        return snippetService.updateSnippet(userId, updateSnippetDto)
    }

    @DeleteMapping("/{id}")
    fun deleteSnippet(
        @PathVariable id: Long,
    ) {
        // TODO() Agregar chequeo de permisos para borrar una vez que se implemente la autenticación en la UI y el módulo permisos
        snippetService.deleteSnippet(id)
    }
}
