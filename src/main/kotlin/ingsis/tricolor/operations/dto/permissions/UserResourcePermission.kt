package ingsis.tricolor.operations.dto.permissions

class UserResourcePermission(
    val userId: String,
    val resourceId: String,
    val permissions: List<Permissons>,
)
