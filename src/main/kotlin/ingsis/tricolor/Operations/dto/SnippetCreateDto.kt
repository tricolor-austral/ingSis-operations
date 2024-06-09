package ingsis.tricolor.Operations.dto

data class SnippetCreateDto (
        val name: String, val language: String, val authorId: String,
        val content: String, val compliance: String,val extension: String
){}