package ingsis.tricolor.Operations.controller

import ingsis.tricolor.Operations.dto.GetSnippetDto
import ingsis.tricolor.Operations.dto.SnippetCreateDto
import ingsis.tricolor.Operations.dto.UpdateSnippetDto
import ingsis.tricolor.Operations.entity.Snippet
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import ingsis.tricolor.Operations.service.SnippetService
import ingsis.tricolor.Operations.service.impls.SnippetServiceImpl
import kotlin.math.log

@RestController
@RequestMapping("/snippets")
class SnippetController(val snippetService: SnippetService) {


    @PostMapping()
    fun createSnippet(@RequestBody snippetDto : SnippetCreateDto): Snippet {
        return snippetService.createSnippet(snippetDto)
    }

    @GetMapping()
    fun getSnippets(@RequestParam pageNumber : Int, @RequestParam pageSize: Int): Page<GetSnippetDto> {
        println("getSnippets")
        return snippetService.getSnippets(pageNumber, pageSize)
    }

    @PutMapping("/{id}")
    fun updateSnippet(@PathVariable id: Long, @RequestBody updateSnippetDto: UpdateSnippetDto): Snippet {
        // TODO() Agregar chequeo de permisos para editar una vez que se implemente la autenticación en la UI y el módulo permisos
        return snippetService.updateSnippet(id, updateSnippetDto)
    }


    @DeleteMapping("/{id}")
    fun deleteSnippet(@PathVariable id: Long) {
        // TODO() Agregar chequeo de permisos para borrar una vez que se implemente la autenticación en la UI y el módulo permisos
        snippetService.deleteSnippet(id)
    }


}