package campaign.nested.bad

import org.junit.jupiter.api.Test
class MenuNestedBuilderTest {

    @Test
    fun `using using any`() {
        val menu = MenuBuilder()

        menu.name("icecream").chocolate()

        menu.name("icecream").chocolate().cheese()
        // prevent
//        menu.name("icecream").asIceCream().cheese()
    }

}