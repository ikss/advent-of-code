import java.util.*
import kotlin.collections.ArrayList

class Day24(title: String) : DayX(title) {
    private data class Blizzard(val point: Point, val direction: Direction)

    private val directions = baseDirections + Point(0, 0) // also add waiting move
    private val grid = input.toCharGrid()
    private val blizzardsState = HashMap<Int, List<Blizzard>>().apply{
        val blizzards = ArrayList<Blizzard>()
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                if (grid[r][c] != '#' && grid[r][c] != '.') {
                    blizzards.add(Blizzard(r to c, Direction.fromChar(grid[r][c])))
                }
            }
        }

        put(0, blizzards)
    }
    private val start = Point(0, grid[0].indexOfFirst { it == '.' })
    private val end = Point(grid.size - 1, grid.last().indexOfFirst { it == '.' })

    override fun part1(): Int {
        return bfs(start, end, 0)
    }

    override fun part2(): Int {
        val toEnd = bfs(start, end, 0)
        val toStart = bfs(end, start, toEnd)
        val toEndAgain = bfs(start, end, toStart)
        return toEndAgain
    }

    private fun bfs(start: Point, end: Point, startMinute: Int): Int {
        val queue = ArrayDeque<Pair<Point, Int>>()
        queue.offer(Pair(start, startMinute))
        val seen = HashSet<Pair<Point, List<Blizzard>>>()

        while (queue.isNotEmpty()) {
            val (current, minutes) = queue.poll()
            if (current == end) {
                return minutes
            }
            val nextMinute = minutes + 1
            val nextBlizzards = blizzardsState.computeIfAbsent(nextMinute) { getNextBlizzardState(blizzardsState[nextMinute - 1]!!) }

            for (dir in directions) {
                val next = current + dir

                if (next !in grid || grid[next] == '#' || nextBlizzards.any { it.point == next } || seen.contains(Pair(next, nextBlizzards))) {
                    continue
                }
                seen.add(Pair(next, nextBlizzards))

                queue.offer(Pair(next, nextMinute))
            }
        }

        throw IllegalStateException("No path found")
    }

    private fun getNextBlizzardState(blizzards: List<Blizzard>): List<Blizzard> {
        val next = ArrayList<Blizzard>(blizzards.size)
        for (blizzard in blizzards) {
            val (point, direction) = blizzard
            var nextPoint = point + direction
            if (grid[nextPoint] == '#') {
                val row = if (nextPoint.first == 0) grid.size - 2 else if (nextPoint.first == grid.size - 1) 1 else nextPoint.first
                val col = if (nextPoint.second == 0) grid[row].size - 2 else if (nextPoint.second == grid[row].size - 1) 1 else nextPoint.second
                nextPoint = Point(row, col)
            }
            next.add(blizzard.copy(point = nextPoint))
        }
        return next
    }
}

fun main() {
    val day = Day24("Day 24: Blizzard Basin")
    day.solve()
    // Part 1: 295
    // Part 2: 851
}
