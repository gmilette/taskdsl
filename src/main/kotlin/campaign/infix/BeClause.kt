package campaign.infix

object be

infix fun Int.should(be: be): BeClause {
    return BeClause(this)
}

class BeClause(val compare: Int) {
    infix fun equal(expected: Int) {
        if (compare != expected) {
            throw RuntimeException("test fail")
        }
    }

    infix fun greaterThan(expected: Int) {
        if (compare < expected) {
            throw RuntimeException("test fail")
        }
    }

    infix fun lessThan(expected: Int) {
        if (compare > expected) {
            throw RuntimeException("test fail")
        }
    }

    infix fun not(expected: Int) {
        if (compare == expected) {
            throw RuntimeException("test fail")
        }
    }
}