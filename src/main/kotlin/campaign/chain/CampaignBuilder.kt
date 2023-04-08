package campaign.chain

import campaign.*

class CampaignBuilder {
    private val taskOrder = mutableListOf<Task>()
    fun emailWithin(id: String, start: Int, end: Int, date: String): CampaignBuilder {
        val task = Task(id, TaskType.Email, TimeAt.Range(start..end, date))
        taskOrder.add(task)
        return this
    }

    fun call(id: String, hour: Int, date: String): CampaignBuilder {
        val task = Task(id, TaskType.Call, TimeAt.Specific(hour, date))
        taskOrder.add(task)
        return this
    }

    fun mail(id: String, shipping: Shipping, date: String): CampaignBuilder {
        val task = Task(id, TaskType.Call, TimeAt.Date(date), shipping)
        taskOrder.add(task)
        return this
    }

    fun add(task: Task): CampaignBuilder {
        taskOrder.add(task)
        return this
    }

    fun build(): Campaign = Campaign(taskOrder)
}
