package campaign.structure

import campaign.*
import java.util.*
import kotlin.random.Random

// add this as a context for all functions?
// or maybe a context for a certain task?
//class TimeScope {
//    val now: Int
//}

//@CampaignDsl
//class CampaignBuilderStructure() {
//
//    private data class EditableTask(
//        var name: String = UUID.randomUUID().toString(),
//        var method: TaskType = TaskType.None,
//        var hour: Int = -1,
//        var shippingMethod: Shipping = Shipping.None,
//        var day: Int = -1,
//        var repeat: Repeat = Repeat.None
//    ) {
//        val task: Task
//            get() = Task(name, method, hour, null, null, shippingMethod, day)
//    }
//
//    // this is a decent usecase, for the context functions
//    // context provides cheap way to inject parameters
////    private val time: TimeScope = TimeScope()
//
//    fun buildScoped() {
//        // use this time for all calclations
////        with (time) {
////            time.now
////            taskOrder.forEach {  }
////        }
//    }
//
//    private var currentTask: EditableTask = EditableTask("test", method = TaskType.Call, day = 1)
//
//    private val taskOrder = mutableListOf<EditableTask>()
//    // tasks
//    private val taskSymbolTable = mutableMapOf<String, EditableTask>()
//
//    private fun add(task: EditableTask): CampaignBuilderStructure {
//        currentTask = task
//        taskOrder.add(task)
//        taskSymbolTable[task.name] = task
//        return this
//    }
//
//    operator fun invoke(block: CampaignBuilderStructure.() -> Unit) {
//        block()
//    }
//
//    fun build(): Schedule =
//        Schedule(taskOrder.map { it.task })
//
//    //private val builder: CampaignBuilderStructure
////    @ScheduleDsl
//    open inner class DayBuilder() {
//        fun on(day: Int) {
//            currentTask.day = day
//        }
//
//        // use the symbol table
//        fun after(daysAfter: Int) {
//        }
//    }
//
//    context(CampaignBuilderStructure, MailBuilder)
//    fun byContext(method: Shipping) {
//        this@CampaignBuilderStructure.currentTask.shippingMethod = method
//    }
//
//    // present: first do this as inheritance,
//    //  then switch to the context receivers like this
//    //  to pick which lambdas can use the onDay function
//
//    interface DaySetter
//
//    context(DaySetter)
//    fun onC(day: Int) {
//        currentTask.day = day
//    }
//
////    context(CampaignBuilderStructure, EmailBuilder)
////    fun onC(day: Int) {
////        this@CampaignBuilderStructure.currentTask.day = day
////    }
//
//    inner class MailBuilder: DaySetter {
//        fun build(block: MailBuilder.() -> Unit) {
//            block()
//        }
//
//        fun by(method: Shipping): DayBuilder {
//            currentTask.shippingMethod = method
//            return DayBuilder()
//        }
//    }
//
//    // try to do this with context receivers
//    fun emailX(name: String, block: context(CampaignBuilderStructure.EmailBuilder) () -> Unit) {
//        currentTask = EditableTask(name, method = TaskType.Email)
//        add(currentTask)
//        val b = EmailBuilder()
//        block(b)
//    }
//
//    inner class EmailBuilder: DayBuilder() {
////        operator fun invoke(block: EmailBuilder.() -> Unit) {
////            block()
////        }
//
//        fun during(hourStart: Int, hourEnd: Int): DayBuilder {
//            currentTask.hour = generateRange(hourStart, hourEnd)
//            return DayBuilder()
//        }
//
//        fun at(hour: Int): DayBuilder {
//            currentTask.hour = hour
//            // switch to day
//            return DayBuilder()
//        }
//
//        fun after(task: String): DayBuilder {
//            return DayBuilder()
//        }
//
//        private fun generateRange(min: Int, max: Int): Int {
//            return Random.nextInt(min, max)
//        }
//    }
//
//    fun call(name: String, block: EmailBuilder.() -> Unit) {
//        currentTask = EditableTask(name, method = TaskType.Email)
//        add(currentTask)
//        EmailBuilder().block()
//    }
//    // demo here explain how we can customize these constructors
//    fun email(name: String, block: EmailBuilder.() -> Unit) {
//        currentTask = EditableTask(name, method = TaskType.Email)
//        add(currentTask)
//        EmailBuilder().block()
//    }
//
//    fun mail(name: String, block: MailBuilder.() -> Unit) {
//        currentTask = EditableTask(name, method = TaskType.Mail)
//        add(currentTask)
//        MailBuilder().block()
//    }
//
//    // use case for context receiver
//    // I'm already in the email context
//    // and I want to call a function on string
//    // like
//    //  "t1".after(2)
//    // if I am in email context do hours
//    // if I am day context get the day
//    // eh weak
//
//    fun inB() {
//
//    }
//
//    context (String)
//    fun after(time: Int) {
//        inB()
//    }
//}


