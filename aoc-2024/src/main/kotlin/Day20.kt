import java.util.*

class Day20 : DayX() {
    private data class State(
        val point: Point,
        val steps: Long,
        val visited: LinkedHashMap<Point, Long>,
    )

    private val grid = input.toCharGrid()
    private val map = TreeMap<Long, Long>()
    private val start = grid.find('S')
    private val end = grid.find('E')
    private val route = getRouteBfs()

    override fun part1(): Long {
        var result = 0L
        val seenCheats = hashSetOf<Pair<Point, Point>>()

        for ((point, steps) in route.visited) {
            for (d in Direction.entries) {
                val newPoint = point + d
                if (newPoint !in grid && grid[newPoint] != '#') {
                    continue
                }
                result += countCheats(point, newPoint, steps + 1, seenCheats, route, 1)
            }
        }

        return result
    }

    private fun countCheats(startPoint: Point, newPoint: Point, steps: Long, seenCheats: MutableSet<Pair<Point, Point>>, route: State, cheatTime: Int): Long {
        if (newPoint !in grid || (grid[newPoint] == '#' && cheatTime == 0)) {
            return 0L
        }
        if (cheatTime == 0) {
            val prev = route.visited[newPoint]!!
            if (prev <= steps || prev - steps < 100 || !seenCheats.add(startPoint to newPoint)) {
                return 0L
            }
//            val old = map.getOrDefault(prev - steps, 0L)
//            map[prev - steps] = old + 1
            return 1L
        }
        var result = 0L
        for (d in Direction.entries) {
            val nextPoint = newPoint + d
            if (nextPoint !in grid) {
                continue
            }
            result += countCheats(startPoint, nextPoint, steps + 1, seenCheats, route, cheatTime - 1)
        }
        return result
    }

    override fun part2(): Long {
        var result = 0L
        val seenCheats = HashSet<Pair<Point, Point>>()
        map.clear()
        val visited = route.visited.entries.toList()
        for (i in visited.indices) {
            val (startPoint, startSteps) = visited[i]
            for (j in i + 1 until visited.size) {
                val (endPoint, endSteps) = visited[j]
                val stepsMade = startPoint.manhattanDistance(endPoint)

                if (stepsMade > 20) {
                    continue
                }
                val stepsSaved = endSteps - startSteps - stepsMade
                if (stepsSaved >= 100 && seenCheats.add(startPoint to endPoint)) {
                    map[stepsSaved] = map.getOrDefault(stepsSaved, 0) + 1
                    result++
                }
            }
        }
//        println(map)
        return result
    }

    private fun getRouteBfs(): State {
        val initial = State(start, 0, linkedMapOf(start to 0L))
        val queue = ArrayDeque<State>()
        queue.offer(initial)

        while (queue.isNotEmpty()) {
            val currState = queue.poll()
            if (currState.point == end) {
                return currState
            }
            for (d in Direction.entries) {
                val newPoint = currState.point + d
                if (newPoint !in grid || newPoint in currState.visited || grid[newPoint] == '#') {
                    continue
                }
                val newSteps = currState.steps + 1
                val newVisited = LinkedHashMap(currState.visited)
                newVisited.put(newPoint, newSteps)
                val newState = State(newPoint, newSteps, newVisited)
                queue.offer(newState)
            }
        }

        throw IllegalStateException("No path found")
    }

}

fun main() {
    val day = Day20()
    day.solve()
    // Part 1: 1395
    // Part 2: 
}