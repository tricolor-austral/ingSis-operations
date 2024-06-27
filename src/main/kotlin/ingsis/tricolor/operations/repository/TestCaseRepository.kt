package ingsis.tricolor.operations.repository

import ingsis.tricolor.operations.entity.TestCase
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TestCaseRepository : CrudRepository<TestCase, Long> {
    fun findBySnippetId(snippetId: Long): MutableList<TestCase>
}
