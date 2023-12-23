import java.util.*

class Day23(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()
    val start = Point(0, input[0].indexOf('.'))
    val end = Point(input.size - 1, input.last().indexOf('.'))

    fun part1(): Long {
        return getPathBfs()
    }

    fun part2(): Long {
        return getPathOptimizedDfs()
    }

    private fun getPathBfs(): Long {
        val queue = ArrayDeque<Track>()
        val path = BitSet(input.size * input[0].length)
        queue.offer(Track(start, path))

        var result = 0

        val map = HashMap<Point, Int>()

        while (queue.isNotEmpty()) {
            val (point, track) = queue.poll()
            val (i, j) = point
            val c = input[i][j]
            val card = track.cardinality()
            if (point == end) {
                result = maxOf(result, card)
                continue
            }
            val cached = map[point]
            if (cached != null && cached > card) {
                continue
            }
            map[point] = card
            if (c in this.slopes) {
                val next = when (c) {
                    '<' -> Point(i, j - 1)
                    '>' -> Point(i, j + 1)
                    '^' -> Point(i - 1, j)
                    'v' -> Point(i + 1, j)
                    else -> error("invalid slope")
                }
                if (next.isValid(input, track)) {
                    track.set(next.first * input[0].length + next.second)
                    queue.offer(Track(next, track))
                }
                continue
            }
            for (dir in Direction.entries) {
                val next = point + dir
                if (next.isValid(input, track)) {
                    val newTrack = track.clone() as BitSet
                    newTrack.set(next.first * input[0].length + next.second)
                    queue.offer(Track(next, newTrack))
                }
            }

        }

        return result.toLong()
    }

    private fun getPathOptimizedDfs(): Long {
        val graph = hashMapOf<Point, ArrayList<Pair<Point, Int>>>(
            start to ArrayList(),
            end to ArrayList(),
        )

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] == '.') {
                    val point = j to i
                    if (point.validNeighbours(input).size > 2) {
                        graph[point] = ArrayList()
                    }
                }
            }
        }

        for (node in graph.keys) {
            val queue = ArrayDeque<Point>()
            queue.offer(node)
            val visited = hashSetOf(node)
            var distance = 0

            while (queue.isNotEmpty()) {
                distance++
                repeat(queue.size) {
                    val c = queue.poll()
                    for (neighbour in c.validNeighbours(input)) {
                        if (neighbour in visited) continue
                        if (neighbour in graph) {
                            graph.getValue(node).add(neighbour to distance)
                        } else {
                            visited.add(neighbour)
                            queue.add(neighbour)
                        }
                    }
                }
            }
        }

        return dfs(start, 0, HashSet(), graph)
    }

    private fun dfs(
        current: Point,
        distance: Long,
        visited: HashSet<Point>,
        graph: HashMap<Point, ArrayList<Pair<Point, Int>>>,
    ): Long {
        if (current == end) {
            return distance
        }

        visited.add(current)
        val max = graph[current]!!
            .filterNot { (neighbour, _) -> visited.contains(neighbour) }
            .maxOfOrNull { (neighbour, weight) ->
                dfs(neighbour, distance + weight, visited, graph)
            }
        visited.remove(current)

        return max ?: 0
    }

    private val slopes = hashSetOf('<', '>', '^', 'v')

    data class Track(val curr: Point, val visited: BitSet)
}

private fun Point.validNeighbours(input: List<String>): List<Point> {
    return Direction.entries
        .map { this + it }
        .filter { (i, j) -> i >= 0 && i < input.size && j >= 0 && j < input[0].length && input[i][j] != '#' }

}

private fun Point.isValid(input: List<String>, track: BitSet): Boolean {
    val (i, j) = this
    return i >= 0 && i < input.size && j >= 0 && j < input[0].length && input[i][j] != '#' && !track.get(i * input[0].length + j)
}

fun main() {
    val daytest = Day23("day23_test.txt")
    println("part1 test: ${daytest.part1()}")     //part1 test: 94
    println("part2 test: ${daytest.part2()}")     //part2 test: 154

    val day = Day23("day23.txt")
    println("part1: ${day.part1()}")               //part1: 2326
    println("part2: ${day.part2()}")               //part2: 6574
}