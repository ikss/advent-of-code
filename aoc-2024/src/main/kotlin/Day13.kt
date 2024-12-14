class Day13 : DayX() {
    private val linesGrouped = input.chunked(4)
    private val costA = 3
    private val costB = 1

    override fun part1(): Long {
        var result = 0L

        for (lines in linesGrouped) {
            result += solveEquation(lines, 0)
        }

        return result
    }

    override fun part2(): Long {
        var result = 0L

        for (lines in linesGrouped) {
            result += solveEquation(lines, 10000000000000)
        }

        return result
    }

    private fun solveEquation(lines: List<String>, addition: Long): Long {
        val (line1, line2, line3) = lines
        val (ax, ay) = line1.readAllNumbers().toList()
        val (bx, by) = line2.readAllNumbers().toList()
        val (prizex, prizey) = line3.readAllNumbers().map { it + addition }.toList()

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

}

fun main() {
    val day = Day13()
    day.solve()
    // Part 1: 35082
    // Part 2: 82570698600470
}
