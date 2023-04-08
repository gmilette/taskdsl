package campaign.structure

class DslMarkerTest {
    fun demo() {
        MainBuilder().build {
            runFoo()
//            runBar()
            this.demo()
        }
    }
}

@DslMarker
annotation class FooDsl

//@FooDsl
class MainBuilder() {
    fun begin(block: MainBuilder.() -> Unit) {

    }
    fun build(block: context(FooBuilder, BarBuilder) () -> Unit) {

    }
}

@FooDsl
class FooBuilder() {
    fun runFoo() {

    }
}

@FooDsl
class BarBuilder() {
    fun runBar() {

    }

}