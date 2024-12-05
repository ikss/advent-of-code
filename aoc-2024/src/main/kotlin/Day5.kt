class Day5 {
    private val input = run {
        this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines()
    }
    private val rules = input.takeWhile { it.isNotBlank() }.map { it.split('|').map { it.toInt() } }
    private val updates = input.takeLastWhile { it.isNotBlank() }.map { it.split(',').map { it.toInt() } }
    private val ruleMap = HashMap<Int, HashSet<Int>>()
    private val comparator = Comparator<Int> { a, b ->
        if (ruleMap[a]?.contains(b) == true) -1 else 0
    }

    init {
        for ((from, to) in rules) {
            ruleMap.computeIfAbsent(from) { HashSet() }.add(to)
        }
    }

    fun part1(): Long {
        var result = 0L

        for (update in updates) {
            if (isValid(update)) {
                result += update[update.size / 2]
            }
        }

        return result
    }

    private fun isValid(update: List<Int>): Boolean = update.sortedWith(comparator) == update

    fun part2(): Long {
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
    println("part1: " + day.part1()) // 5374
    println("part2: " + day.part2()) // 4260
}
            