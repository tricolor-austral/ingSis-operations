package ingsis.tricolor.operations.dto.permissions

class ResourcePermissionCreateDto(
    val userId: String,
    val resourceId: String,
    val permissions: List<String>,
)
