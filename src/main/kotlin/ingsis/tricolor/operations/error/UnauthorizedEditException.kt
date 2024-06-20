package ingsis.tricolor.operations.error

import org.springframework.http.HttpStatus

class UnauthorizedEditException : HttpException(
    "User doesn't have permission to modify snippet",
    HttpStatus.UNAUTHORIZED,
)
