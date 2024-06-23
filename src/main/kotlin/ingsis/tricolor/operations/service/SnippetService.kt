package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.dto.apicalls.UserResourcePermission
import ingsis.tricolor.operations.entity.Snippet
import org.springframework.data.domain.Page

interface SnippetService {
    fun createSnippet(snippetDto: SnippetCreateDto): Snippet

    fun getSnippets(
        userId: String,
        page: Int,
        size: Int,
    ): Page<GetSnippetDto>

    fun getSnippetById(
        userId: String,
        snippetId: Long,
    ): GetSnippetDto

    fun updateSnippet(
        userId: String,
        updateSnippetDto: UpdateSnippetDto,
    ): GetSnippetDto

    fun deleteSnippet(
        userId: String,
        snippetId: Long,
    )

    fun getSnippet(id: String): String

    fun shareSnippet(
        authorId: String,
        friendId: String,
        snippetId: Long,
    ): UserResourcePermission
}
