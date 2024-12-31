class Day4(title: String) : DayX(title) {

    override fun part1(): Int {
        val (min, max) = input[0].split('-')

        return countDfs(0, 0, false, min.toInt(), max.toInt())
    }

    private fun countDfs(pos: Int, curr: Int, countOnlyTwo: Boolean, min: Int, max: Int): Int {
        if (pos == 6) {
            return if (curr in min..max && hasSame(curr, countOnlyTwo)) 1 else 0
        }

        var res = 0
        for (i in curr % 10..9) {
            res += countDfs(pos + 1, curr * 10 + i, countOnlyTwo, min, max)
        }

        return res
    }

    private fun hasSame(curr: Int, countOnlyTwo: Boolean): Boolean {
        val str = curr.toString()
        for (i in 1 until str.length) {
            if (str[i] == str[i - 1]) {
                if (!countOnlyTwo || (i < 2 || str[i - 2] != str[i]) && (i + 1 >= str.length || str[i + 1] != str[i])) {
                    return true
                }
            }
        }

        return false
    }

    override fun part2(): Int {
        val (min, max) = input[0].split('-')

        return countDfs(0, 0, true, min.toInt(), max.toInt())
    }
}

fun main() {
    val day = Day4("Day 4: Secure Container")
    day.solve()
    // Part 1: 1767
    // Part 2: 1192
}
