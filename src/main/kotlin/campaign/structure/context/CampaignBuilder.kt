package campaign.structure.context

import campaign.*

class CampaignBuilder {
    private val taskOrder = mutableListOf<TaskBuilder>()

    private interface TaskBuilder {
        val id: String
        fun build(): Task
    }

    fun create(block: CampaignBuilder.() -> Unit): Campaign {
        block()
        return this.build()
    }

    fun email(id: String, block: context(TimeBuilder, DateBuilder) () -> Unit): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Email)
        block(builder, builder.dateBuilder)
        taskOrder.add(builder)
        return builder
    }

    fun call(id: String, block: context(TimeBuilder, DateBuilder) () -> Unit): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Call)
        block(builder, builder.dateBuilder)
        taskOrder.add(builder)
        return builder
    }

    fun mail(id: String, block: context(MailBuilder, DateBuilder) () -> Unit): MailBuilder {
        val builder = MailBuilder(id)
        block(builder, builder.dateBuilder)
        taskOrder.add(builder)
        return builder
    }

    inner class TimeBuilder(override val id: String, private val type: TaskType): TaskBuilder {
        val dateBuilder = DateBuilder()
        private var hourRange: IntRange? = null
        private var hour: Int = 0

        infix fun at(hour: Int) {
            this.hour = hour
        }

        fun within(startHour: Int, endHour: Int) {
            hourRange = startHour..endHour
        }

        override fun build(): Task =
            Task(id, type, buildTime())

        private fun buildTime(): TimeAt {
            return when {
                hourRange != null -> TimeAt.Range(
                    hourRange ?: 0..0,
                    dateBuilder.date
                )
                else -> TimeAt.Specific(
                    hour,
                    dateBuilder.date
                )
            }
        }
    }

    inner class MailBuilder(override val id: String): TaskBuilder {
        val dateBuilder = DateBuilder()
        private var shipping: Shipping = Shipping.None

        fun by(shipping: Shipping) {
            this.shipping = shipping
        }

        override fun build() : Task {
            return Task(id, TaskType.Mail, TimeAt.Date(dateBuilder.date), shipping)
        }
    }

    open inner class DateBuilder {
        var date: String = ""
        fun on(date: String) {
            this.date = date
        }
    }

    fun build(): Campaign =
        Campaign(taskOrder.map { it.build() })
}






















