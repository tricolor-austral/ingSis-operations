package entity

import dto.SnippetCreateDto
import jakarta.persistence.*


@Entity
class Snippet {

    @Id
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