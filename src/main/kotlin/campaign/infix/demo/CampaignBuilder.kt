package campaign.infix.demo

import campaign.*
import java.util.Date

@DslMarker
annotation class CampaignDsl

@CampaignDsl
class CampaignBuilder {
    private val taskOrder = mutableListOf<TaskBuilder>()

    private interface TaskBuilder {
        val id: String
        fun build(): Task
    }

    operator fun invoke(block: CampaignBuilder.() -> Unit): Campaign {
        block()
        return this.build()
    }

    infix fun String.isA(call: call): TimeBuilder {
        val builder = TimeBuilder(this, TaskType.Call)
        taskOrder.add(builder)
        return builder
    }

    object call

    fun email(id: String, block: TimeBuilder.() -> Unit): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Email)
        builder.block()
        taskOrder.add(builder)
        return builder
    }

    fun call(id: String, block: TimeBuilder.() -> Unit): TimeBuilder {
        val builder = TimeBuilder(id, TaskType.Call)
        builder.block()
        taskOrder.add(builder)
        return builder
    }

    fun mail(id: String, block: MailBuilder.() -> Unit): MailBuilder {
        val builder = MailBuilder(id)
        builder.block()
        taskOrder.add(builder)
        return builder
    }

    @CampaignDsl
    inner class TimeBuilder(override val id: String, private val type: TaskType): TaskBuilder, DateBuilder() {
        private var hourRange: IntRange? = null
        private var hour: Int = 0

        infix fun at(hour: Int): TimeBuilder {
            this.hour = hour
            return this
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
    open inner class DateBuilder {
        var date: String = ""
        infix fun on(date: String) {
            this.date = date
        }
    }

    fun build(): Campaign =
        Campaign(taskOrder.map { it.build() })
}



































