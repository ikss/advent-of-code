import com.google.common.base.Stopwatch

class Day6(title: String) : DayX(title) {
    private val grid = input.toCharGrid()
    private val start = grid.find('^')

    override fun part1(): Any {
        return countPositions()
    }

    private fun countPositions(): Long {
        val visited = HashSet<Point>()
        var curr = start to Direction.UP

        while (true) {
            val (point, direction) = curr
            visited.add(point)

            val next = point + direction.next

            if (next !in grid) {
                break
            }
            curr = if (grid[next] != '#') {
                next to direction
            } else {
                point to direction.getClockwise()
            }
        }
        return visited.size.toLong()
    }

    override fun part2(): Any {
        return countObstructionPositions()
    }

    private fun countObstructionPositions(): Long {
        var result = 0L
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '#' || i to j == start) {
                    continue
                }

                grid[i][j] = '#'

                val visited = HashSet<Pair<Point, Direction>>()
                var curr = start to Direction.UP

                while (true) {
                    val (point, direction) = curr

                    if (visited.contains(point to direction)) {
                        result++
                        break
                    }
                    visited.add(point to direction)

                    val (nextr, nextc) = point + direction.next

                    if (nextr !in grid.indices || nextc !in grid[0].indices) {
                        break
                    }
                    curr = if (grid[nextr][nextc] != '#') {
                        nextr to nextc to direction
                    } else {
                        point to direction.getClockwise()
                    }
                }
                grid[i][j] = '.'
            }
        }

        return result
    }

    fun part2optimized(): Long {
        var result = 0L
        var curr = start to Direction.UP
        val visited = HashSet<Pair<Point, Direction>>()
        val placedObstacles = HashSet<Point>()

        while (true) {
            val (point, direction) = curr
            visited.add(curr)

            val next = point + direction.next

            if (next !in grid) {
                break
            }
            curr = if (grid[next] != '#') {
                if (next !in placedObstacles && start != next) {
                    placedObstacles.add(next)
                    
                    val old = grid[next]
                    grid[next] = '#'
                    
                    if (hasCycle(point, direction.getClockwise(), visited)) {
                        result++
                    }
                    grid[next] = old
                }
                next to direction
            } else {
                point to direction.getClockwise()
            }
        }

        return result
    }

    private fun hasCycle(
        startPoint: Point,
        startDirection: Direction,
        visitedBefore: java.util.HashSet<Pair<Point, Direction>>,
    ): Boolean {
        val visited = HashSet(visitedBefore)
        var curr = startPoint to startDirection

        while (true) {
            val (point, direction) = curr

            if (visited.contains(point to direction)) {
                return true
            }
            visited.add(point to direction)

            val next = point + direction.next

            if (next !in grid) {
                break
            }
            curr = if (grid[next] != '#') {
                next to direction
            } else {
                point to direction.getClockwise()
            }
        }
        return false
    }
}

fun main() {
    val day = Day6("Day 6: Guard Gallivant")
    day.solve()
    // Part 1: 4883
    // Part 2: 1655
    val sw = Stopwatch.createStarted()
    val part2optimized = day.part2optimized()
    println("Part 2 optimized:")
    println("Result: $part2optimized")
    println("Time: $sw")
}
