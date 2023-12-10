import Day10.Direction.*

class Day10(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()
    private val startingPoint: Point = findStartingPoint()
    private val loop: Set<Point> = findLoop(startingPoint)

    private fun findStartingPoint(): Point {
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 'S') {
                    return i to j
                }
            }
        }
        throw IllegalArgumentException("No starting point found")
    }

    private fun findLoop(startingPoint: Point): Set<Point> {
        val loop = HashSet<Point>()
        loop.add(startingPoint)
        var next = Direction.entries.firstNotNullOf { checkDirection(startingPoint, it) }

        while (next.first != startingPoint) {
            loop.add(next.first)
            val (point, direction) = next
            next = checkDirection(point.first to point.second, direction)!!
        }
        return loop
    }

    fun part1(): Long {
        return loop.size / 2L
    }

    fun part2(): Long {
        val verticalSet = mutableSetOf('|', 'J', 'L')
        if (checkDirection(startingPoint, N) != null) {
            verticalSet.add('S')
        }
        var result = 0L

        for (i in input.indices) {
            var northfacing = 0
            for (j in input[0].indices) {
                val point = i to j
                if (input[i][j] in verticalSet && point in loop) {
                    northfacing++
                } else if (northfacing % 2 == 1 && point !in loop) {
                    result++
                }
            }
        }
        return result
    }

    private fun checkDirection(point: Point, direction: Direction): Pair<Point, Direction>? {
        val (i, j) = point
        val nextPoint = point + direction.diff

        val nextDir = when {
            direction == N && i > 0 -> {
                val char = input[i - 1][j]
                when (char) {
                    '|' -> N
                    '7' -> W
                    'F' -> E
                    'S' -> END
                    else -> null
                }
            }

            direction == S && i < input.size - 1 -> {
                val char = input[i + 1][j]
                when (char) {
                    '|' -> S
                    'L' -> E
                    'J' -> W
                    'S' -> END
                    else -> null
                }
            }

            direction == W && j > 0 -> {
                val char = input[i][j - 1]
                when (char) {
                    '-' -> W
                    'L' -> N
                    'F' -> S
                    'S' -> END
                    else -> null
                }
            }

            direction == E && j < input[0].length - 1 -> {
                val char = input[i][j + 1]
                when (char) {
                    '-' -> E
                    'J' -> N
                    '7' -> S
                    'S' -> END
                    else -> null
                }
            }
            else -> null
        } ?: return null
        return nextPoint to nextDir
    }

    private enum class Direction(val diff: Point) {
        N(-1 to 0),
        S(1 to 0),
        W(0 to -1),
        E(0 to 1),
        END(0 to 0),
    }
}

fun main() {
    val daytest11 = Day10("day10_part1_test1.txt")
    println("part1 test1: ${daytest11.part1()}")    //part1 test1: 4

    val daytest12 = Day10("day10_part1_test2.txt")
    println("part1 test2: ${daytest12.part1()}")    //part1 test2: 8

    val daytest21 = Day10("day10_part2_test1.txt")
    println("part2 test1: ${daytest21.part2()}")    //part1 test2: 4

    val day = Day10("day10.txt")
    println("part1: ${day.part1()}")                //part1: 6923
    println("part2: ${day.part2()}")                //part2: 529
}