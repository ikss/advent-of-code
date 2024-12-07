import java.util.*

private typealias LongOperation = (Long, Long) -> Long

class Day7 : DayX() {
    private val operations = input.map {
        val (res, operands) = it.split(':')
        res.toLong() to operands.splitToNumbers().toList()
    }

    override fun part1(): Long {
        var result = 0L
        for ((res, operands) in operations) {
            val nums = LinkedList(operands)
            if (isValid(res, nums, listOf(Long::plus, Long::times))) {
                result += res
            }
        }
        return result
    }

    private fun isValid(res: Long, nums: LinkedList<Long>, ops: List<LongOperation>): Boolean {
        if (nums.size == 1) {
            return res == nums.poll()
        }
        val first = nums.pollFirst()
        val second = nums.pollFirst()
        for (op in ops) {
            nums.addFirst(op(first, second))
            if (isValid(res, LinkedList(nums), ops)) {
                return true
            }
            nums.pollFirst()
        }
        return false
    }

    override fun part2(): Long {
        var result = 0L
        for ((res, operands) in operations) {
            val nums = LinkedList(operands)
            if (isValid(res, nums, listOf(Long::plus, Long::times, Long::concat))) {
                result += res
            }
        }
        return result
    }

}

fun main() {
    val day = Day7()
    day.solve()
    // Part 1: 2437272016585
    // Part 2: 162987117690649
}
