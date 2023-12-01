class Day1(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    private val digits = listOf(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
    )

    fun part1(): Int = input.sumOf { parseLine(it, ::parseDigit) }

    fun part2(): Int = input.sumOf { parseLine(it, ::parseDigitWithLetters) }

    private fun parseLine(line: String, parse: (String, Int) -> Int): Int {
        var calibrationValue = 0
        val indices = line.indices
        for (i in indices) {
            val digit = parse(line, i)
            if (digit != -1) {
                calibrationValue += 10 * digit
                break
            }
        }
        for (i in indices.reversed()) {
            val digit = parse(line, i)
            if (digit != -1) {
                calibrationValue += digit
                break
            }
        }
        return calibrationValue
    }

    private fun parseDigit(line: String, offset: Int): Int =
        if (line[offset].isDigit()) {
            line[offset] - '0'
        } else {
            -1
        }

    private fun parseDigitWithLetters(line: String, offset: Int): Int {
        val digit = parseDigit(line, offset)
        if (digit != -1) {
            return digit
        }
        for (i in 0..8) {
            if (line.startsWith(digits[i], offset)) {
                return i + 1
            }
        }
        return -1
    }
}

fun main() {
    val day1 = Day1("day1.txt")
    println("part1: ${day1.part1()}")
    println("part2: ${day1.part2()}")
}
            