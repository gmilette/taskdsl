package campaign.context

import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test
import campaign.Shipping.Overnight
import campaign.TaskType
import campaign.TimeAt
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ContextVariableTest {
    @Test
    fun `using state`() {
        val campaign = CampaignBuilder()
            .emailWithin("c1", hourStart = 10, hourEnd = 16, date = "10-20-23")
            .callAfter("c2", hours = 24)
            .build()

        campaign.tasks shouldHaveSize 2
        val email = campaign.tasks[0]
        val call = campaign.tasks[1]
        email.type shouldBe TaskType.Email
        call.apply {
            this.type shouldBe TaskType.Call
            time.shouldBeInstanceOf<TimeAt.HoursAfterTask>()
            (time as TimeAt.HoursAfterTask).task shouldBe email
        }
    }
}