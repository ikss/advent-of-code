import kotlin.math.pow

class Day25 : DayX() {

    override fun part1(): Any {
        var numericResult = 0L

        for (line in input) {
            numericResult += snafu2numeric(line)
        }

        return numeric2snafu(numericResult)
    }

    private fun numeric2snafu(numericResult: Long): String {

        var number = numericResult
        val snafuBuilder = StringBuilder()

        while (number != 0L) {
            var remainder = number % 5
            if (remainder > 2) {
                remainder -= 5
            }
            val char = when (remainder) {
                0L -> "0"
                1L -> "1"
                2L -> "2"
                -1L -> "-"
                -2L -> "="
                else -> throw IllegalArgumentException("Unexpected remainder: $remainder")
            }

            snafuBuilder.append(char)

            number = (number - remainder) / 5
        }

        return snafuBuilder.reverse().toString()
    }

    private fun snafu2numeric(line: String): Long {
        var pow = 0
        var result = 0L
        for (c in line.reversed()) {
            val add = when (c) {
                '2' -> 2
                '1' -> 1
                '0' -> 0
                '-' -> -1
                '=' -> -2
                else -> throw IllegalArgumentException("Unexpected character: $c")
            }
            result += add * 5.0.pow(pow).toLong()
            pow++
        }
        return result
    }

    override fun part2(): Any {
        var result = 0L

        return result
    }
}

fun main() {
    val day = Day25()
    day.solve()
    // Part 1: 20-1-0=-2=-2220=0011
    // Part 2: 
}
