class Day5(title: String) : DayX(title) {

    override fun part1(): Long {
        val codes = input.joinToString(",").readAllNumbers().toMutableList()
        val input = 1

        return IntcodeComputer(codes, input).execute()
    }

    override fun part2(): Long {
        val codes = input.joinToString(",").readAllNumbers().toMutableList()
        val input = 5

        return IntcodeComputer(codes, input).execute()
    }
}

fun main() {
    val day = Day5("Day 5: Sunny with a Chance of Asteroids")
    day.solve()
    // Part 1: 13294380
    // Part 2: 11460760
}
