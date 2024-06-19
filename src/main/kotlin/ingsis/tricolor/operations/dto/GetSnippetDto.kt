package ingsis.tricolor.operations.dto

import ingsis.tricolor.operations.entity.Snippet

data class GetSnippetDto(
    val id: String,
    val name: String,
    val language: String,
    val author: String,
    val content: String,
    val compliance: String,
    val extension: String,
) {
    companion object {
        fun from(snippet: Snippet): GetSnippetDto {
            return GetSnippetDto(
                snippet.id.toString(),
                snippet.name,
                snippet.language,
                snippet.authorId,
                snippet.content,
                snippet.compliance,
                snippet.extension,
            )
        }
    }
}
