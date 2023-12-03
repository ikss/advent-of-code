class Day3(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Int = getAllSymbols { true }
        .flatMap { findAdjacentNumbers(it) }
        .sum()

    fun part2(): Int = getAllSymbols { it == '*' }
        .map { findAdjacentNumbers(it) }
        .filter { it.size == 2 }
        .sumOf { it.first() * it.last() }

    private fun getAllSymbols(predicate: (Char) -> Boolean): List<Pair<Int, Int>> {
        val symbols = ArrayList<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                val c = input[i][j]
                if (c.isDigit() || c == '.' || !predicate(c)) continue
                symbols.add(i to j)
            }
        }
        return symbols
    }

    private fun findAdjacentNumbers(symbol: Pair<Int, Int>): List<Int> {
        val (i, j) = symbol
        val numbers = ArrayList<Int>()
        val visited = HashSet<Pair<Int, Int>>()

        for ((di, dj) in directions) {
            val newI = i + di
            val newJ = j + dj
            if (!visited.add(newI to newJ)) continue

            if (input[newI][newJ].isDigit()) {
                numbers.add(findNumber(newI, newJ, visited))
            }
        }
        return numbers
    }

    private fun findNumber(i: Int, j: Int, visited: HashSet<Pair<Int, Int>>): Int {
        val row = input[i]
        var j = j
        while (j > 0 && row[j - 1].isDigit()) {
            j--
        }
        val number = StringBuilder()

        // start moving right to read the entire number, stopping at a . or if we hit the right end of the grid
        while (j < row.length && row[j].isDigit()) {
            number.append(row[j])
            visited.add(i to j)
            j++
        }
        return number.toString().toInt()
    }

    private companion object {
        private val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
    }
}

fun main() {
    val daytest = Day3("day3_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 4361
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 467835

    val day = Day3("day3.txt")
    println("part1: ${day.part1()}")             //part1: 546563
    println("part2: ${day.part2()}")             //part2: 91031374
}