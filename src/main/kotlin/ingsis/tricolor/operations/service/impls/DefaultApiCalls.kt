package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.apicalls.ResourcePermissionCreateDto
import ingsis.tricolor.operations.dto.apicalls.UserResource
import ingsis.tricolor.operations.service.APICalls
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.security.Permissions

@Service
class DefaultApiCalls : APICalls {
    private val permissionApi = WebClient.builder().baseUrl("http://localhost:8081").build()
    private val assetServiceApi = WebClient.builder().baseUrl("http://localhost:8080/v1/asset").build()

    override fun createResourcePermission(resourceData: ResourcePermissionCreateDto): Boolean {
        try {
            permissionApi.post()
                .uri("/resource/create-resource")
                .bodyValue(resourceData)
                .retrieve()
            return true
        } catch (e: Error) {
            println(e.message)
            return false
        }
    }

    override fun getUserResourcePermissions(userResource: UserResource): List<Permissions> {
        TODO("Implement method")
    }

    override fun saveSnippet(
        key: String,
        snippet: String,
    ): Boolean {
        try {
            val responseStatus =
                assetServiceApi.post()
                    .uri("/snippets/{key}", key)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(snippet)
                    .exchangeToMono { clientResponse ->
                        if (clientResponse.statusCode() == HttpStatus.CREATED) {
                            Mono.just(HttpStatus.CREATED)
                        } else {
                            Mono.just(HttpStatus.BAD_REQUEST)
                        }
                    }
                    .block()
            println(responseStatus)
            return responseStatus == HttpStatus.CREATED
        } catch (e: Error) {
            println(e.message)
            return false
        }
    }

    override fun getSnippet(key: String): String {
        try {
            val response =
                assetServiceApi.get()
                    .uri("/snippets/{key}", key)
                    .retrieve()
                    .bodyToMono(String::class.java)
                    .block() ?: throw RuntimeException("Failed to retrieve snippet")
            print(response)
            return response
        } catch (e: Exception) {
            print(e.message)
            return ""
        }
    }
}
