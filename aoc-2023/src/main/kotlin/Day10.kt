import Day10.Direction.*

private typealias Point = Pair<Int, Int>

class Day10(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        var startingPoint = -1 to -1
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 'S') {
                    startingPoint = i to j
                    break
                }
            }
        }
        var result = 1L
        var next = Direction.entries.firstNotNullOf { checkDirection(startingPoint, it) }

        while (next.first != startingPoint) {
            val (point, direction) = next
            next = checkDirection(point.first to point.second, direction)!!
            result++
        }

        return result / 2
    }

    fun part2(): Long {
        var startingPoint = -1 to -1
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 'S') {
                    startingPoint = i to j
                    break
                }
            }
        }
        val loop = HashSet<Pair<Int, Int>>()
        loop.add(startingPoint)
        var next = Direction.entries.firstNotNullOf { checkDirection(startingPoint, it) }

        while (next.first != startingPoint) {
            loop.add(next.first)
            val (point, direction) = next
            next = checkDirection(point.first to point.second, direction)!!
        }
        
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

        return when {
            direction == N && i > 0 -> {
                val char = input[i - 1][j]
                when (char) {
                    '|' -> i - 1 to j to N
                    '7' -> i - 1 to j to W
                    'F' -> i - 1 to j to E
                    'S' -> i - 1 to j to END
                    else -> null
                }
            }

            direction == S && i < input.size - 1 -> {
                val char = input[i + 1][j]
                when (char) {
                    '|' -> i + 1 to j to S
                    'L' -> i + 1 to j to E
                    'J' -> i + 1 to j to W
                    'S' -> i + 1 to j to END
                    else -> null
                }
            }

            direction == W && j > 0 -> {
                val char = input[i][j - 1]
                when (char) {
                    '-' -> i to j - 1 to W
                    'L' -> i to j - 1 to N
                    'F' -> i to j - 1 to S
                    'S' -> i to j - 1 to END
                    else -> null
                }
            }

            direction == E && j < input[0].length - 1 -> {
                val char = input[i][j + 1]
                when (char) {
                    '-' -> i to j + 1 to E
                    'J' -> i to j + 1 to N
                    '7' -> i to j + 1 to S
                    'S' -> i to j + 1 to END
                    else -> null
                }
            }

            else -> null
        }
    }

    private enum class Direction {
        N,
        S,
        W,
        E,
        END
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