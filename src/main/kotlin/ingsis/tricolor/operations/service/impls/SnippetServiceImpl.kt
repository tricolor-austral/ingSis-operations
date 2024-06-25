package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.dto.permissions.ResourcePermissionCreateDto
import ingsis.tricolor.operations.dto.permissions.UserResourcePermission
import ingsis.tricolor.operations.entity.Snippet
import ingsis.tricolor.operations.error.NotFoundException
import ingsis.tricolor.operations.error.UnauthorizedException
import ingsis.tricolor.operations.repository.SnippetRepositoryCrud
import ingsis.tricolor.operations.repository.SnippetRepositoryPage
import ingsis.tricolor.operations.service.APICalls
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SnippetServiceImpl
    @Autowired
    constructor(
        val snippetRepositoryPage: SnippetRepositoryPage,
        val snippetRepositoryCrud: SnippetRepositoryCrud,
        val apiCalls: APICalls,
    ) : SnippetService {
        override fun createSnippet(snippetDto: SnippetCreateDto): Snippet {
            // TODO: manejar error id ya existe
            println("creating snippet...")
            val snippet = Snippet.from(snippetDto)
            val savedSnippet = this.snippetRepositoryCrud.save(snippet)
            println("snippet created")
            createResourcePermissions(snippetDto, savedSnippet)
            saveSnippetOnAssetService(savedSnippet.id.toString(), snippetDto.content)
            return savedSnippet
        }

        override fun getSnippets(
            userId: String,
            page: Int,
            size: Int,
        ): Page<GetSnippetDto> {
            val resources = apiCalls.getAllUserResources(userId)
            val context = snippetRepositoryCrud.findAllById(resources.map { it.resourceId.toLong() })
            val snippets =
                context.map {
                    val content = apiCalls.getSnippet(it.id.toString())
                    GetSnippetDto.from(it, content)
                }
            return toPageable(snippets, page, size)
        }

        override fun getSnippetById(
            userId: String,
            snippetId: Long,
        ): GetSnippetDto {
            val context = snippetRepositoryCrud.findById(snippetId).orElse(null) ?: throw NotFoundException()
            val permission = apiCalls.getAllUserResources(userId).filter { it.resourceId == snippetId.toString() }
            if (permission.isEmpty()) throw UnauthorizedException("User cannot acces this snippet")
            val content = apiCalls.getSnippet(snippetId.toString())
            return GetSnippetDto.from(context, content)
        }

        override fun getAllSnippetsByUser(userId: String): List<GetSnippetDto> {
            val resources = apiCalls.getAllUserResources(userId)
            val context = snippetRepositoryCrud.findAllById(resources.map { it.resourceId.toLong() })
            val snippets =
                context.map {
                    val content = apiCalls.getSnippet(it.id.toString())
                    GetSnippetDto.from(it, content)
                }
            return snippets
        }

        override fun updateSnippet(
            userId: String,
            updateSnippetDto: UpdateSnippetDto,
        ): GetSnippetDto {
            val snippet = checkSnippetExists(updateSnippetDto.id)
            checkUserCanModify(userId, updateSnippetDto.id.toString())
            saveSnippetOnAssetService(updateSnippetDto.id.toString(), updateSnippetDto.content)
            return GetSnippetDto.from(snippet, updateSnippetDto.content)
        }

        override fun deleteSnippet(
            userId: String,
            snippetId: Long,
        ) {
            println("id: $snippetId")
            apiCalls.deleteResourcePermissions(userId, snippetId.toString())
            println("deleted from resources")
            apiCalls.deleteSnippet(snippetId.toString())
            println("deleted from asset service")
            val snippet = snippetRepositoryCrud.findById(snippetId).orElse(null)
            if (snippet == null) {
                println("snippet not found")
                throw NotFoundException()
            }
            snippetRepositoryCrud.delete(snippet)
        }

        override fun getSnippet(id: String): String {
            return apiCalls.getSnippet(id)
        }

        override fun shareSnippet(
            authorId: String,
            friendId: String,
            snippetId: Long,
        ): UserResourcePermission {
            return apiCalls.shareResource(authorId, snippetId.toString(), friendId)
        }

        override fun getUsers(
            pageNumber: Int,
            pageSize: Int,
        ): Page<String> {
            val snippets = apiCalls.getUsers()
            val total = snippets.size
            val start = (pageNumber * pageSize).coerceAtMost(total)
            val end = (start + pageSize).coerceAtMost(total)
            val subList = snippets.subList(start, end)
            return PageImpl(subList, PageRequest.of(pageNumber, pageSize), total.toLong())
        }

        override fun updateFormattedLintedSnippet(
            snippetId: Long,
            content: String,
        ) {
            apiCalls.saveSnippet(snippetId.toString(), content)
        }

        private fun createResourcePermissions(
            snippetDto: SnippetCreateDto,
            savedSnippet: Snippet,
        ) {
            println("creating permissions for snippet...")
            val permissions = listOf("READ", "WRITE")
            val dto = ResourcePermissionCreateDto(snippetDto.authorId, savedSnippet.id.toString(), permissions)
            apiCalls.createResourcePermission(dto)
            println("permissions created")
        }

        private fun saveSnippetOnAssetService(
            id: String,
            content: String,
        ) {
            println("saving on asset service...")
            apiCalls.saveSnippet(id, content)
            println("asset saved!")
        }

        private fun toPageable(
            snippets: List<GetSnippetDto>,
            page: Int,
            size: Int,
        ): Page<GetSnippetDto> {
            val total = snippets.size
            val start = (page * size).coerceAtMost(total)
            val end = (start + size).coerceAtMost(total)
            val subList = snippets.subList(start, end)
            return PageImpl(subList, PageRequest.of(page, size), total.toLong())
        }

        private fun checkUserCanModify(
            userId: String,
            snippetId: String,
        ) {
            val permissions = apiCalls.userCanWrite(userId, snippetId)
            if (!permissions.permissions.contains("WRITE")) {
                throw UnauthorizedException()
            }
        }

        private fun checkSnippetExists(id: Long): Snippet {
            return snippetRepositoryCrud.findById(id).orElseThrow { throw NotFoundException() }
        }
    }
