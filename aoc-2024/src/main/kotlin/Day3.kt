class Day3 {
    private val input = run {
        this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines()
    }

    fun part1(): Long {
        return 0L
    }

    fun part2(): Long {
        return 0L
    }
}

fun main() {
    val day = Day3()
    println("part1: " + day.part1()) // 279
    println("part2: " + day.part2()) // 343
}
            