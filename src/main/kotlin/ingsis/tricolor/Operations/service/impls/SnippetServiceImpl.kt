package ingsis.tricolor.Operations.service.impls

import ingsis.tricolor.Operations.dto.GetSnippetDto
import ingsis.tricolor.Operations.dto.SnippetCreateDto
import ingsis.tricolor.Operations.entity.Snippet
import org.springframework.data.domain.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ingsis.tricolor.Operations.repository.SnippetRepositoryCrud
import ingsis.tricolor.Operations.repository.SnippetRepositoryPage
import ingsis.tricolor.Operations.service.SnippetService


@Service
class SnippetServiceImpl @Autowired constructor(val snippetRepositoryPage : SnippetRepositoryPage, val snippetRepositoryCrud: SnippetRepositoryCrud) : SnippetService {
    override fun createSnippet(snippetDto: SnippetCreateDto): Snippet {
        // manejar error id ya existe
        val snippet = Snippet.from(snippetDto)
        return this.snippetRepositoryCrud.save(snippet)
    }

    override fun getSnippets(page: Int, size: Int): Page<GetSnippetDto> {
        val snippets = snippetRepositoryPage.findAll(PageRequest.of(page, size))
        return snippets.map { GetSnippetDto.from(it) }
    }


}