class Day3(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Int =
        getAllSymbols { true }
            .flatMap { findAdjacentNumbers(it) }
            .sum()

    fun part2(): Int {
        val gears = getAllSymbols { it == '*' }
        return gears
            .map { findAdjacentNumbers(it) }
            .filter { it.size == 2 }
            .sumOf {
                it.reduce { a, b -> a * b }
            }
    }

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
        val (x, y) = symbol
        val numbers = ArrayList<Int>()
        val visited = HashSet<Pair<Int, Int>>()

        for ((dx, dy) in directions) {
            val newX = x + dx
            val newY = y + dy
            if (!visited.add(newX to newY)) continue

            if (input[newX][newY].isDigit()) {
                numbers.add(findNumber(newX, newY, visited))
            }
        }
        return numbers
    }

    private fun findNumber(x: Int, y: Int, visited: HashSet<Pair<Int, Int>>): Int {
        var y = y
        while (y >= 0 && input[x][y].isDigit()) {
            y--
        }

        // go back to the first digit of the number
        y++
        val number = StringBuilder()

        // start moving right to read the entire number, stopping at a . or if we hit the right end of the grid
        while (y < input[x].length && input[x][y].isDigit()) {
            number.append(input[x][y])
            visited.add(x to y)
            y++
        }
        return number.toString().toInt()
    }

    private companion object {
        private val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
    }
}

fun main() {
    val daytest = Day3("day3_test.txt")
    println("part1 test: ${daytest.part1()}")
    println("part2 test: ${daytest.part2()}\n")

    val day = Day3("day3.txt")
    println("part1: ${day.part1()}")
    println("part2: ${day.part2()}")
}