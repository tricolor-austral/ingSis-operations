package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.dto.permissions.UserResourcePermission
import ingsis.tricolor.operations.entity.Snippet
import org.springframework.data.domain.Page

interface SnippetService {
    fun createSnippet(
        snippetDto: SnippetCreateDto,
        correlationId: String,
    ): Snippet

    fun getSnippets(
        userId: String,
        page: Int,
        size: Int,
    ): Page<GetSnippetDto>

    fun getSnippetById(
        userId: String,
        snippetId: Long,
    ): GetSnippetDto

    fun getAllSnippetsByUser(userId: String): List<GetSnippetDto>

    fun updateSnippet(
        userId: String,
        updateSnippetDto: UpdateSnippetDto,
        correlationId: String,
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

    fun getUsers(
        pageNumber: Int,
        pageSize: Int,
    ): Page<String> // TODO: armarle un repositorio para los users
}
