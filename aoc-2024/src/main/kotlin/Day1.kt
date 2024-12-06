class Day1 : DayX() {

    override fun part1(): Long {
        val first = ArrayList<Long>()
        val second = ArrayList<Long>()

        for (element in input) {
            val line = element.splitToNumbers()
            val firstNumber = line.first()
            first.add(firstNumber)

            val secondNumber = line.last()
            second.add(secondNumber)
        }

        first.sort()
        second.sort()
        var result = 0L
        first.zip(second).forEach { (f, s) ->
            result += Math.abs(s - f)
        }

        return result
    }

    override fun part2(): Long {
        val first = ArrayList<Long>()
        val second = HashMap<Long, Int>()

        for (element in input) {
            val line = element.splitToNumbers()
            val firstNumber = line.first()
            first.add(firstNumber)

            val secondNumber = line.last()
            second.merge(secondNumber, 1, Int::plus)
        }

        var result = 0L
        for (n in first) {
            if (n in second) {
                result += n * second[n]!!
            }
        }

        return result
    }
}

fun main() {
    val day = Day1()
    day.evaluate()
    // Part 1: 2815556
    // Part 2: 23927637
}
