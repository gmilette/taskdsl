package campaign.chain

import campaign.*
import campaign.TaskType.*
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test
import campaign.Shipping.Overnight
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class FunctionChainTest {

    @Test
    fun `create function chain lines`() {
        val campaign = CampaignBuilder()
            .add(Task("c1", Email, TimeAt.Specific(10, "10-25-23")))
            .add(Task("c2", Call, TimeAt.Specific(10, "10-27-23")))
            .add(Task("c3", Mail, TimeAt.Date("10-29-23")))
            .build()

        campaign.tasks shouldHaveSize 3
    }

    @Test
    fun `create function chain`() {
        val campaign = CampaignBuilder()
                .add(Task("c1",
                    Email,
                    TimeAt.Specific(10, "10-25-23")))
                .add(Task("c2",
                    Call,
                    TimeAt.Specific(10, "10-27-23")))
                .add(Task("c3",
                    Mail,
                    TimeAt.Specific(12, "10-29-23")))
                .build()

        campaign.tasks shouldHaveSize 3
    }

    @Test
    fun `using dsl words`() {
        val campaign = CampaignBuilder()
            .emailWithin("t1", 8, 10, "10-23-23")
            .call("t2", 20, "10-25-23")
            .mail("t3", shipping = Overnight, "10-27-23")
            .build()

        campaign.tasks shouldHaveSize 3
        campaign.tasks[0].time.shouldBeInstanceOf<TimeAt.Range>()
        campaign.tasks[1].time.shouldBeInstanceOf<TimeAt.Specific>()
        campaign.tasks[2].shipping shouldBe Overnight
    }
}