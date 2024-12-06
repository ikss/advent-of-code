class Day2 : DayX() {

    override fun part1(): Long {
        var result = 0L
        for (element in input) {
            val line = element.splitToNumbers().toList()
            val safe = checkSafe(line)
            if (safe) result++
        }

        return result
    }

    private fun checkSafe(line: List<Long>): Boolean {
        if (line[1] == line[0]) return false
        val decreasing = line[1] <= line[0]

        for (i in 1 until line.size) {
            if (decreasing) {
                if (line[i - 1] - line[i] !in 1..3) {
                    return false
                }
            } else {
                if (line[i] - line[i - 1] !in 1..3) {
                    return false
                }
            }
        }
        return true
    }

    override fun part2(): Long {
        var result = 0L
        for (element in input) {
            val line = element.splitToNumbers().toMutableList()
            var safe = checkSafe(line)
            if (!safe) {
                for (i in line.indices) {
                    val copy = ArrayList(line)
                    copy.removeAt(i)
                    safe = checkSafe(copy)
                    if (safe) break
                }
            }
            if (safe) result++
        }

        return result
    }
}

fun main() {
    val day = Day2()
    day.evaluate()
    // Part 1: 279
    // Part 2: 343
}
