package ingsis.tricolor.operations.dto.execution

data class Rule(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val value: Any, // string number o null
)
