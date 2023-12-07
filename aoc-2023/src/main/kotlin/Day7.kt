class Day7(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()
    private val cards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()
    private val newCards = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()

    fun part1(): Long {
        val sortedWith = input
            .asSequence()
            .map { val (hand, rank) = it.split(" "); hand to rank.toLong() }
            .sortedWith { h1, h2 -> compareHands(h1.first, h2.first) }
            .toList()
        return sortedWith
            .mapIndexed { index, (_, rank) -> rank * (index + 1) }
            .sum()
    }

    private fun compareHands(first: String, second: String): Int {
        val h1pow = getPow(first)
        val h2pow = getPow(second)
        if (h1pow != h2pow) return h1pow.compareTo(h2pow)


        return compareHighCard(first, second, cards)
    }

    private fun compareHighCard(first: String, second: String, cards: List<Char>): Int {
        for (i in 0..<5) {
            if (first[i] != second[i]) {
                return compareCards(first[i], second[i], cards)
            }
        }
        return 0
    }

    private fun getPow(first: String): Int {
        val map = hashMapOf<Char, Int>()
        for (c in first) {
            map.merge(c, 1, Int::plus)
        }
        if (map.size == 1) {
            return 6
        } else if (map.size == 5) {
            return 0
        } else if (map.size == 2) {
            if (map.any { it.value == 4 }) {
                return 5
            }
            return 4
        } else if (map.size == 3) {
            if (map.any { it.value == 3 }) {
                return 3
            }
            return 2
        }
        return 1
    }

    private fun compareCards(c1: Char, c2: Char, cards: List<Char>): Int {
        return cards.indexOf(c1).compareTo(cards.indexOf(c2))
    }

    fun part2(): Long {
        return input
            .asSequence()
            .map { val (hand, rank) = it.split(" "); hand to rank.toLong() }
            .map { (hand, rank) ->
                val pow = getPow(hand)
                if (!hand.contains('J')) {
                    (hand to pow) to rank
                }
                var maxPow = pow
                for (c in cards) {
                    val newHand = hand.replace('J', c)
                    val newPow = getPow(newHand)
                    maxPow = maxOf(maxPow, newPow)
                }
                (hand to maxPow) to rank
            }
            .sortedWith { h1, h2 ->
                if (h1.first.second != h2.first.second) {
                    h1.first.second.compareTo(h2.first.second)
                } else {
                    compareHighCard(h1.first.first, h2.first.first, newCards)
                }
            }
            .mapIndexed { index, (_, rank) -> rank * (index + 1) }
            .sum()
    }
}

fun main() {
    val daytest = Day7("day7_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 6440
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 5905

    val day = Day7("day7.txt")
    println("part1: ${day.part1()}")             //part1: 251136060
    println("part2: ${day.part2()}")             //part2: 249400220
}