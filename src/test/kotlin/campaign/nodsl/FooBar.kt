package campaign.nodsl

import org.junit.jupiter.api.Test

class FooBar {
    @Test
    fun runFoo() {
        val foo = FooBar()
        foo.bara(1)

        foo bar 1
    }

    class FooBar {
        fun bara(a: Int) {

        }

        infix fun bar(a: Int) {

        }
    }
}