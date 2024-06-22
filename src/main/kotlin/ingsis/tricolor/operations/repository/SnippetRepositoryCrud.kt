package ingsis.tricolor.operations.repository

import ingsis.tricolor.operations.entity.Snippet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SnippetRepositoryCrud : CrudRepository<Snippet, Long>
