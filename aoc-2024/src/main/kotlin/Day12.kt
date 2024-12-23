class Day12 : DayX() {
    private val grid = input.toCharGrid()

    override fun part1(): Any {
        var result = 0L

        val visited = Array(grid.size) { BooleanArray(grid[0].size) }
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (visited[r][c]) continue
                result += countCost(r, c, visited)
            }
        }

        return result
    }

    private fun countCost(r: Int, c: Int, visited: Array<BooleanArray>): Long {
        var perimeter = 0L
        var area = 0L
        val queue = java.util.ArrayDeque<Point>()
        visited[r][c] = true
        queue.add(r to c)

        while (queue.isNotEmpty()) {
            val curr = queue.poll()
            area++
            for (dir in fourDirections) {
                val next = curr + dir
                if (next !in grid || grid[next] != grid[r][c]) {
                    perimeter++
                    continue
                }
                if (visited[next.first][next.second]) continue
                visited[next.first][next.second] = true
                queue.offer(next)
            }
        }
        return perimeter * area
    }

    override fun part2(): Any {
        var result = 0L

        val visited = Array(grid.size) { BooleanArray(grid[0].size) }
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (visited[r][c]) continue
                result += countCost2(r, c, visited)
            }
        }

        return result
    }

    private fun countCost2(r: Int, c: Int, visited: Array<BooleanArray>): Long {
        var sides = 0L
        val seenSides = HashSet<Pair<Point, Direction>>()
        var area = 0L
        val queue = java.util.ArrayDeque<Point>()
        visited[r][c] = true
        queue.add(r to c)

        while (queue.isNotEmpty()) {
            val curr = queue.poll()
            area++
            for (dir in Direction.entries) {
                val next = curr + dir
                if (next !in grid || grid[next] != grid[r][c]) {
                    val rotated = dir.getClockwise()
                    val pr = curr + rotated
                    val nx = curr + rotated.getOpposite()
                    if (pr to dir !in seenSides && nx to dir !in seenSides) {
                        sides++
                    }
                    seenSides.add(curr to dir)
                    continue
                }
                if (visited[next.first][next.second]) continue
                visited[next.first][next.second] = true
                queue.offer(next)
            }
        }
        return sides * area
    }
}

fun main() {
    val day = Day12()
    day.solve()
    // Part 1: 1363682
    // Part 2: 787680
}
