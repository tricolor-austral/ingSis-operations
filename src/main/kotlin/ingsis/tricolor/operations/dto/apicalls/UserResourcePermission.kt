package ingsis.tricolor.operations.dto.apicalls

class UserResourcePermission(
    val userId: String,
    val resourceId: String,
    val permissions: List<Permissons>,
)
