import java.util.*

class Day3 {
    private val input = run {
        this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines().joinToString("")
    }

    fun part1(): Long {
        val pattern = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        var result = 0L
        val matches = pattern.findAll(input)
        for (match in matches) {
            val (a, b) = match.destructured
            result += a.toLong() * b.toLong()
        }
        return result
    }

    fun part2(): Long {
        var result = 0L
        val doPattern = Regex("do\\(\\)")
        val dontPattern = Regex("don't\\(\\)")
        val pattern = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val treeMap = TreeMap<Int, Boolean>()
        treeMap[0] = true
        val domatches = doPattern.findAll(input)
        for (match in domatches) {
            treeMap[match.range.last] = true
        }
        val dontmatches = dontPattern.findAll(input)
        for (match in dontmatches) {
            treeMap[match.range.last] = false
        }

        val matches = pattern.findAll(input)
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
    println("part1: " + day.part1()) // 175615763
    println("part2: " + day.part2()) // 74361272
}
            