package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.GetSnippetDto
import ingsis.tricolor.operations.dto.SnippetCreateDto
import ingsis.tricolor.operations.dto.UpdateSnippetDto
import ingsis.tricolor.operations.dto.apicalls.ResourcePermissionCreateDto
import ingsis.tricolor.operations.entity.Snippet
import ingsis.tricolor.operations.repository.SnippetRepositoryCrud
import ingsis.tricolor.operations.repository.SnippetRepositoryPage
import ingsis.tricolor.operations.service.APICalls
import ingsis.tricolor.operations.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SnippetServiceImpl
    @Autowired
    constructor(
        val snippetRepositoryPage: SnippetRepositoryPage,
        val snippetRepositoryCrud: SnippetRepositoryCrud,
        val apiCalls: APICalls,
        repositoryCrud: SnippetRepositoryCrud,
    ) : SnippetService {
        override fun createSnippet(snippetDto: SnippetCreateDto): Snippet {
            // TODO: manejar error id ya existe
            println("creating snippet...")
            val snippet = Snippet.from(snippetDto)
            val savedSnippet = this.snippetRepositoryCrud.save(snippet)
            println("snippet created")
//            createResourcePermissions(snippetDto, savedSnippet)
            saveSnippetOnAssetService(savedSnippet, snippet)
            return savedSnippet
        }

        override fun getSnippets(
            page: Int,
            size: Int,
        ): Page<GetSnippetDto> {
            val snippets = snippetRepositoryPage.findAll(PageRequest.of(page, size))
            return snippets.map { GetSnippetDto.from(it) }
        }

        override fun updateSnippet(
            id: Long,
            updateSnippetDto: UpdateSnippetDto,
        ): Snippet {
            val snippet = snippetRepositoryCrud.findById(id).orElseThrow { throw Exception("Snippet not found") }
            snippet.content = updateSnippetDto.content
            return snippetRepositoryCrud.save(snippet)
        }

        override fun deleteSnippet(id: Long) {
            snippetRepositoryCrud.deleteById(id)
        }

        override fun getSnippet(id: String): String {
            println("getting snippet with id: $id")
            return apiCalls.getSnippet(id)
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
            savedSnippet: Snippet,
            snippet: Snippet,
        ) {
            println("saving on asset service...")
            apiCalls.saveSnippet(savedSnippet.id.toString(), snippet.content)
            println("asset saved!")
        }
    }
