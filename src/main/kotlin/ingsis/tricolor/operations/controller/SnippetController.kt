@file:Suppress("ktlint:standard:no-wildcard-imports")

package ingsis.tricolor.operations.controller

import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.entity.Snippet
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/snippets")
class SnippetController(val snippetService: SnippetService) {
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

//    @GetMapping()
//    fun getSnippets(
//        @RequestParam pageNumber: Int,
//        @RequestParam pageSize: Int,
//    ): Page<GetSnippetDto> {
//        println("getSnippets")
//        return snippetService.getSnippets(pageNumber, pageSize)
//    }

    @PutMapping("/{id}")
    fun updateSnippet(
        @PathVariable id: Long,
        @RequestBody updateSnippetDto: UpdateSnippetDto,
    ): Snippet {
        // TODO() Agregar chequeo de permisos para editar una vez que se implemente la autenticación en la UI y el módulo permisos
        return snippetService.updateSnippet(id, updateSnippetDto)
    }

    @DeleteMapping("/{id}")
    fun deleteSnippet(
        @PathVariable id: Long,
    ) {
        // TODO() Agregar chequeo de permisos para borrar una vez que se implemente la autenticación en la UI y el módulo permisos
        snippetService.deleteSnippet(id)
    }
}
