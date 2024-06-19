package ingsis.tricolor.operations.service

import ingsis.tricolor.operations.dto.apicalls.ResourcePermissionCreateDto
import ingsis.tricolor.operations.dto.apicalls.UserResource
import java.security.Permissions

interface APICalls {
    fun createResourcePermission(resourceData: ResourcePermissionCreateDto): Boolean

    fun getUserResourcePermissions(userResource: UserResource): List<Permissions>

    fun saveSnippet(
        key: String,
        snippet: String,
    ): Boolean

    fun getSnippet(key: String): String
}
