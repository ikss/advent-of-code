class Day12(title: String) : DayX(title) {

    override fun part1(): Any {
        var result = 0L

        var areas = IntArray(6)
        for (i in 0 until 6) {
            for (l in 1..3) {
                areas[i] += input[i * 5 + l].count { it == '#' }
            }
        }

        for (l in input.drop(30)) {
            val (areanum, nums) = l.split(": ")
            val (x, y) = areanum.split("x")
            val area = x.toLong() * y.toLong()
            val need = nums.split(" ").map { it.toInt() }.withIndex().sumOf { areas[it.index].toLong() * it.value }
            if (need <= area) {
                result++
            }
        }

        return result
    }

    override fun part2(): Any {
        var result = 0L

        return result
    }
}

fun main() {
    val day = Day12("Day 12: Christmas Tree Farm")
    day.solve()
    // Example part 1: 2
    // Real part 1: 433
}
