import java.util.*

class Day3 : DayX() {
    private val joinedInput = input.joinToString("")

    override fun part1(): Long {
        val pattern = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        var result = 0L
        val matches = pattern.findAll(joinedInput)
        for (match in matches) {
            val (a, b) = match.destructured
            result += a.toLong() * b.toLong()
        }
        return result
    }

    override fun part2(): Long {
        var result = 0L
        val doPattern = Regex("do\\(\\)")
        val dontPattern = Regex("don't\\(\\)")
        val pattern = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val treeMap = TreeMap<Int, Boolean>()
        treeMap[0] = true
        val domatches = doPattern.findAll(joinedInput)
        for (match in domatches) {
            treeMap[match.range.last] = true
        }
        val dontmatches = dontPattern.findAll(joinedInput)
        for (match in dontmatches) {
            treeMap[match.range.last] = false
        }

        val matches = pattern.findAll(joinedInput)
        for (match in matches) {
            if (treeMap.floorEntry(match.range.first).value == true) {
                val (a, b) = match.destructured
                result += a.toLong() * b.toLong()
            }
        }
        return result
    }
}

fun main() {
    val day = Day3()
    day.solve()
    // Part 1: 175615763
    // Part 2: 74361272
}
            