fun List<LongRange>.mergeRanges(): List<LongRange> {
    if (this.isEmpty()) return this
    val result = ArrayList<LongRange>()

    var prev = this[0]
    for (i in 1 until size) {
        val curr = this[i]

        if (curr.first <= prev.last) {
            prev = LongRange(prev.first, maxOf(prev.last, curr.last))
        } else {
            result.add(prev)
            prev = curr
        }
    }
    result.add(prev)

    return result
}

fun LongRange.size(): Long = last - first + 1