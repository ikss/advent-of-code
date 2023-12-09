class Day9(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        return input.map { findNextStory(it.splitToNumbers().toList()) }
            .sum()
    }

    fun part2(): Long {
        return input.map { findPrevStory(it.splitToNumbers().toList()) }
            .sum()
    }

    private fun findNextStory(numbers: List<Long>): Long {
        val result = ArrayList<Long>()
        for (i in 1..<numbers.size) {
            result.add(numbers[i] - numbers[i - 1])
        }
        return if (result.toSet().size == 1) {
            numbers.last() + result.first()
        } else {
            val diff = findNextStory(result)
            numbers.last() + diff
        }
    }

    private fun findPrevStory(numbers: List<Long>): Long {
        val result = ArrayList<Long>()
        for (i in 1..<numbers.size) {
            result.add(numbers[i] - numbers[i - 1])
        }
        return if (result.toSet().size == 1) {
            numbers.first() - result.first()
        } else {
            val diff = findPrevStory(result)
            numbers.first() - diff
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