package ingsis.tricolor.operations.server
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestLogFilter : OncePerRequestFilter() {
    val logger = LoggerFactory.getLogger(RequestLogFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val uri = request.requestURI
        val method = request.method
        val prefix = "$method $uri"
        try {
            return filterChain.doFilter(request, response)
        } finally {
            val statusCode = response.status
            logger.info("$prefix - $statusCode")
        }
    }
}
