package service.impls

import dto.SnippetCreateDto
import entity.Snippet
import org.springframework.data.domain.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import repository.SnippetRepositoryCrud
import repository.SnippetRepositoryPage
import service.SnippetService


@Service
class SnippetServiceImpl @Autowired constructor(val snippetRepositoryPage : SnippetRepositoryPage, val snippetRepositoryCrud: SnippetRepositoryCrud) : SnippetService{
    override fun createSnippet(snippetDto: SnippetCreateDto): Snippet {
        // manejar error id ya existe
        val snippet = Snippet.from(snippetDto)
        return this.snippetRepositoryCrud.save(snippet)
    }

    override fun getSnippets(page: Int, size: Int): Page<Snippet> {
        return snippetRepositoryPage.findAll(PageRequest.of(page, size))
    }


}