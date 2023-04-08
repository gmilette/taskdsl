package campaign

import java.util.UUID


data class Task(
    val id: String = UUID.randomUUID().toString(),
    val type: TaskType = TaskType.None,
    val time: TimeAt = TimeAt.Unspecified,
    val shipping: Shipping = Shipping.None,
    val recipients: List<String> = emptyList()
)

sealed class TimeAt {
    object Unspecified: TimeAt()
    class Specific(val hour: Int, val date: String): TimeAt()
    class Date(val date: String): TimeAt()
    class Range(val hour: IntRange, val date: String): TimeAt()
    class HoursAfter(val task: String, val hours: Int): TimeAt() {
        fun withTask(task: Task): HoursAfterTask =
            HoursAfterTask(task, hours)
    }
    class HoursAfterTask(val task: Task? = null, val hours: Int): TimeAt()
}

fun DaysAfter(task: String, days: Int): TimeAt.HoursAfter =
    TimeAt.HoursAfter(task, days * 24)

//data class TimeAfter(val task: String, val time: Int)

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

