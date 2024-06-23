package ingsis.tricolor.operations.dto.execution

data class Rules(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val value: Any, // string number o null
)
