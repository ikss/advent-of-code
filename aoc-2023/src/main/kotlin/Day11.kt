class Day11(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()
    private val emptyRows = findEmptyRows()
    private val emptyCols = findEmptyCols()
    private val galaxies = findGalaxies()

    private fun findEmptyRows(): Set<Int> {
        val emptyRows = HashSet<Int>()
        for (i in input.indices) {
            if (input[i].all { it == '.' }) {
                emptyRows.add(i)
            }
        }
        return emptyRows
    }

    private fun findEmptyCols(): Set<Int> {
        val emptyCols = HashSet<Int>()
        for (j in input[0].indices) {
            var empty = true
            for (i in input.indices) {
                if (input[i][j] != '.') {
                    empty = false
                    break
                }
            }
            if (empty) emptyCols.add(j)
        }
        return emptyCols
    }

    private fun findGalaxies(): Set<Point> {
        val result = LinkedHashSet<Point>()
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] == '#') result.add(i to j)
            }
        }
        return result
    }

    fun part1(): Long {
        return getDistance(expandFactor = 2)
    }

    fun part2(): Long {
        return getDistance(expandFactor = 1_000_000)
    }

    private fun getDistance(expandFactor: Long): Long {
        var result = 0L

        for (g1 in 0 until galaxies.size) {
            for (g2 in g1 + 1 until galaxies.size) {
                val (x1, y1) = galaxies.elementAt(g1)
                val (x2, y2) = galaxies.elementAt(g2)

                val (minX, maxX) = if (x1 < x2) x1 to x2 else x2 to x1
                val (minY, maxY) = if (y1 < y2) y1 to y2 else y2 to y1

                result += maxX - minX + maxY - minY
                result += emptyCols.count { it in minY..maxY } * (expandFactor - 1)
                result += emptyRows.count { it in minX..maxX } * (expandFactor - 1)
            }
        }

        return result
    }

}

fun main() {
    val daytest = Day11("day11_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 374
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 82000210

    val day = Day11("day11.txt")
    println("part1: ${day.part1()}")             //part1: 9723824
    println("part2: ${day.part2()}")             //part2: 731244261352
}