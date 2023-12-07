import kotlin.math.pow

class Day4(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Int = getWinningNumberAndRegularNumbers()
        .map { (winning, card) -> 2.0.pow(card.count { it in winning } - 1).toInt() }
        .sum()

    fun part2(): Int {
        val numbers = getWinningNumberAndRegularNumbers().withIndex()
            .map { (index, pair) -> index to pair.second.count { it in pair.first } }
            .toList()
        val cardCount = IntArray(numbers.size) { 1 }
        for ((index, count) in numbers) {
            for (step in 1..count) {
                cardCount[index + step] += cardCount[index]
            }
        }

        return cardCount.sum()
    }

    private fun getWinningNumberAndRegularNumbers(): Sequence<Pair<Set<Long>, List<Long>>> =
        input.asSequence()
            .map {
                val (winning, card) = it.substringAfter(": ").split(" | ")
                winning.splitToNumbers().toSet() to card.splitToNumbers().toList()
            }
}

fun main() {
    val daytest = Day4("day4_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 13
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 30

    val day = Day4("day4.txt")
    println("part1: ${day.part1()}")             //part1: 25010
    println("part2: ${day.part2()}")             //part2: 9924412
}