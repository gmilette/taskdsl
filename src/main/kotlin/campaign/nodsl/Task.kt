package campaign.nodsl

import java.util.UUID


data class Task(
    val name: String = UUID.randomUUID().toString(),
    val type: TaskType,
    val hour: Int = -1,
    val hourRange: IntRange? = null,
    val after: TimeAfter? = null,
    val shipping: Shipping = Shipping.None,
    val date: String? = null,
)

data class TimeAfter(val taskName: String = "", val task: Task? = null, val hours: Int, val days: Int = 0)

enum class TaskType {
    None, Email, Call, Mail
}

enum class Shipping {
    None,
    Overnight,
    ThreeDay,
    Ground
}

enum class Repeat {
    None,
    Daily,
    Weekly,
    Monthly
}

data class Campaign(val tasks: List<Task>)

