package ingsis.tricolor.operations.entity

import ingsis.tricolor.operations.dto.SnippetCreateDto
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
class Snippet {
    @Id()
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    var name: String = ""

    @Column
    var content: String = ""

    @Column
    var compliance: String = ""

    @Column
    var authorId: String = ""

    @Column
    var language: String = ""

    @Column
    var extension: String = ""

    companion object {
        fun from(snippetDto: SnippetCreateDto): Snippet {
            val snippet = Snippet()
            snippet.name = snippetDto.name
            snippet.content = snippetDto.content
            snippet.compliance = snippetDto.compliance
            snippet.authorId = snippetDto.authorId
            snippet.language = snippetDto.language
            snippet.extension = snippetDto.extension
            return snippet
        }
    }
}
