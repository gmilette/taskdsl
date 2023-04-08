package campaign.nodsl

import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test
import campaign.nodsl.TaskType.*

class NotADslTest {

    fun `create tasks`() {
        val task1 = Task("t1", Email, 10,date = "10-12-23")
        val task2 = Task("t2", Call, 10,date = "10-14-23")
        val task3 = Task("t3", Mail, date = "10-16-23")
        val campaign = Campaign(
            listOf(task1, task2, task3)
        )

        campaign.tasks shouldHaveSize 3
    }

    @Test
    fun create() {
        val task1 = Task(
            "t1",
            Email,
            hour = 10,
            date = "10-12-23"
        )
        val task2 = Task(
            "t2",
            Call,
            hour = 10,
            date = "10-14-23"
        )
        val task3 = Task(
            "t3",
            Mail,
            date = "10-16-23"
        )

        val campaign = Campaign(
            listOf(task1, task2, task3)
        )

        campaign.tasks shouldHaveSize 3
    }

    @Test
    fun `create time ranges and dependencies`() {
        val task1 = Task(
            "t1",
            Email,
            hourRange = 10..12,
            date = "10-20-23"
        )
        val task2 = Task(
            "t2",
            Call,
            after = TimeAfter(
                task = task1,
                hours = 24
            )
        )
        val task3 = Task(
            "t3",
            Mail,
            date = "10-23-23"
        )

        val campaign = Campaign(
            listOf(task1, task2, task3)
        )

        campaign.tasks shouldHaveSize 3
    }

    @Test
    fun `create with dependencies symbols`() {
        Task(
            "t1",
            Email,
            hourRange = 10..12,
            date = "2023-10-12"
        )
        Task(
            "t2",
            Call,
            after = TimeAfter("t1", hours = 24)
        )
    }

    @Test
    fun `The full campaign`() {
        // welcome
        val welcome1 = Task(
            "w1",
            type = Email,
            hourRange = 10..12,
            date = "10-12-23"
        )
        val welcome2 = Task(
            "w2",
            type = Email,
            hourRange = 12..24,
            date = "10-15-23"
        )
        // ...

        val welcome3 = Task(
            "w3",
            Call,
            after = TimeAfter(task = welcome2, hours = 2),
            date = "10-17-23"
        ) // add extra days
        val welcome4 = Task(
            "w4",
            Email,
            date = "10-18-23"
        )
        // ...

        // teach them something
        val learn1 = Task(
            "l1",
            TaskType.Email,
            hourRange = 10..12,
            date = "10-20-23"
        )
        val learn2 = Task(
            "l2",
            TaskType.Email,
            hour = 10,
            date = "10-21-23"
        )
        val learn3 = Task(
            "l3",
            TaskType.Email,
            hour = 17,
            date = "10-22-23"
        )

        // thank you mail!
        // use different range
        val thanksEmail = Task(
            "t1",
            TaskType.Email,
            hourRange = 10..12,
            date = "10-25-23"
        )
        val thanksShipping = Task(
            "t2",
            TaskType.Mail,
            shipping = Shipping.Overnight,
            date = "10-26-23"
        )

        val campaign = Campaign(
            listOf(
                welcome1,
                welcome2,
                welcome3,
                welcome4,
                learn1,
                learn2,
                learn3,
                thanksEmail,
                thanksShipping
            )
        )
        campaign.tasks shouldHaveSize 9
    }
}

