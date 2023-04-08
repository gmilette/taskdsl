package campaign.structure.props

import campaign.*

@DslMarker
annotation class CampaignDsl

@CampaignDsl
class CampaignBuilder {
    private val taskOrder = mutableListOf<TaskBuilder>()

    private interface TaskBuilder {
        val id: String
        fun build(): Task
    }

    fun create(block: CampaignBuilder.() -> Unit): Campaign {
        block(this)
        return this.build()
    }

    operator fun invoke(block: CampaignBuilder.() -> Unit): Campaign {
        block(this)
        return this.build()
    }


    private fun add(task: TaskBuilder) {
        taskOrder.add(task)
    }

    fun email(id: String, block: TimeBuilder.() -> Unit) {
        val builder = TimeBuilder(id, TaskType.Email)
        builder.block()
        add(builder)
    }

    fun call(id: String, block: TimeBuilder.() -> Unit) {
        val builder = TimeBuilder(id, TaskType.Call)
        builder.block()
        add(builder)
    }

    fun mail(id: String, block: MailBuilder.() -> Unit) {
        val builder = MailBuilder(id)
        builder.block()
        add(builder)
    }

    class TimeBuilder(override var id: String, private val type: TaskType) : TaskBuilder, DateBuilder() {
        private var hourRange: IntRange? = null
        private var hour: Int = 0
        private var timeAfter: TimeAt.HoursAfter? = null

        private val recipients: MutableList<String> = mutableListOf()

        operator fun String.unaryPlus() {
            recipients += this
        }

        val morning: Unit
            get() {
                hourRange = 6..12
            }

        fun at(hour: Int) {
            this.hour = hour
        }

        fun String.after(hours: Int) {
            timeAfter = TimeAt.HoursAfter(this, hours)
        }

        val Int.hours: Int
            get() {
                return this
            }

        val Int.days: Int
            get() = this*24

        operator fun String.plusAssign(hours: Int) {
            timeAfter = TimeAt.HoursAfter(this, hours)
        }

        fun within(startHour: Int, endHour: Int) {
            hourRange = startHour..endHour
        }

        override fun build(): Task =
            Task(id, type, buildTime(), recipients = recipients)

        private fun buildTime(): TimeAt {
            return when {
                timeAfter != null -> timeAfter ?: throw IllegalArgumentException("invalid time")
                hourRange != null -> TimeAt.Range(
                    hourRange ?: 0..0,
                    date
                )

                else -> TimeAt.Specific(
                    hour,
                    date
                )
            }
        }
    }

    @CampaignDsl
    class MailBuilder(override val id: String) : TaskBuilder, DateBuilder() {
        private var shipping: Shipping = Shipping.None

        fun by(shipping: Shipping) {
            this.shipping = shipping
        }

        override fun build(): Task {
            return Task(id, TaskType.Mail, TimeAt.Date(date), shipping)
        }
    }

    @CampaignDsl
    open class DateBuilder {
        var date: String = ""
        fun on(date: String) {
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



































