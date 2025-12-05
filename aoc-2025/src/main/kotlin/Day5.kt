class Day5(title: String) : DayX(title) {

    override fun part1(): Any {
        val ranges = input.takeWhile { it.isNotBlank() }.readAllRanges()
        val ingridients = input.takeLastWhile { it.isNotBlank() }.map { it.toLong() }
        var result = 0L

        for (i in ingridients) {
            for (range in ranges) {
                if (i in range) {
                    result++
                    break
                }
            }
        }

        return result
    }

    override fun part2(): Any {
        val ranges = input.takeWhile { it.isNotBlank() }.readAllRanges().sortedBy { it.first }

        var result = 0L

        for (range in ranges.mergeRanges()) {
            result += (range.last - range.first + 1)
        }

        return result
    }
}

fun main() {
    val day = Day5("Day 5: Cafeteria")
    day.solve()
    // Example part 1: 3
    // Example part 2: 14
    // Real part 1: 821
    // Real part 2: 344771884978261
}
