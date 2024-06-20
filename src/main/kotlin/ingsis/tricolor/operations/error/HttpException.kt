package ingsis.tricolor.operations.error

import org.springframework.http.HttpStatus

open class HttpException(message: String, val status: HttpStatus) : RuntimeException(message)
