package campaign.structure

import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test
import campaign.Shipping.Overnight

class StructureTest {
    @Test
    fun `use structure`() {
        val builder = CampaignBuilder()
        val campaign = builder {
            email("t1") {
                within(10, 12)
                on("10-20-23")
            }
            call("t2") {
                at(10)
                on("10-25-23")
            }
            mail("t3") {
                by(Overnight)
                on("10-27-23")
            }
        }

        campaign.tasks shouldHaveSize 3
    }
}