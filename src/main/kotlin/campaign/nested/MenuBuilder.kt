package campaign.nested

class MenuBuilder {
    private var id: String = ""
    private val toppings = mutableListOf<String>()

    fun name(id: String): MenuBuilder {
        return this
    }

    fun asPizza(): PizzaBuilder {
        return PizzaBuilder()
    }

    fun asIceCream(): IceCreamBuilder {
        return IceCreamBuilder()
    }

    inner class IceCreamBuilder {
        fun chocolate() {
            toppings.add("chocolate")
        }
    }
    inner class PizzaBuilder {
        fun cheese() {
            toppings.add("cheese")
        }
        fun pepperoni() {
            toppings.add("pepperoni")
        }
        fun sausage() {
            toppings.add("sausage")
        }
        fun mushrooms() {
            toppings.add("mushrooms")
        }
        fun bacon() {
            toppings.add("bacon")
        }
        fun onions() {
            toppings.add("onions")
        }
        fun peppers() {
            toppings.add("peppers")
        }
        fun kale() {
            toppings.add("kale")
        }
    }
}