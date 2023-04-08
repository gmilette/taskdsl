package campaign.infix

import campaign.TimeAt
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import campaign.TaskType.Call
import campaign.infix.CampaignBuilder.call

class InfixTest {
    @Test
    fun `test isA`() {
        val builder = CampaignBuilder()
        val campaign = builder {
            "t1" isA call at 5 on "10-22-23"
        }

        campaign.tasks[0].type shouldBe Call
        campaign.tasks[0].time.shouldBeInstanceOf<TimeAt.Specific>()
    }

}