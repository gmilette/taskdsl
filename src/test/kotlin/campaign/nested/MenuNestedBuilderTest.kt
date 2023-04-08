package campaign.nested

import org.junit.jupiter.api.Test

class MenuNestedBuilderTest {

    @Test
    fun `using using state`() {
        val menu = MenuBuilder()
        menu.name("cheese").asPizza().cheese()
        menu.name("icecream").asIceCream().chocolate()

        // prevent
//        menu.name("icecream").asIceCream().cheese()
    }

    @Test
    fun `test ide`() {
        val menu = MenuBuilder()
//        menu.name("cheese").asPizza()
    }
}