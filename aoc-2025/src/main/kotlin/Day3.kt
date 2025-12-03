import java.util.*

class Day3(title: String) : DayX(title) {

    override fun part1(): Any {
        var result = 0L

        for (line in input) {
            result += getMax(line, 2)
        }

        return result
    }

    override fun part2(): Any {
        var result = 0L

        for (line in input) {
            result += getMax(line, 12)
        }

        return result
    }

    private fun getMax(line: String, size: Int): Long {
        val stack = Stack<Int>()
        var res = 0L

        for (c in line) {
            val digit = c - '0'

            stack.push(digit)
            if (stack.size > size) {
                bubbleUp(stack)
            }

            if (stack.size == size) {
                res = maxOf(res, getNumber(stack))
            }
        }
        return res
    }

    private fun bubbleUp(stack: Stack<Int>) {
        for (i in 0 until stack.size - 1) {
            if (stack[i] < stack[i + 1]) {
                stack.removeAt(i)
                return
            }
        }
        stack.pop()
    }

    private fun getNumber(chars: Stack<Int>): Long {
        var res = 0L
        for (c in chars) {
            res = res * 10 + c
        }
        return res
    }

}

fun main() {
    val day = Day3("Day 3: Lobby")
    day.solve()
    // Example part 1: 357
    // Example part 2: 3121910778619
    // Real part 1: 16993
    // Real part 2: 168617068915447
}
