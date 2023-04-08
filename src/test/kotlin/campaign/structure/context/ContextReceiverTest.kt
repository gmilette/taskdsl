package campaign.structure.context

import org.junit.jupiter.api.Test

class ContextReceiverTest {
    @Test
    fun `demonstrate context receiver`() {
        val timeBuilder = TimeBuilder()
        val dateBuilder = DateBuilder()
        with(dateBuilder) {
            with(timeBuilder) {
                at(9)
                on("10-23")
            }
        }
    }

    class TimeBuilder {
        fun at(hour: Int) {

        }
    }

    class DateBuilder {
        fun on(date: String) {

        }
    }
}