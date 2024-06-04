package dto

data class SnippetCreateDto (
        val name: String, val language: String, val author: String,
        val content: String, val compliance: String,val extension: String
){}