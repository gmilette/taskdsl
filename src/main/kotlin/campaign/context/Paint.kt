package campaign.context

class PaintMain {
    fun runPaintExample() {
        val paint = Paint()
        paint.start(10, 10)
        paint.lineTo(10, 20)
        paint.lineTo(20, 20)
        paint.lineTo(20, 10)
        paint.lineTo(10, 10)

        val paintNoContext = Paint()
        paint.lineFromTo(10, 10, 10, 20)
        paint.lineFromTo(10, 20, 20, 20)
        paint.lineFromTo(20, 20, 20, 10)
        paint.lineFromTo(20, 10, 10, 10)
    }

    fun runPaintExampleBad() {

        // square1
        val paint = Paint()
        paint.start(10, 10)
        paint.lineTo(10, 20)
        paint.lineTo(20, 20)
        paint.lineTo(20, 10)
        paint.lineTo(10, 10)

        // square2
        paint.lineTo(110, 110)
        paint.lineTo(110, 120)
        paint.lineTo(120, 120)
        paint.lineTo(120, 110)
        paint.lineTo(110, 110)
    }

    fun runPaintFixes() {

        // square1
        val paint = Paint()

        // Nested Builders
        paint.start(10, 10)
            .lineTo(10, 20)
            .lineTo(20, 20)
            .lineTo(20, 10)
            .lineTo(10, 10)

        // Lambda with Receiver
        paint.draw(10, 10) {
            lineTo(10, 20)
            lineTo(20, 20)
            lineTo(20, 10)
            lineTo(10, 10)
        }
    }

}

class PaintBuilder() {
    var currentX: Int = 0
    var currentY: Int = 0

    fun start(startX: Int, startY: Int): LineBuilder {
        currentX = startX
        currentY = startY
        return LineBuilder()
    } 
    
    class LineBuilder {
        fun lineTo(x: Int, y: Int): LineBuilder {
            return this
        }
    }
}

class Paint {
    fun start(startX: Int, startY: Int): Paint {
        return this
    }

    fun moveFromTo(startX: Int, startY: Int, endX: Int, endY: Int) {

    }

    fun lineFromTo(startX: Int, startY: Int, endX: Int, endY: Int) {

    }


    fun moveTo(x: Int, y: Int) {

    }

    fun lineTo(x: Int, y: Int): Paint {
        return this
    }

    fun draw(startX: Int, startY: Int, block: Paint.() -> Unit) {

    }
}