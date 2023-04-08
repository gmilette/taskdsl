package campaign.infix

import campaign.TimeAt
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import campaign.TaskType.Call
import campaign.TaskType.Email
import campaign.infix.CampaignBuilder.TimeBuilder.after

class InfixAfterTest {
    @Test
    fun `test hours after`() {
        val builder = CampaignBuilder()
        val campaign = builder {
            email("t1") {
                at(5)
                on("10-23-23")
            }
            call("t2") {
                5 hours after
            }
        }

        campaign.tasks[0].type shouldBe Email
        campaign.tasks[1].type shouldBe Call
        (campaign.tasks[1].time as TimeAt.HoursAfterTask).task shouldBe campaign.tasks[0]
    }

}