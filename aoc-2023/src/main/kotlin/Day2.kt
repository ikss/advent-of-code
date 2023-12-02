class Day2(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Int {
        return input.asSequence()
            .map {
                val (first, last) = it.split(": ")
                first.substringAfter(" ").toInt() to last
            }
            .filter { validate(it.second) }
            .sumOf { it.first }
    }

    fun part2(): Int {
        return input.asSequence()
            .map {
                val (_, last) = it.split(": ")
                last
            }
            .map { getPower(it) }
            .sum()
    }

    private fun getPower(s: String): Int {
        val games = s.split("; ")
        val map = mutableMapOf<String, Int>()
        for (g in games) {
            val balls = g.split(", ")
            for (b in balls) {
                val (count, color) = b.split(" ")
                map.merge(color, count.toInt(), Math::max)
            }
        }
        if (map.size != 3) return 0
        return map.values.reduce { acc, i -> acc * i }
    }

    private fun validate(s: String): Boolean {
        val games = s.split("; ")
        for (g in games) {
            val balls = g.split(", ")
            for (b in balls) {
                val (count, color) = b.split(" ")
                when (color) {
                    "red" -> if (count.toInt() > 12) return false
                    "green" -> if (count.toInt() > 13) return false
                    "blue" -> if (count.toInt() > 14) return false
                }
            }
        }
        return true
    }

}

fun main() {
    val daytest = Day2("day2_test.txt")
    println("part1 test: ${daytest.part1()}")
    println("part2 test: ${daytest.part2()}\n")

    val day = Day2("day2.txt")
    println("part1: ${day.part1()}")
    println("part2: ${day.part2()}")
}
            