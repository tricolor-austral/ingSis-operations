package ingsis.tricolor.operations.dto.apicalls

class ResourcePermissionCreateDto(
    val userId: String,
    val resourceId: String,
    val permissions: List<String>,
)
