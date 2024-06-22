package ingsis.tricolor.operations.error

import org.springframework.http.HttpStatus

class UnauthorizedException(
    override val message: String = "User doesn't have permission to modify or delete snippet snippet",
) : HttpException(
        message,
        HttpStatus.UNAUTHORIZED,
    )
