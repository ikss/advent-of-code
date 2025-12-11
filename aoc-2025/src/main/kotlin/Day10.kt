class Day10(title: String) : DayX(title) {

    override fun part1(): Any {
        var result = 0L

        val machines = input.map { it.toMachine() }

        for (machine in machines) {
            val initial = ".".repeat(machine.requirements.length)
            val cache = HashMap<String, Long>()
            result += findMinimalOnline(machine, initial, 0L, cache)
        }

        return result
    }

    private fun findMinimalOnline(machine: Machine, state: String, presses: Long, cache: HashMap<String, Long>): Long {
        val cached = cache[state]
        if (cached != null && cached <= presses) {
            return -1L
        }
        cache[state] = presses

        if (state == machine.requirements) {
            return presses
        }

        var result = Long.MAX_VALUE

        for (button in machine.buttons) {
            val newStateChars = state.toCharArray()
            for (j in button) {
                newStateChars[j] = if (newStateChars[j] == '.') '#' else '.'
            }
            val newState = String(newStateChars)

            val subResult = findMinimalOnline(machine, newState, presses + 1, cache)
            if (subResult == -1L) {
                continue
            }
            result = minOf(result, subResult)
        }
        return if (result == Long.MAX_VALUE) -1 else result
    }

    override fun part2(): Any {
        var result = 0L
        // solved with Z3
        return result
    }

    val regex = Regex("""\[([^\]]+)\]\s+(.+?)\s+\{([^}]+)}""")
    private fun String.toMachine(): Machine {
        // [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        val match = regex.find(this)!!
        val requirements = match.groupValues[1]
        val middlePart = match.groupValues[2]
        val joltage = match.groupValues[3]

        return Machine(
            requirements = requirements,
            buttons = middlePart.split(") (").map { it.trim('(', ')').split(',').map { it.toInt() } },
            joltage = joltage.split(',').map { it.toInt() },
        )
    }

    private data class Machine(
        val requirements: String,
        val buttons: List<List<Int>>,
        val joltage: List<Int>,
    )
}

fun main() {
    val day = Day10("Day 10: Factory")
    day.solve()
    // Example part 1: 7
    // Example part 2: 33
    // Real part 1: 459
    // Real part 2: 18687
}
