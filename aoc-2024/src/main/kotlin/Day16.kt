import java.util.*

class Day16(title: String) : DayX(title) {
    private data class State(val point: Point, val dir: Direction, val score: Long, val points: ArrayList<Point> = arrayListOf())

    override fun part1(): Any {
        val grid = input.toCharGrid()
        val start = grid.find('S')
        val end = grid.find('E')

        val queue = PriorityQueue(compareBy(State::score))
        queue.add(State(start, Direction.RIGHT, 0L))

        val visited = HashMap<Pair<Point, Direction>, Long>()
        while (queue.isNotEmpty()) {
            val (point, dir, score) = queue.poll()
            if (point == end) {
                return score
            }
            val seen = visited[point to dir]
            if (seen != null && seen < score) {
                continue
            }
            visited[point to dir] = score

            for (d in Direction.entries) {
                val newPoint = point + d
                if (d != dir.getOpposite() && newPoint in grid && grid[newPoint] != '#') {
                    val newScore = 1 + score + if (d == dir) 0 else 1000
                    queue.add(State(newPoint, d, newScore))
                }
            }
        }

        return -1
    }

    override fun part2(): Any {
        val grid = input.toCharGrid()
        val start = grid.find('S')
        val end = grid.find('E')

        val queue = PriorityQueue(compareBy(State::score))
        queue.add(State(start, Direction.RIGHT, 0, arrayListOf(start)))

        var bestScore = Long.MAX_VALUE
        val bestRoute = HashSet<Point>()
        val visited = HashMap<Pair<Point, Direction>, Long>()

        while (queue.isNotEmpty()) {
            val (point, dir, score, points) = queue.poll()

            if (point == end) {
                if (score > bestScore) {
                    return bestRoute.size.toLong()
                }
                bestScore = score
                bestRoute.addAll(points)
            }

            val seen = visited[point to dir]
            if (seen != null && seen < score) {
                continue
            }
            visited[point to dir] = score

            for (d in Direction.entries) {
                val newPoint = point + d
                if (d != dir.getOpposite() && newPoint in grid && grid[newPoint] != '#') {
                    val newScore = 1 + score + if (d == dir) 0 else 1000
                    val newPoints = ArrayList(points)
                    newPoints.add(newPoint)
                    queue.add(State(newPoint, d, newScore, newPoints))
                }
            }
        }
        return visited.size.toLong()
    }

}

fun main() {
    val day = Day16("Day 16: Reindeer Maze")
    day.solve()
    // Part 1: 123540
    // Part 2: 665
}
