package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.entity.Snippet
import org.springframework.data.domain.Page

interface SnippetService {
    fun createSnippet(snippetDto: SnippetCreateDto): Snippet

    fun getSnippets(
        page: Int,
        size: Int,
    ): Page<GetSnippetDto>

    fun updateSnippet(
        id: Long,
        updateSnippetDto: UpdateSnippetDto,
    ): Snippet

    fun deleteSnippet(id: Long)

    fun getSnippet(id: String): String
}
