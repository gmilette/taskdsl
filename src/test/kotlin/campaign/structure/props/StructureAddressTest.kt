package campaign.structure.props

import campaign.TimeAt
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class StructureAddressTest {
    @Test
    fun `test add addresses`() {
        val builder = CampaignBuilder()
        val campaign = builder {
            email("t1") {
                morning
                on("10-20-23")
                +"tiger@gmail.com"
                +"forest@gmail.com"
                +"ocean@gmail.com"
            }
            call ("t2") {
                "t1" += 5
            }
        }

        campaign.tasks[0].recipients shouldHaveSize 3
        (campaign.tasks[1].time as TimeAt.HoursAfterTask).hours shouldBe 5
    }
}
