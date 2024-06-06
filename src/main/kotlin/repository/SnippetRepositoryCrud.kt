package repository

import entity.Snippet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SnippetRepositoryCrud : CrudRepository<Snippet, Long> {

}