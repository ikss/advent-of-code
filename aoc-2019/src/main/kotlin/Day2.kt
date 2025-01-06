class Day2(title: String) : DayX(title) {

    override fun part1(): Long {
        val codes = input.readAllNumbers().toMutableList()
        codes[1] = 12
        codes[2] = 2

        val computer = IntcodeComputer(codes)
        computer.execute()
        return computer.memory[0]!!
    }

    override fun part2(): Long {
        val codes = input.joinToString(",").readAllNumbers()
        val max = 100L
        for (first in 0..max) {
            for (second in 0..max) {
                try {
                    val copy = codes.toMutableList()
                    copy[1] = first
                    copy[2] = second
                    val computer = IntcodeComputer(copy)
                    computer.execute()
                    if (computer.memory[0]!! == 19690720L) {
                        return 100 * first + second
                    }
                } catch (_: Exception) {
                    continue
                }
            }
        }
        return -1L
    }
}

fun main() {
    val day = Day2("Day 2: 1202 Program Alarm")
    day.solve()
    // Part 1: 3931283
    // Part 2: 6979
}
