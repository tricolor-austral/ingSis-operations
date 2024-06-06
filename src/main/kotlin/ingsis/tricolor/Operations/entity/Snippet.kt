package ingsis.tricolor.Operations.entity

import ingsis.tricolor.Operations.dto.SnippetCreateDto
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull


@Entity
class Snippet {

    @Id()
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0;

    @Column
    var name : String = "";

    @Column
    var content: String = "";

    @Column
    var compliance: String = "";

    @Column
    var author: String = "";

    @Column
    var language : String = "";

    @Column
    var extension: String = "";


    companion object {
        fun from(snippetDto: SnippetCreateDto): Snippet {
            val snippet = Snippet()
            snippet.name = snippetDto.name
            snippet.content = snippetDto.content
            snippet.compliance = snippetDto.compliance
            snippet.author = snippetDto.author
            snippet.language = snippetDto.language
            snippet.extension = snippetDto.extension
            return snippet
        }
    }


}