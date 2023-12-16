import Day16.Direction.*
import java.util.*

class Day16(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        return energized(Point(0, -1) to RIGHT)
    }

    fun part2(): Long {
        val points = ArrayList<Pair<Point, Direction>>()

        for (i in input.indices) {
            points.add(Point(i, -1) to RIGHT)
            points.add(Point(i, input[0].length) to LEFT)
        }
        for (j in input[0].indices) {
            points.add(Point(-1, j) to DOWN)
            points.add(Point(input.size, j) to UP)
        }

        var result = 0L
        for (p in points) {
            result = maxOf(result, energized(p))
        }

        return result
    }

    private fun energized(pair: Pair<Point, Direction>): Long {
        val nextPoints = ArrayDeque<Pair<Point, Direction>>()
        val grid = Array(input.size) { BooleanArray(input[0].length) }

        nextPoints.add(pair)
        val visited = HashSet<Pair<Point, Direction>>()

        while (nextPoints.isNotEmpty()) {
            val (point, direction) = nextPoints.poll()
            if (!visited.add(point to direction)) {
                continue
            }
            val (x, y) = point

            val nextX = x + direction.delta.first
            val nextY = y + direction.delta.second

            if (nextX < 0 || nextY < 0 || nextX >= grid.size || nextY >= grid[0].size) {
                continue
            }
            grid[nextX][nextY] = true

            when (input[nextX][nextY]) {
                '.' -> {
                    nextPoints.add(Point(nextX, nextY) to direction)
                }

                '/' -> {
                    val newDirection = when (direction) {
                        UP -> RIGHT
                        RIGHT -> UP
                        DOWN -> LEFT
                        LEFT -> DOWN
                    }
                    nextPoints.add(Point(nextX, nextY) to newDirection)
                }

                '\\' -> {
                    val newDirection = when (direction) {
                        UP -> LEFT
                        RIGHT -> DOWN
                        DOWN -> RIGHT
                        LEFT -> UP
                    }
                    nextPoints.add(Point(nextX, nextY) to newDirection)
                }

                '-' -> {
                    if (direction == UP || direction == DOWN) {
                        nextPoints.add(Point(nextX, nextY) to LEFT)
                        nextPoints.add(Point(nextX, nextY) to RIGHT)
                    } else {
                        nextPoints.add(Point(nextX, nextY) to direction)
                    }
                }

                '|' -> {
                    if (direction == LEFT || direction == RIGHT) {
                        nextPoints.add(Point(nextX, nextY) to UP)
                        nextPoints.add(Point(nextX, nextY) to DOWN)
                    } else {
                        nextPoints.add(Point(nextX, nextY) to direction)
                    }
                }

                else -> throw IllegalStateException("Unknown char: ${input[nextX][nextY]}")
            }
        }

        var result = 0
        for (line in grid) {
            for (energized in line) {
                if (energized) {
                    result++
                }
            }
        }
        return result.toLong()
    }

    private enum class Direction(val delta: Pair<Int, Int>) {
        UP(-1 to 0),
        RIGHT(0 to 1),
        DOWN(1 to 0),
        LEFT(0 to -1),
    }
}

fun main() {
    val daytest = Day16("day16_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 46
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 51

    val day = Day16("day16.txt")
    println("part1: ${day.part1()}")             //part1: 6622
    println("part2: ${day.part2()}")             //part2: 7130
}