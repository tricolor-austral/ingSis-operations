package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.apicalls.*
import ingsis.tricolor.operations.error.NotFoundException
import ingsis.tricolor.operations.error.UnauthorizedException
import ingsis.tricolor.operations.service.APICalls
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class DefaultApiCalls(
    @Value("\${permission.url}") permissionUrl: String,
    @Value("\${asset.url}") assetUrl: String,
) : APICalls {
    private val permissionApi = WebClient.builder().baseUrl("http://$permissionUrl").build()
    private val assetServiceApi = WebClient.builder().baseUrl("http://$assetUrl/v1/asset").build()

    override fun createResourcePermission(resourceData: ResourcePermissionCreateDto): Boolean {
        try {
            val response =
                permissionApi.post()
                    .uri("/resource/create-resource")
                    .bodyValue(resourceData)
                    .retrieve()
                    .bodyToMono(PermissionCreateResponse::class.java)
                    .block()
            return true
        } catch (e: Error) {
            println(e.message)
            return false
        }
    }

    override fun getAllUserResources(userId: String): List<PermissionCreateResponse> {
        val response =
            permissionApi.get()
                .uri("/resource/all-by-userId?id=$userId")
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<List<PermissionCreateResponse>>() {})
                .block() ?: throw RuntimeException("Unable to fetch the resources")
        return response
    }

    override fun userCanWrite(
        userId: String,
        resourceId: String,
    ): PermissionCreateResponse {
        return permissionApi.get()
            .uri("/resource/can-write")
            .cookie("userId", userId)
            .cookie("resourceId", resourceId)
            .retrieve()
            .bodyToMono(PermissionCreateResponse::class.java)
            .block() ?: throw RuntimeException("Unable to fetch permissions")
    }

    override fun deleteResourcePermissions(
        userId: String,
        resourceId: String,
    ) {
        permissionApi.delete()
            .uri("/resource/{resourceId}", resourceId)
            .cookie("userId", userId)
            .retrieve()
            .bodyToMono(String::class.java)
            .block() ?: throw UnauthorizedException()
    }

    override fun shareResource(
        userId: String,
        resourceId: String,
        otherId: String,
    ): UserResourcePermission {
        val shareDto = ShareResource(userId, otherId, resourceId)
        return permissionApi.post()
            .uri("/resource/share-resource")
            .bodyValue(shareDto)
            .retrieve()
            .bodyToMono(UserResourcePermission::class.java)
            .block() ?: throw UnauthorizedException("User cannot share this resource as he is not the owner")
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
        val response =
            assetServiceApi.get()
                .uri("/snippets/{key}", key)
                .retrieve()
                .bodyToMono(String::class.java)
                .block() ?: throw NotFoundException()
        return response
    }

    override fun deleteSnippet(key: String): Boolean {
        println("key: $key")
        assetServiceApi.delete()
            .uri("/snippets/{key}", key)
            .exchangeToMono { clientResponse ->
                if (clientResponse.statusCode() == HttpStatus.NO_CONTENT) {
                    Mono.just(HttpStatus.OK)
                } else {
                    Mono.just(HttpStatus.BAD_REQUEST)
                }
            }
            .block() ?: throw NotFoundException()
        return true
    }
}
