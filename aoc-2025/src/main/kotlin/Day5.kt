import java.util.Stack

class Day5(title: String) : DayX(title) {

    override fun part1(): Any {
        val ranges = input.takeWhile { it.isNotBlank() }.map {
            val (left, right) = it.split('-')
            LongRange(left.toLong(), right.toLong())
        }
        val ingridients = input.takeLastWhile { it.isNotBlank() }.map { it.toLong() }
        var result = 0L

        for (i in ingridients) {
            for (range in ranges) {
                if (i in range) {
                    result++
                    break
                }
            }
        }

        return result
    }

    override fun part2(): Any {
        val ranges = input.takeWhile { it.isNotBlank() }.map {
            val (left, right) = it.split('-')
            LongRange(left.toLong(), right.toLong())
        }.sortedWith { r1, r2 ->
            if (r1.start != r2.start) r1.start.compareTo(r2.start) else r1.endInclusive.compareTo(r2.endInclusive)
        }

        val stack = Stack<LongRange>()

        for (range in ranges) {
            if (stack.isEmpty()) {
                stack.push(range)
                continue
            }

            var last = stack.peek()
            if (range.start <= last.endInclusive + 1) {
                last = stack.pop()
                val newRange = LongRange(last.start, maxOf(last.endInclusive, range.endInclusive))
                stack.push(newRange)
            } else {
                stack.push(range)
            }
        }
        var result = 0L

        for (range in stack) {
            result += (range.endInclusive - range.start + 1)
        }

        return result
    }
}

fun main() {
    val day = Day5("Day 5: Cafeteria")
    day.solve()
    // Example part 1: 3
    // Example part 2: 14
    // Real part 1: 821
    // Real part 2: 344771884978261
}
