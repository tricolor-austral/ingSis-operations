package ingsis.tricolor.operations.service.impls

import ingsis.tricolor.operations.dto.execution.ChangeRulesDto
import ingsis.tricolor.operations.dto.execution.ExecutionDataDto
import ingsis.tricolor.operations.dto.execution.ExecutionResponseDto
import ingsis.tricolor.operations.dto.execution.Rule
import ingsis.tricolor.operations.dto.permissions.*
import ingsis.tricolor.operations.error.HttpException
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
import java.util.*

@Service
class DefaultApiCalls(
    @Value("\${permission.url}") permissionUrl: String,
    @Value("\${asset.url}") assetUrl: String,
    @Value("runner.url") runnerUrl: String,
) : APICalls {
    private val permissionApi = WebClient.builder().baseUrl("http://$permissionUrl").build()
    private val assetServiceApi = WebClient.builder().baseUrl("http://$assetUrl/v1/asset").build()
    private val runnerApi = WebClient.builder().baseUrl("http://$runnerUrl").build()

    override fun createResourcePermission(resourceData: ResourcePermissionCreateDto): Boolean {
        try {
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

    override fun getUsers(): List<String> {
        return permissionApi.get()
            .uri("/user")
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<String>>() {})
            .block() ?: throw NotFoundException()
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

    override fun formatSnippet(data: ExecutionDataDto): ExecutionResponseDto {
        return runnerApi.post()
            .uri("/format")
            .bodyValue(data)
            .retrieve()
            .bodyToMono(ExecutionResponseDto::class.java)
            .block() ?: throw HttpException("Could not format correctly", HttpStatus.EXPECTATION_FAILED)
    }

    override fun getFormatRules(
        userId: String,
        correlationId: UUID,
    ): List<Rule> {
        return runnerApi.get()
            .uri("/format/$userId")
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<Rule>>() {})
            .block() ?: throw HttpException("Could not ger rules", HttpStatus.EXPECTATION_FAILED)
    }

    override fun getLintRules(
        userId: String,
        correlationId: UUID,
    ): List<Rule> {
        return runnerApi.get()
            .uri("/lint/$userId")
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<Rule>>() {})
            .block() ?: throw HttpException("Could not ger rules", HttpStatus.EXPECTATION_FAILED)
    }

    override fun changeFormatRules(
        userId: String,
        rules: List<Rule>,
        snippets: List<ExecutionDataDto>,
        correlationId: UUID,
    ) {
        val data = ChangeRulesDto(userId, rules, snippets, correlationId)
        runnerApi.put()
            .uri("/redis/format")
            .bodyValue(data)
            .retrieve()
            .bodyToMono(Unit::class.java)
            .block() ?: throw HttpException("Error formatting rules", HttpStatus.EXPECTATION_FAILED)
    }

    override fun changeLintRules(
        userId: String,
        rules: List<Rule>,
        snippets: List<ExecutionDataDto>,
        correlationId: UUID,
    ) {
        val data = ChangeRulesDto(userId, rules, snippets, correlationId)
        runnerApi.put()
            .uri("/redis/lint")
            .bodyValue(data)
            .retrieve()
            .bodyToMono(Unit::class.java)
            .block() ?: throw HttpException("Error linting rules", HttpStatus.EXPECTATION_FAILED)
    }
}
