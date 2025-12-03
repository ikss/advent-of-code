class Day2(title: String) : DayX(title) {


    override fun part1(): Any {
        val ranges = input.joinToString(",").split(",")
        var result = 0L

        for (line in ranges) {
            val (first, second) = line.split("-").map { it.toLong() }

            for (num in first..second) {
                if (isInvalid1(num)) {
                    result += num
                }
            }
        }

        return result
    }

    private fun isInvalid1(num: Long): Boolean {
        val numstr = num.toString()
        val len = numstr.length
        if (len == 1 || len % 2 != 0) return false

        if (numstr.take(len / 2) == numstr.substring(len / 2)) {
            return true
        }
        return false
    }

    override fun part2(): Any {
        val ranges = input.joinToString(",").split(",")
        var result = 0L

        for (line in ranges) {
            val (first, second) = line.split("-").map { it.toLong() }


            for (num in first..second) {
                if (isInvalid2(num)) {
                    result += num
                }
            }
        }

        return result
    }

    private fun isInvalid2(num: Long): Boolean {
        val numstr = num.toString()
        val len = numstr.length

        for (i in 1..len / 2) {
            if (len % i != 0) continue
            val substr = numstr.take(i)
            var proper = false
            for (j in 0 until len / i) {
                if (numstr.substring(j * i, (j + 1) * i) != substr) {
                    proper = true
                    break
                }
            }
            if (!proper) {
                return true
            }
        }
        return false
    }
}

fun main() {
    val day = Day2("Day 2: Gift Shop")
    day.solve()
    // Part 1: 30608905813
    // Part 2: 31898925685
}
