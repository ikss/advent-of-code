class Day6(title: String) : DayX(title) {
    private val graph = HashMap<String, ArrayList<String>>()
    private val indirectOrbits = HashMap<String, Int>()

    init {
        for (line in input) {
            val (parent, child) = line.split(")")
            graph.computeIfAbsent(parent) { ArrayList() }.add(child)
            graph.computeIfAbsent(child) { ArrayList() }.add(parent)
            if (parent !in indirectOrbits) {
                indirectOrbits[parent] = 0
            }
            indirectOrbits.merge(child, 1, Int::plus)
        }
    }

    override fun part1(): Long {
        val start = indirectOrbits.entries.first { it.value == 0 }.key

        val queue = java.util.ArrayDeque<Pair<String, Int>>()
        val seen = HashSet<String>()
        seen.add(start)
        queue.offer(start to 0)
        var result = 0L
        while (queue.isNotEmpty()) {
            val (curr, count) = queue.poll()

            for (child in graph[curr] ?: continue) {
                if (!seen.add(child)) continue
                result += count + 1
                queue.offer(child to count + 1)
            }

        }
        return result
    }

    override fun part2(): Long {
        val start = "YOU"
        val end = "SAN"

        val queue = java.util.ArrayDeque<Pair<String, Int>>()
        val seen = HashSet<String>()
        seen.add(start)
        queue.offer(start to 0)
        var result = 0L
        while (queue.isNotEmpty()) {
            val (curr, count) = queue.poll()
            if (curr == end) {
                return count - 2L
            }

            for (child in graph[curr] ?: continue) {
                if (!seen.add(child)) continue
                result += count + 1
                queue.offer(child to count + 1)
            }

        }
        return result
    }
}

fun main() {
    val day = Day6("Day 6: Universal Orbit Map")
    day.solve()
    // Part 1: 253104
    // Part 2: 499
}
