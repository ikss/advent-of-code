class Day6(title: String) : DayX(title) {

    override fun part1(): Any {
        val lines = input.map { it.dropLast(1) }.map { it.split(Regex(" ")).filter(String::isNotEmpty) }

        var result = 0L

        for (i in lines[0].indices) {
            val operation = lines.last()[i][0]
            var interm = if (operation == '+') 0L else 1L
            for (line in lines.dropLast(1)) {
                val value = line[i].toLong()

                when (operation) {
                    '+' -> interm += value
                    '*' -> interm *= value
                }
            }
            result += interm
        }

        return result
    }

    override fun part2(): Any {
        val lines = input.map { it.dropLast(1) }
        val indices = ArrayList<Pair<Int, Char>>()
        for (i in lines.last().indices) {
            if (lines.last()[i] != ' ') {
                indices.add(i to lines.last()[i])
            }
        }

        var result = 0L

        for (i in 0 until indices.size) {
            val (index, operation) = indices[i]
            val nextIndex = if (i != indices.size - 1) indices[i + 1].first - 1 else lines.last().length

            val numbers = readBlock(index, nextIndex)

            var interm = if (operation == '+') 0L else 1L

            for (number in numbers) {
                when (operation) {
                    '+' -> interm += number
                    '*' -> interm *= number
                }
            }
            result += interm
        }


        return result
    }

    private fun readBlock(index: Int, nextIndex: Int): List<Long> {
        val numbers = ArrayList<Long>()
        for (i in index until nextIndex) {
            var result = 0L
            for (line in input.dropLast(1)) {
                val char = line[i]
                if (char == ' ') {
                    continue
                }
                result *= 10
                result += char - '0'
            }
            numbers.add(result)
        }
        return numbers
    }
}

fun main() {
    val day = Day6("Day 6: Trash Compactor")
    day.solve()
    // Example part 1: 4277556
    // Example part 2: 3263827
    // Real part 1: 5060053676136
    // Real part 2: 9695042567249
}
