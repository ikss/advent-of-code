class Day11 : DayX() {
    private val sequence = input.joinToString("").splitToNumbers(' ')

    override fun part1(): Long {
        return solve(25)
    }

    override fun part2(): Long {
        return solve(75)
    }

    private fun solve(iterations: Int): Long {
        var map = HashMap<Long, Long>()
        for (num in sequence) {
            map[num] = map.getOrDefault(num, 0) + 1
        }

        for (i in 0 until iterations) {
            val newMap = HashMap<Long, Long>()
            for ((num, count) in map) {
                for (newNum in applyRules(num)) {
                    newMap[newNum] = newMap.getOrDefault(newNum, 0) + count
                }
            }
            map = newMap
        }

        return map.values.sum()
    }

    private fun applyRules(num: Long): List<Long> =
        when {
            num == 0L -> listOf(1L)
            num.toString().length % 2 == 0 -> {
                val str = num.toString()
                val leftHalf = str.substring(0, str.length / 2)
                val rightHalf = str.substring(str.length / 2)
                listOf(leftHalf.toLong(), rightHalf.replaceFirst("0+", "").toLongOrNull() ?: 0L)
            }

            else -> {
                listOf(num * 2024)
            }
        }
}

fun main() {
    val day = Day11()
    day.solve()
    // Part 1: 186424
    // Part 2: 219838428124832
}
