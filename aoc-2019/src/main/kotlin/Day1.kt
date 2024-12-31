class Day1(title: String) : DayX(title) {
    private val modules = input.map { it.toLong() }

    override fun part1(): Long {
        return modules.sumOf { calculateFuel(it, false) }
    }

    override fun part2(): Long {
        return modules.sumOf { calculateFuel(it, true) }
    }

    private fun calculateFuel(mass: Long, recursive: Boolean): Long {
        val fuel = mass / 3 - 2
        return when {
            fuel < 0 -> 0
            !recursive -> fuel
            else -> fuel + calculateFuel(fuel, true)
        }
    }
}

fun main() {
    val day = Day1("Day 1: The Tyranny of the Rocket Equation")
    day.solve()
    // Part 1: 3224048
    // Part 2: 4833211
}
