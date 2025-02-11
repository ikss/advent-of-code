import java.util.*

private typealias LongOperation = (Long, Long) -> Long

class Day7(title: String) : DayX(title) {
    private val operations = input.map {
        val (res, operands) = it.split(':')
        res.toLong() to operands.splitToNumbers().toList()
    }

    override fun part1(): Any {
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
            val newNums = LinkedList(nums)
            newNums.addFirst(op(first, second))
            if (isValid(res, newNums, ops)) {
                return true
            }
        }
        return false
    }

    override fun part2(): Any {
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
    val day = Day7("Day 7: Bridge Repair")
    day.solve()
    // Part 1: 2437272016585
    // Part 2: 162987117690649
}
