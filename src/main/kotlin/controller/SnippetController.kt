package controller

import dto.SnippetCreateDto
import entity.Snippet
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import service.SnippetService
import service.impls.SnippetServiceImpl

@RestController
@RequestMapping("/snippets")
class SnippetController(val snippetService: SnippetService) {


    @PutMapping()
    fun createSnippet(@RequestBody snippetDto : SnippetCreateDto): Snippet {
        return snippetService.createSnippet(snippetDto)
    }

    @GetMapping()
    fun getSnippets(@RequestParam pageNumber : Int, @RequestParam pageSize: Int): Page<Snippet> {
        return snippetService.getSnippets(pageNumber, pageSize)
    }





}