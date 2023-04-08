package campaign.nested

import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test
import campaign.Shipping.*
import campaign.TimeAt
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class NestedTest {
    @Test
    fun `test nested builder`() {
        val campaign = CampaignBuilder()
            .email("t1").within(10, 12).on("10-23-23")
            .call("t2").at(10).on("10-25-23")
            .mail("t3").by(Overnight).on("10-27-23")
            .build()

        campaign.tasks shouldHaveSize 3
        campaign.tasks[0].time.shouldBeInstanceOf<TimeAt.Range>()
        campaign.tasks[1].time.shouldBeInstanceOf<TimeAt.Specific>()
        campaign.tasks[2].shipping shouldBe Overnight
    }
}