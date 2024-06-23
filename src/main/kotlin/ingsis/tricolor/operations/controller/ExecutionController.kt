package ingsis.tricolor.operations.controller

import ingsis.tricolor.operations.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExecutionController
    @Autowired
    constructor(private val snippetService: SnippetService) {
        @PostMapping
        fun completeLater() {
        }
    }
