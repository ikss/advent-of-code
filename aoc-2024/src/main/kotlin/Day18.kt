class Day18 : DayX() {


    override fun part1(): Any {
        return calc(71, 1024)
    }

    private fun calc(size: Int, bytes: Int): Long {
        val grid = Array(size) { IntArray(size) { Int.MAX_VALUE } }

        for (i in 0 until bytes) {
            val (x, y) = input[i].readAllNumbers().map { it.toInt() }.toList()
            grid[y][x] = -1
        }

        val start = 0 to 0
        grid[0][0] = 0
        val end = grid.size - 1 to grid[0].size - 1

        val queue = java.util.ArrayDeque<Point>()
        queue.add(start)

        while (queue.isNotEmpty()) {
            val point = queue.poll()
            if (point == end) {
                return grid[point.first][point.second].toLong()
            }
            val currSteps = grid[point.first][point.second]
            val newSteps = currSteps + 1

            for (d in Direction.entries) {
                val newPoint = point + d
                if (newPoint.first !in grid.indices || newPoint.second !in grid[0].indices || grid[newPoint.first][newPoint.second] == -1) {
                    continue
                }

                if (grid[newPoint.first][newPoint.second] <= newSteps) {
                    continue
                }
                grid[newPoint.first][newPoint.second] = newSteps


                queue.add(newPoint)
            }
        }
        return -1L
    }

    override fun part2(): Any {
        var min = 1024
        var max = input.size - 1
        while (min < max) {
            val mid = (max - min) / 2 + min
            val result = calc(71, mid)
            if (result == -1L) {
                max = mid
            } else {
                min = mid + 1
            }
        }
        return "Line $min: ${input[min - 1]}"
    }
}

fun main() {
    val day = Day18()
    day.solve()
    // Part 1: 252
    // Part 2: line 3035, 5,60
}
