package campaign.infix

import campaign.*

@DslMarker
annotation class CampaignDsl

@CampaignDsl
class CampaignBuilder {
    private val taskOrder = mutableListOf<TaskBuilder>()

    interface TaskBuilder {
        val id: String
        fun build(): Task
    }

    private var previousTask: TaskBuilder? = null

    operator fun invoke(block: CampaignBuilder.() -> Unit): Campaign {
        block()
        return this.build()
    }

    private fun add(task: TaskBuilder) {
        taskOrder.add(task)
        previousTask = task
    }

    infix fun String.isA(call: call): TimeBuilder {
        val builder = TimeBuilder(this, TaskType.Call, previousTask)
        add(builder)
        return builder
    }

    object call

    fun email(id: String, block: TimeBuilder.() -> Unit): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Email, previousTask)
        builder.block()
        add(builder)
        return builder
    }

    fun call(id: String, block: TimeBuilder.() -> Unit): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Call, previousTask)
        builder.block()
        add(builder)
        return builder
    }

    fun mail(id: String, block: MailBuilder.() -> Unit): MailBuilder {
        val builder = MailBuilder(id)
        builder.block()
        add(builder)
        return builder
    }

    @CampaignDsl
    class TimeBuilder(override val id: String, private val type: TaskType, private val previousTask: TaskBuilder?): TaskBuilder, DateBuilder() {
        private var hour: Int = 0
        private var timeAfter: TimeAt.HoursAfter? = null

        object after

        infix fun Int.hours(after: after) {
            val previous = previousTask ?: throw IllegalArgumentException("previous not set")
            timeAfter = TimeAt.HoursAfter(previous.id, this)
        }

        infix fun at(hour: Int): TimeBuilder {
            this.hour = hour
            return this
        }

        override fun build(): Task =
            Task(id, type, buildTime())

        private fun buildTime(): TimeAt {
            return when {
                timeAfter != null -> timeAfter ?: throw IllegalArgumentException("invalid time")

                else -> TimeAt.Specific(
                    hour,
                    date
                )
            }
        }
    }

    @CampaignDsl
    inner class MailBuilder(override val id: String): TaskBuilder, DateBuilder() {
        private var shipping: Shipping = Shipping.None

        fun by(shipping: Shipping) {
            this.shipping = shipping
        }

        override fun build() : Task {
            return Task(id, TaskType.Mail, TimeAt.Date(date), shipping)
        }
    }

    @CampaignDsl
    open class DateBuilder {
        var date: String = ""
        infix fun on(date: String) {
            this.date = date
        }
    }

    fun build(): Campaign {
        val tasksUnresolved = taskOrder.map { it.build() }

        val tasks = resolveTimes(tasksUnresolved)

        return Campaign(tasks)
    }

    private fun resolveTimes(tasks: List<Task>): List<Task> {
        val symbols = tasks.associateBy { it.id }
        return tasks.map { task ->
            when (task.time) {
                is TimeAt.HoursAfter -> {
                    val referredTask = symbols[task.time.task] ?: throw IllegalArgumentException("unknown task ${task.id}")
                    task.copy(time = task.time.withTask(referredTask))
                }

                else -> task
            }
        }
    }
}



































