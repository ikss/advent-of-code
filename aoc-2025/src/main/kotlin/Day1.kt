class Day1(title: String) : DayX(title) {

    override fun part1(): Any {
        var result = 0
        var start = 50

        for (line in input) {
            val direction = line[0]
            val count = line.drop(1).toInt()

            when (direction) {
                'L' -> start -= count
                'R' -> start += count
            }
            start %= 100
            if (start == 0) result++
        }
        return result
    }

    override fun part2(): Any {
        var result = 0
        var start = 50

        for (line in input) {
            val direction = line[0]
            val count = line.drop(1).toInt()
            for (r in 1..count) {
                when (direction) {
                    'L' -> start--
                    'R' -> start++

                }
                start %= 100
                if (start == 0) result++
            }
        }
        return result
    }
}

fun main() {
    val day = Day1("Day 1: Secret Entrance")
    day.solve()
    // Part 1:
    // Part 2:
}
