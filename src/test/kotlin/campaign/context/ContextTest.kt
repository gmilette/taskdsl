package campaign.context

import campaign.Shipping.Overnight
import campaign.TimeAt
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test

class ContextTest {
    @Test
    fun `test after`() {
        val campaign = CampaignBuilder()
            .emailWithin("t1", 10, 12, "10-23-23")
            .call("t2", 10, "10-25-23")
            .mail("t3", shipping = Overnight, "10-27-23")
            .build()

        campaign.tasks shouldHaveSize 3
        campaign.tasks[0].time.shouldBeInstanceOf<TimeAt.Range>()
        campaign.tasks[1].time.shouldBeInstanceOf<TimeAt.Specific>()
        campaign.tasks[2].shipping shouldBe Overnight
    }
}