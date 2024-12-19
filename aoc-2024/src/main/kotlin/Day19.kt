class Day19 : DayX() {

    override fun part1(): Long {
        val towels = input[0].split(", ")

        val patterns = input.takeLastWhile { it.isNotEmpty() }

        return patterns.count { canBreak(it, towels) > 0L }.toLong()
    }

    override fun part2(): Long {
        val towels = input[0].split(", ")

        val patterns = input.takeLastWhile { it.isNotEmpty() }

        return patterns.sumOf { canBreak(it, towels) }
    }

    private fun canBreak(p: String, towels: List<String>): Long {
        val dp = LongArray(p.length)

        for (i in p.indices) {
            for (t in towels) {
                if (i < t.length - 1) {
                    continue
                }
                if (i == t.length - 1) {
                    if (p.substring(0, i + 1) == t) {
                        dp[i] += 1L
                    }
                } else if (dp[i - t.length] > 0 && p.substring(i - t.length + 1, i + 1) == t) {
                    dp[i] += dp[i - t.length]
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