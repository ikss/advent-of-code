class Day8(title: String) : DayX(title) {
    private val wide = 25
    private val tall = 6

    override fun part1(): Long {
        var result = 0L
        input.joinToString("").chunked(wide * tall).minBy { it.count { c -> c == '0' } }.let {
            val ones = it.count { c -> c == '1' }
            val twos = it.count { c -> c == '2' }
            result = ones.toLong() * twos
        }

        return result
    }

    override fun part2(): Long {
        val grid = Array(tall) { IntArray(wide) { 2 } }

        input.joinToString("").chunked(wide * tall).forEach {
            it.forEachIndexed { index, c ->
                val row = index / wide % tall
                val col = index % wide
                if (grid[row][col] == 2) {
                    grid[row][col] = c.toString().toInt()
                }
            }
        }
        for (row in grid) {
            println(row.joinToString("") { if (it == 0) " " else "#" })
        }
        return 0L
    }
}

fun main() {
    val day = Day8("Day 8: Space Image Format")
    day.solve()
    // Part 1: 1330
    // Part 2: FAHEF
}
