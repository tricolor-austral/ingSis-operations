package ingsis.tricolor.Operations.service

import ingsis.tricolor.Operations.dto.GetSnippetDto
import ingsis.tricolor.Operations.dto.SnippetCreateDto
import ingsis.tricolor.Operations.dto.UpdateSnippetDto
import ingsis.tricolor.Operations.entity.Snippet
import org.springframework.data.domain.Page

interface SnippetService {
    fun createSnippet(snippetDto : SnippetCreateDto): Snippet
    fun getSnippets(page: Int, size: Int): Page<GetSnippetDto>

    fun updateSnippet(id: Long, updateSnippetDto: UpdateSnippetDto): Snippet

    fun deleteSnippet(id: Long)




}