package campaign.nested.bad

/**
 * implemented without nested builders. You can add cheese() and chocolate() together creating a culinary disaster!
 */
class MenuBuilder {
    private var id: String = ""
    private val toppings = mutableListOf<String>()
    fun name(id: String): MenuBuilder {
        return this
    }
    fun chocolate(): MenuBuilder {
        toppings.add("chocolate")
        return this
    }
    fun cheese(): MenuBuilder {
        toppings.add("cheese")
        return this
    }
    fun pepperoni(): MenuBuilder {
        toppings.add("pepperoni")
        return this
    }
    fun sausage(): MenuBuilder {
        toppings.add("sausage")
        return this
    }
    fun mushrooms(): MenuBuilder {
        toppings.add("mushrooms")
        return this
    }
    fun bacon(): MenuBuilder {
        toppings.add("bacon")
        return this
    }
    fun onions(): MenuBuilder {
        toppings.add("onions")
        return this
    }
    fun peppers(): MenuBuilder {
        toppings.add("peppers")
        return this
    }
    fun kale(): MenuBuilder {
        toppings.add("kale")
        return this
    }
}