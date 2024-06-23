package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.execution.ExecutionDataDto
import ingsis.tricolor.operations.dto.execution.ExecutionResponseDto
import ingsis.tricolor.operations.dto.execution.Rules
import ingsis.tricolor.operations.dto.permissions.PermissionCreateResponse
import ingsis.tricolor.operations.dto.permissions.ResourcePermissionCreateDto
import ingsis.tricolor.operations.dto.permissions.UserResourcePermission
import java.util.UUID

interface APICalls {
    // Permissions API:
    fun createResourcePermission(resourceData: ResourcePermissionCreateDto): Boolean

    fun getAllUserResources(userId: String): List<PermissionCreateResponse>

    fun userCanWrite(
        userId: String,
        resourceId: String,
    ): PermissionCreateResponse

    fun deleteResourcePermissions(
        userId: String,
        resourceId: String,
    )

    fun shareResource(
        userId: String,
        resourceId: String,
        otherId: String,
    ): UserResourcePermission

    fun getUsers(): List<String>

    // Asset service:
    fun saveSnippet(
        key: String,
        snippet: String,
    ): Boolean

    fun getSnippet(key: String): String

    fun deleteSnippet(key: String): Boolean

    // Runner API:

    fun formatSnippet(data: ExecutionDataDto): ExecutionResponseDto

    fun getFormatRules(
        userId: String,
        correlationId: UUID,
    ): List<Rules>

    fun getLintRules(
        userId: String,
        correlationId: UUID,
    ): List<Rules>

    fun changeFormatRules(
        userId: String,
        rules: List<Rules>,
        snippets: List<ExecutionDataDto>,
        correlationId: UUID,
    )

    fun changeLintRules(
        userId: String,
        rules: List<Rules>,
        snippets: List<ExecutionDataDto>,
        correlationId: UUID,
    )
}
