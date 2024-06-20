package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.apicalls.PermissionCreateResponse
import ingsis.tricolor.operations.dto.apicalls.ResourcePermissionCreateDto
import ingsis.tricolor.operations.dto.apicalls.UserResource
import ingsis.tricolor.operations.dto.apicalls.UserResourcePermission
import java.security.Permissions

interface APICalls {
    // Permissions:
    fun createResourcePermission(resourceData: ResourcePermissionCreateDto): Boolean

    fun getUserResourcePermissions(userResource: UserResource): List<Permissions>

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

    // Asset service:
    fun saveSnippet(
        key: String,
        snippet: String,
    ): Boolean

    fun getSnippet(key: String): String

    fun deleteSnippet(key: String): Boolean
}
