class Day10 : DayX() {
    private val grid = input.toCharGrid()
    private val starts = grid.findStarts('0')
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
            val nextChar = char + 1

            for (dir in fourDirections) {
                val next = point + dir
                if (next in grid && grid[next] == nextChar) {
                    queue.offer(next to nextChar)
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
