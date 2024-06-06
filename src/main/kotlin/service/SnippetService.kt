package service

import dto.SnippetCreateDto
import entity.Snippet
import org.springframework.data.domain.Page

interface SnippetService {
    fun createSnippet(snippetDto : SnippetCreateDto): Snippet
    fun getSnippets(page: Int, size: Int): Page<Snippet>




}