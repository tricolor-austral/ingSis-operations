package ingsis.tricolor.operations.error

import org.springframework.http.HttpStatus

class NotFoundException : HttpException("Snippet not found", HttpStatus.NOT_FOUND)
