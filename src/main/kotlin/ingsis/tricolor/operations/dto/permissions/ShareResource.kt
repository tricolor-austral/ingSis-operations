package ingsis.tricolor.operations.dto.permissions

class ShareResource(
    val selfId: String,
    val otherId: String,
    val resourceId: String,
    val permissions: List<Permissons> = listOf(Permissons.READ, Permissons.WRITE),
)
