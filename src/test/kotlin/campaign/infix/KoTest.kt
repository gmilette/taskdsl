package campaign.infix

import io.kotest.matchers.ints.shouldBeEven
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KoTest {
    @Test
    fun `ko test`() {
        val sum = 2
        assertEquals(2, sum)

        sum.shouldNotBe(4)
        sum.shouldBeGreaterThan(1)
        sum.shouldBeLessThan(4)
        sum.shouldBe(2)


        sum shouldNotBe 4
        sum shouldBeGreaterThan 1
        sum shouldBeLessThan 4
        sum shouldBe 2

        sum shouldBe 2
    }

    @Test
    fun `ko test ideas`() {
        val sum = 2
        sum.should(be).equal(2)
        sum should be equal 2
        sum should be not 4
        sum should be greaterThan 1
        sum should be lessThan 4
    }
}