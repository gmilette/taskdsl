package campaign.structure

import campaign.*
import campaign.TaskType.*
import java.sql.Date
import java.util.*

object ObjectWithInvoke {
    operator fun invoke() {
        // do some invoke
    }
}

@CampaignDsl
class CampaignBuilder {
    private val noTask = Task("none")

    private var previousTask: Task? = null

    private var currentTask: String = ""

    private val taskOrder = mutableListOf<Task>()
    // tasks
    private val taskSymbolTable = mutableMapOf<String, Task>()

    private var taskBuilder: TaskBuilder? = null
    private interface TaskBuilder {
        fun build(): Task
    }

    private fun add(task: Task): CampaignBuilder {
        previousTask = task
        taskOrder.add(task)
        taskSymbolTable[task.id] = task
        return this
    }

    operator fun invoke(block: CampaignBuilder.() -> Unit): Campaign {
        block()
        return this.build()
    }

    fun build(): Campaign {
        val tasks = taskOrder.map { task ->
            when (task.time) {
                is TimeAt.HoursAfter -> {
                    val referredTask = taskSymbolTable[task.time.task] ?: throw IllegalArgumentException("unknown task ${task.id}")
                    task.copy(time = task.time.withTask(referredTask))
                }
                else -> task
            }
        }
        return Campaign(tasks)
    }

    operator fun String.unaryPlus() {
        // add
    }

    operator fun TaskType.unaryPlus() {
        // add
    }

    open inner class DateBuilder {
        var date: String = ""
        fun on(date: String): CampaignBuilder {
            this.date = date
            return this@CampaignBuilder
        }
    }

    inner class MailBuilder: TaskBuilder {
        private var shipping: Shipping = Shipping.None
        var daysAfterBuilder = DaysAfterBuilder()
        fun by(shipping: Shipping): DaysAfterBuilder {
            this.shipping = shipping
            return daysAfterBuilder
        }

        val nextDay: Unit
            get() {
                // set the nextDay
            }

        override fun build() : Task {
            return Task(currentTask, Mail, daysAfterBuilder.build())
        }
    }

//    inner class TimeBuilder(private val currentTask: String,
//                            private val type: TaskType): DateBuilder

    @CampaignDsl
    inner class TimeBuilder(private val currentTask: String,
                            private val type: TaskType,
                            val dateBuilder: DateBuilder = DateBuilder()): DateBuilder() {

        // user will use one of these to create a time
        var afterBuilder: AfterBuilder? = null
        var hourRange: IntRange? = null
        var hour: Int = 0

        fun at(hour: Int): DateBuilder {
            this.hour = hour
            return dateBuilder
        }

        fun within(startHour: Int, endHour: Int): DateBuilder {
            hourRange = startHour..endHour
            return dateBuilder
        }

        val morning: Unit
            get() {
                // set the nextDay
            }

        val Int.days
            get() = this * 24

        val Int.hours
            get() = this

        fun Int.after(task: String) {

        }

        // using context variable to pass info
        context (String)
        fun Int.afterC() {
            val string = this@afterC
            val num = this
        }

        context (String)
        val Int.afterz: Int
            get() {
                val num = this
                val string = this@afterz
                return 0
            }

        operator fun plus(increment: Int): String {
            return ""
        }

        operator fun String.plus(other: Any?): String {
            return ""
        }

        val nextDay: Unit
            get() {
                // set the nextDay
            }

        //                "t1" + 10 // 10 hours after t1
//                "t1".plusHours(10)
//                    after("t1") {
//                        hours(10)
//                        days(2)
//                    }

//        fun after(which: String = previousTask?.id ?: ""): AfterBuilder {
//            val builder = AfterBuilder(which)
//            afterBuilder = builder
//            return builder
//        }

        fun build(): Task =
            Task(currentTask, type, buildTime())

        private fun buildTime(): TimeAt {
            return when {
                afterBuilder != null -> afterBuilder?.build() ?: throw IllegalArgumentException("incomplete time for $currentTask")
                // time task
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

    inner class AfterBuilder(private val relativeTask: String) {
        private var timeAt: TimeAt = TimeAt.Unspecified

        fun hours(hours: Int): CampaignBuilder {
            timeAt = TimeAt.HoursAfter(relativeTask, hours)
            return this@CampaignBuilder
        }

        fun days(days: Int): CampaignBuilder {
            timeAt = DaysAfter(relativeTask, days)
            return this@CampaignBuilder
        }

        fun build(): TimeAt = timeAt
    }

    inner class DaysAfterBuilder: DateBuilder() {
        private var daysAfter: Int = 0
        private var relativeTask: String = ""

        fun after(id: String = previousTask?.id ?: ""): DaysAfterBuilder {
            relativeTask = id
            return this
        }

        fun days(days: Int): CampaignBuilder {
            daysAfter = days
            return this@CampaignBuilder
        }

        fun build(): TimeAt {
            return when {
                this.date.isNotEmpty() -> TimeAt.Date(date)
                else -> DaysAfter(relativeTask, daysAfter)
            }
        }
    }

    // demo here explain how we can customize these constructors

   @CampaignDsl
    fun email(id: String, block: TimeBuilder.() -> Unit) {
        currentTask = id
        val timeBuilder = TimeBuilder(id, Email)
        val after = timeBuilder.afterBuilder
        block(timeBuilder)
        add(timeBuilder.build())
    }

    // or this using inheritance
//    fun email(id: String, block: TimeBuilder.() -> Unit) {
//        TimeBuilder(Email).block()
//    }

//    fun call(id: String, block: context(TimeBuilder, DateBuilder) () -> Unit) {
//    @CampaignDsl
    fun call(id: String, block: context(TimeBuilder) () -> Unit) {
        currentTask = id
        val timeBuilder = TimeBuilder(id, Call)
//        block(timeBuilder, timeBuilder.dateBuilder)
        add(timeBuilder.build())
    }

//    @CampaignDsl
    fun mail(id: String, block: context(MailBuilder, DaysAfterBuilder) () -> Unit) {
        currentTask = id
        val timeBuilder = MailBuilder()
        block(timeBuilder, timeBuilder.daysAfterBuilder)
        add(timeBuilder.build())
    }
}

typealias Time = Int
