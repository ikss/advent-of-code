class Day9(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        return input.sumOf { findStory(it.splitToNumbers().toList(), next = true) }
    }

    fun part2(): Long {
        return input.sumOf { findStory(it.splitToNumbers().toList(), next = false) }
    }

    private fun findStory(numbers: List<Long>, next: Boolean): Long {
        val result = ArrayList<Long>()
        for (i in 1..<numbers.size) {
            result.add(numbers[i] - numbers[i - 1])
        }
        val elem = if (next) numbers.last() else numbers.first()
        return if (result.toSet().size == 1) {
            elem - result.first()
        } else {
            val diff = findStory(result, next)
            if (next) elem + diff else elem - diff
        }
    }

}

fun main() {
    val daytest = Day9("day9_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 114
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 2

    val day = Day9("day9.txt")
    println("part1: ${day.part1()}")             //part1: 1819125966
    println("part2: ${day.part2()}")             //part2: 1140
}