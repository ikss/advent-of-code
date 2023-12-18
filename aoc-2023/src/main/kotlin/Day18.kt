import Direction.*

class Day18(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val instructions = input.map {
            it.substringBefore(" (").split(" ").let { it[0].toDir() to it[1].toInt() }
        }
        val (grid, border) = getGrid(instructions)

        val area = getArea(grid)
        return area + border / 2 + 1
    }

    fun part2(): Long {
        val instructions = input.map {
            it.substringAfter("(#").dropLast(1).let { it.last().numberToDir() to it.dropLast(1).toInt(radix = 16) }
        }
        val (grid, border) = getGrid(instructions)

        val area = getArea(grid)
        return area + border / 2 + 1
    }

    private fun getGrid(instructions: List<Pair<Direction, Int>>): Pair<ArrayList<Point>, Long> {
        var prev = 0 to 0
        val grid = ArrayList<Point>()
        var border = 0L
        for ((dir, steps) in instructions) {
            border += steps
            prev = when (dir) {
                RIGHT -> prev.first to prev.second + steps
                LEFT -> prev.first to prev.second - steps
                UP -> prev.first - steps to prev.second
                DOWN -> prev.first + steps to prev.second
            }
            grid.add(prev)
        }
        return grid to border
    }

    private fun getArea(grid: ArrayList<Point>): Long {
        var sum = 0L
        for (i in 0 until grid.size) {
            val (x1, y1) = grid[i]
            val (x2, y2) = grid[(i + 1) % grid.size]
            sum += x1.toLong() * y2 - y1.toLong() * x2
        }
        return Math.abs(sum) / 2
    }

}

private fun String.toDir(): Direction {
    return when (this) {
        "R" -> RIGHT
        "D" -> DOWN
        "L" -> LEFT
        "U" -> UP
        else -> throw IllegalArgumentException("Invalid direction: $this")
    }
}

private fun Char.numberToDir(): Direction {
    return when (this) {
        '0' -> RIGHT
        '1' -> DOWN
        '2' -> LEFT
        '3' -> UP
        else -> throw IllegalArgumentException("Invalid direction: $this")
    }
}

fun main() {
    val daytest = Day18("day18_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 62
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 952408144115

    val day = Day18("day18.txt")
    println("part1: ${day.part1()}")             //part1: 44436
    println("part2: ${day.part2()}")             //part2: 106941819907437
}