class Day5 : DayX() {
    private val rules = input.takeWhile { it.isNotBlank() }.map { it.split('|').map(String::toInt) }
    private val updates = input.takeLastWhile { it.isNotBlank() }.map { it.split(',').map(String::toInt) }
    private val ruleMap = HashMap<Int, HashSet<Int>>()
    private val comparator = Comparator<Int> { a, b ->
        if (ruleMap[a]?.contains(b) == true) -1 else 0
    }

    init {
        for ((from, to) in rules) {
            ruleMap.computeIfAbsent(from) { HashSet() }.add(to)
        }
    }

    override fun part1(): Long {
        var result = 0L

        for (update in updates) {
            if (isValid(update)) {
                result += update[update.size / 2]
            }
        }

        return result
    }

    private fun isValid(update: List<Int>): Boolean = update.sortedWith(comparator) == update

    override fun part2(): Long {
        val invalidUpdates = updates.filterNot { isValid(it) }
        var result = 0L
        
        for (update in invalidUpdates) {
            val fixed = update.sortedWith(comparator)
            result += fixed[fixed.size / 2]
        }

        return result
    }
}

fun main() {
    val day = Day5()
    day.evaluate()
    // Part 1: 5374
    // Part 2: 4260
}
            