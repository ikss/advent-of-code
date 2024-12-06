class Day6 : DayX() {
    private val grid = input.toCharGrid()
    private val start = input.findStart('^')

    override fun part1(): Long {
        return countPositions()
    }

    private fun countPositions(): Long {
        val visited = HashSet<Point>()
        var curr = start to Direction.UP

        while (true) {
            val (point, direction) = curr
            visited.add(point)

            val (nextr, nextc) = point + direction.next

            if (nextr !in grid.indices || nextc !in grid[nextr].indices) {
                break
            }
            curr = if (grid[nextr][nextc] != '#') {
                nextr to nextc to direction
            } else {
                point to direction.getRight()
            }
        }
        return visited.size.toLong()
    }

    override fun part2(): Long {
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
                        point to direction.getRight()
                    }
                }
                grid[i][j] = '.'
            }
        }

        return result
    }
}

fun main() {
    val day = Day6()
    day.solve()
    // Part 1: 4883
    // Part 2: 1655
}
            