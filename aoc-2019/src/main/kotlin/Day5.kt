class Day5(title: String) : DayX(title) {
    private val codes = input.readAllNumbers().toList()

    override fun part1(): Long {
        val input = listOf(1L).toBlockingQueue()

        val intcodeComputer = IntcodeComputer(codes, input)
        intcodeComputer.execute()
        return intcodeComputer.output.last()
    }

    override fun part2(): Long {
        val input = listOf(5L).toBlockingQueue()

        val intcodeComputer = IntcodeComputer(codes, input)
        intcodeComputer.execute()
        return intcodeComputer.output.last()
    }
}

fun main() {
    val day = Day5("Day 5: Sunny with a Chance of Asteroids")
    day.solve()
    // Part 1: 13294380
    // Part 2: 11460760
}
