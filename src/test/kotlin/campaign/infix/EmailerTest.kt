package campaign.infix

import campaign.structure.CampaignBuilder
import org.junit.jupiter.api.Test

class EmailerTest {
    @Test
    fun `infix tester`() {
        val builder = CampaignBuilder()

        val emailer = Emailer()
        emailer.send("test@pluralsite.com")

        emailer send "test@pluralsite.com"
    }

    @Test
    fun `two param and extensions`() {
        val builder = CampaignBuilder()

        val emailer = Emailer()
        emailer.send("from@pluralsite.com", "test@pluralsite.com")

        with (emailer) {
            "from@pluralsite.com" sendTo "test@pluralsite.com"
        }
    }

}