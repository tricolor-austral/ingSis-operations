package ingsis.tricolor.operations.dto

import ingsis.tricolor.operations.entity.Snippet

data class GetSnippetDto(
    val id: String,
    val name: String,
    val language: String,
    val content: String,
    var compliance: String,
    val extension: String,
) {
    companion object {
        fun from(
            snippet: Snippet,
            content: String,
            compliance: String? = null
        ): GetSnippetDto {
            return GetSnippetDto(
                snippet.id.toString(),
                snippet.name,
                snippet.language,
                content,
                compliance?: snippet.compliance,
                snippet.extension,
            )
        }
    }
}
