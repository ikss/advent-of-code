class Day13 : DayX() {

    private val costA = 3
    private val costB = 1

    override fun part1(): Long {
        var result = 0L

        for (i in 0 until input.size - 2 step 4) {
            var line1 = input[i]
            val line2 = input[i + 1]
            val line3 = input[i + 2]
            result += solveEquation(line1, line2, line3, 0)
        }

        return result
    }

    private fun solveEquation(line1: String, line2: String, line3: String, addition: Long): Long {
        val pattern = Regex("\\d+")
        val (ax, ay) = pattern.findAll(line1).map { it.value.toLong() }.toList()
        val (bx, by) = pattern.findAll(line2).map { it.value.toLong() }.toList()
        val (prizex, prizey) = pattern.findAll(line3).map { it.value.toLong() + addition }.toList()

        // equation 1 = a * ax + b * bx = prizex
        // equation 2 = a * ay + b * by = prizexy
        // b = (prizey - a * ay) / by
        // a * ax + (prizey - a * ay) / by * bx = prizex
        // a * ax * by + (prizey - a * ay) * bx = prizex * by
        // a * ax * by + prizey * bx - a * ay * bx = prizex * by
        // a * (ax * by - ay * bx) = prizex * by - prizey * bx
        // a = (prizex * by - prizey * bx) / (ax * by - ay * bx)

        val a = (prizex * by - prizey * bx) / (ax * by - ay * bx)
        val b = (prizey - a * ay) / by

        if (a * ax + b * bx != prizex || a * ay + b * by != prizey) {
            return 0
        }

        return a * costA + b * costB
    }


    override fun part2(): Long {
        var result = 0L

        for (i in 0 until input.size - 2 step 4) {
            val line1 = input[i]
            val line2 = input[i + 1]
            val line3 = input[i + 2]
            result += solveEquation(line1, line2, line3, 10000000000000)
        }

        return result
    }

}

fun main() {
    val day = Day13()
    day.solve()
    // Part 1: 
    // Part 2: 
}
