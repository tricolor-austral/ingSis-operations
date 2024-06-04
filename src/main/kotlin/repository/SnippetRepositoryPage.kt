package repository

import entity.Snippet
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SnippetRepositoryPage : PagingAndSortingRepository<Snippet, Long> {


}