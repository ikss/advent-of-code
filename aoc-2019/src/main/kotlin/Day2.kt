class Day2(title: String) : DayX(title) {

    override fun part1(): Long {
        val codes = input.joinToString(",").readAllNumbers().toMutableList()
        codes[1] = 12
        codes[2] = 2
        return executeProgram(codes)
    }

    override fun part2(): Long {
        val codes = input.joinToString(",").readAllNumbers().toMutableList()
        val max = 100L
        for (first in 0..max) {
            for (second in 0..max) {
                try {
                    val copy = codes.toMutableList()
                    copy[1] = first
                    copy[2] = second
                    if (executeProgram(copy) == 19690720L) {
                        return 100 * first + second
                    }
                } catch (_: Exception) {
                    continue
                }
            }
        }
        return -1L
    }

    private fun executeProgram(codes: MutableList<Long>): Long {
        for (i in 0 until codes.size step 4) {
            val opcode = codes[i]
            if (opcode == 99L) break
            val a = codes[i + 1]
            val b = codes[i + 2]
            val c = codes[i + 3]
            when (opcode) {
                1L -> codes[c.toInt()] = codes[a.toInt()] + codes[b.toInt()]
                2L -> codes[c.toInt()] = codes[a.toInt()] * codes[b.toInt()]
            }
        }
        return codes[0]
    }
}

fun main() {
    val day = Day2("Day 2: 1202 Program Alarm")
    day.solve()
    // Part 1: 3931283
    // Part 2: 6979
}
