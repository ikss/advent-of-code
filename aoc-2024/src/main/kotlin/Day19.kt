class Day19 : DayX() {
    private val towels = input[0].split(", ")
    private val patterns = input.takeLastWhile { it.isNotEmpty() }

    override fun part1(): Any {

        return patterns.count { canBreak(it, towels) > 0L }
    }

    override fun part2(): Any {

        return patterns.sumOf { canBreak(it, towels) }
    }

    private fun canBreak(p: String, towels: List<String>): Long {
        val dp = LongArray(p.length)

        for (i in p.indices) {
            for (t in towels) {
                if (i < t.length - 1) {
                    continue
                }
                if (p.substring(i - t.length + 1, i + 1) == t) {
                    if (i == t.length - 1) {
                        dp[i] += 1L
                    } else {
                        dp[i] += dp[i - t.length]
                    }
                }
            }
        }

        return dp[p.length - 1]
    }
}

fun main() {
    val day = Day19()
    day.solve()
    // Part 1: 278
    // Part 2: 569808947758890
}