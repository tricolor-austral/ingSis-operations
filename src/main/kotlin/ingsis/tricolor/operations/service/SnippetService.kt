package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.entity.Snippet
import org.springframework.data.domain.Page

interface SnippetService {
    fun createSnippet(snippetDto: SnippetCreateDto): Snippet

    fun getSnippets(
        userId: String,
        page: Int,
        size: Int,
    ): Page<GetSnippetDto>

    fun updateSnippet(
        userId: String,
        updateSnippetDto: UpdateSnippetDto,
    ): GetSnippetDto

    fun deleteSnippet(id: Long)

    fun getSnippet(id: String): String
}
