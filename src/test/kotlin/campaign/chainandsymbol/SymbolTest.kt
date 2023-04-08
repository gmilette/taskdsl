package campaign.chainandsymbol

import campaign.Shipping.Overnight
import campaign.TimeAt
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test

class SymbolTest {
    @Test
    fun `test function chain`() {
        val campaign = CampaignBuilder()
            .emailWithin("t1", 8, 10, "10-23-23")
            .callAfter("t2", "t1", 20)
            .mail("t3", shipping = Overnight, "10-27-23")
            .build()

        campaign.tasks shouldHaveSize 3
        campaign.tasks[0].time.shouldBeInstanceOf<TimeAt.Range>()
        campaign.tasks[1].time.shouldBeInstanceOf<TimeAt.HoursAfterTask>()
        campaign.tasks[2].shipping shouldBe Overnight
    }
}