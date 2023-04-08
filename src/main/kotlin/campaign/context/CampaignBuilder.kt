package campaign.context

import campaign.*


class CampaignBuilder {
    private val taskOrder = mutableListOf<Task>()

    private var previousTask: Task? = null

    private fun add(task: Task) {
        taskOrder.add(task)
        previousTask = task
    }

    fun emailWithin(id: String,
                    hourStart: Int = -1,
                    hourEnd: Int = -1,
                    date: String
    ): CampaignBuilder {
        val task = Task(id, TaskType.Email, TimeAt.Range(hourStart..hourEnd, date))
        add(task)
        return this
    }

    fun call(id: String,
             hour: Int = -1,
             date: String
    ): CampaignBuilder {
        val task = Task(id, TaskType.Email, TimeAt.Specific(hour, date))
        add(task)
        return this
    }

    fun callAfter(id: String,
              after: String = previousTask?.id ?: "",
              hours: Int): CampaignBuilder {
        val task = Task(id, TaskType.Call, TimeAt.HoursAfter(after, hours))
        add(task)
        return this
    }

    fun mail(id: String,
             shipping: Shipping,
             date: String,
    ): CampaignBuilder {
        val task = Task(id, TaskType.Mail, TimeAt.Date(date), shipping = shipping)
        taskOrder.add(task)
        return this
    }

    fun build(): Campaign {
        val symbolTable = taskOrder.associateBy { it.id }
        val tasks = taskOrder.map { task ->
            when (task.time) {
                is TimeAt.HoursAfter -> {
                    val referredTask = symbolTable[task.time.task] ?: throw IllegalArgumentException("unknown")
                    task.copy(time = TimeAt.HoursAfterTask(referredTask, task.time.hours))
                }
                else -> task
            }

        }

        return Campaign(tasks)
    }
}




































