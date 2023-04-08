package campaign.structure.props

import campaign.Shipping.Overnight
import campaign.TimeAt
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test
import campaign.structure.props.*

class StructurePropertiesTest {

    @Test
    fun `test props`() {
        val builder = CampaignBuilder()
        val campaign = builder {
            email("t1") {
                morning
                on("10-20-23")
            }
            call("t2") {
                "t1".after(5)
            }
            call("t3") {
                "t1" += 10
            }
            mail("t4") {
                by(Overnight)
                on("10-24-23")
            }
        }

        campaign.tasks shouldHaveSize 4
        (campaign.tasks[0].time as TimeAt.Range).apply {
            hour.first shouldBe 6
            hour.last shouldBe 12
            date shouldBe "10-20-23"
        }
        (campaign.tasks[1].time as TimeAt.HoursAfterTask).apply {
            task shouldBe campaign.tasks[0]
            hours shouldBe 5
        }
        (campaign.tasks[2].time as TimeAt.HoursAfterTask).apply {
            task shouldBe campaign.tasks[0]
            hours shouldBe 10
        }
        campaign.tasks[3].shipping shouldBe Overnight
    }
}