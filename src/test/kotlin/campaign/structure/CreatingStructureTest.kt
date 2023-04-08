package campaign.structure

import org.junit.jupiter.api.Test

class CreatingStructureTest {
    @Test
    fun `creating a structure`() {
        foo {

        }

        foo {
            bar {

            }
            baz {

            }
        }
    }

    fun foo(block: () -> Unit) {

    }

    fun bar(block: () -> Unit) {

    }

    fun baz(block: () -> Unit) {

    }
}