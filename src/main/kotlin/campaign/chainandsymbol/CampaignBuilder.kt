package campaign.chainandsymbol

import campaign.*


class CampaignBuilder {
    private val taskOrder = mutableListOf<Task>()

    fun emailWithin(id: String,
                    hourStart: Int = -1,
                    hourEnd: Int = -1,
                    date: String
    ): CampaignBuilder {
        val task = Task(id, TaskType.Email, TimeAt.Range(hourStart..hourEnd, date))
        taskOrder.add(task)
        return this
    }

    fun call(id: String,
             hour: Int = -1,
             date: String
    ): CampaignBuilder {
        val task = Task(id, TaskType.Email, TimeAt.Specific(hour, date))
        taskOrder.add(task)
        return this
    }

    fun callAfter(id: String,
                  after: String,
                  hours: Int): CampaignBuilder {
        val task = Task(id, TaskType.Call, TimeAt.HoursAfter(after, hours))
        taskOrder.add(task)
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



































