package campaign.nested

import campaign.*

class CampaignBuilder {
    private val taskOrder = mutableListOf<TaskBuilder>()

    private interface TaskBuilder {
        val id: String
        fun build(): Task
    }

    fun email(id: String): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Email)
        taskOrder.add(builder)
        return builder
    }

    inner class TimeBuilder(override val id: String, private val type: TaskType): TaskBuilder {
        private val dateBuilder = DateBuilder()

        private var hourRange: IntRange? = null
        private var hour: Int = 0

        fun within(startHour: Int, endHour: Int): DateBuilder {
            hourRange = startHour..endHour
            return dateBuilder
        }

        fun at(hour: Int): DateBuilder {
            this.hour = hour
            return dateBuilder
        }

        override fun build(): Task {
            return Task(id, type, buildTime())
        }

        private fun buildTime(): TimeAt {
            return when {
                hourRange != null -> TimeAt.Range(hourRange ?: 0..0, dateBuilder.date)
                else -> TimeAt.Specific(hour, dateBuilder.date)
            }
        }
    }

    fun call(id: String): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Call)
        taskOrder.add(builder)
        return builder
    }

    fun mail(id: String): MailBuilder {
        val builder = MailBuilder(id)
        taskOrder.add(builder)
        return builder
    }

    inner class DateBuilder() {
        var date: String = ""

        fun on(date: String): CampaignBuilder {
            this.date = date
            return this@CampaignBuilder
        }
    }

    inner class MailBuilder(override val id: String): TaskBuilder {
        private var shipping: Shipping = Shipping.None
        private var dateBuilder: DateBuilder = DateBuilder()

        fun by(shipping: Shipping): DateBuilder {
            this.shipping = shipping
            return dateBuilder
        }

        override fun build(): Task {
            return Task(id, TaskType.Mail, TimeAt.Date(dateBuilder.date), shipping)
        }
    }

    fun build(): Campaign =
        Campaign(taskOrder.map { it.build() })
}




































