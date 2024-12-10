class Day10 : DayX() {
    private val grid = input.toCharGrid()
    private val starts = input.findStarts('0')
    private val trails = starts.map { getTrails(it) }

    override fun part1(): Long {
        return trails.sumOf { it.toSet().size }.toLong()
    }

    override fun part2(): Long {
        return trails.sumOf { it.size }.toLong()
    }

    private fun getTrails(start: Pair<Int, Int>): List<Point> {
        val queue = java.util.ArrayDeque<Pair<Point, Char>>()
        queue.offer(start to '0')
        val result = ArrayList<Point>()

        while (queue.isNotEmpty()) {
            val (point, char) = queue.poll()
            if (char == '9') {
                result.add(point)
                continue
            }
            val newChar = char + 1

            for (dir in fourDirections) {
                val (nr, nc) = point + dir
                if (nr in grid.indices && nc in grid[0].indices && grid[nr][nc] == newChar) {
                    queue.offer(Point(nr, nc) to newChar)
                }
            }
        }

        return result
    }

}

fun main() {
    val day = Day10()
    day.solve()
    // Part 1: 825
    // Part 2: 1805
}
