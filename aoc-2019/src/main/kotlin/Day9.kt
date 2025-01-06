class Day9(title: String) : DayX(title) {
    private val inputNumbers = input.readAllNumbers().toList()

    override fun part1(): Long {
        val computer = IntcodeComputer(inputNumbers, listOf(1L).toBlockingQueue())
        computer.execute()

        return computer.output.poll()
    }

    override fun part2(): Long {
        val computer = IntcodeComputer(inputNumbers, listOf(2L).toBlockingQueue())
        computer.execute()

        return computer.output.poll()
    }
}

fun main() {
    val day = Day9("Day 9: Sensor Boost")
    day.solve()
    // Part 1: 3335138414
    // Part 2: 49122
}
