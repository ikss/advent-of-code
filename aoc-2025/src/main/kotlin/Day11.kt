import java.util.*

class Day11(title: String) : DayX(title) {

    override fun part1(): Any {
        var result = 0L

        val graph = HashMap<String, HashSet<String>>()
        for (line in input) {
            val (start, ends) = line.split(": ")

            graph.computeIfAbsent(start) { HashSet() }.addAll(ends.split(" "))
        }

        result = getPath("you", "out", graph)

        return result
    }

    private fun getPath(
        start: String,
        end: String,
        graph: HashMap<String, HashSet<String>>
    ): Long {
        var result = 0L
        val queue = ArrayDeque<MutableSet<String>>()
        queue.offer(mutableSetOf(start))

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            val last = current.last()
            if (last == end) {
                result++
                continue
            }

            val neighbors = graph[last] ?: continue

            for (neighbor in neighbors) {
                if (current.contains(neighbor)) {
                    continue
                }
                val newPath = current.toMutableSet()
                newPath.add(neighbor)
                queue.offer(newPath)
            }
        }
        return result
    }

    override fun part2(): Any {
        var result = 0L

        val graph = HashMap<String, HashSet<String>>()
        for (line in input) {
            val (start, ends) = line.split(": ")

            graph.computeIfAbsent(start) { HashSet() }.addAll(ends.split(" "))
        }
        val reversed = reverseGraph(graph)
        val topological = topologicalSort(graph, reversed)

        val svr2fft = countPaths("svr", "fft", topological, reversed)
        val fft2dac = countPaths("fft", "dac", topological, reversed)
        val dac2out = countPaths("dac", "out", topological, reversed)

        result += svr2fft * fft2dac * dac2out

        val svr2dac = countPaths("svr", "dac", topological, reversed)
        val dac2fft = countPaths("dac", "fft", topological, reversed)
        val fft2out = countPaths("fft", "out", topological, reversed)

        result += svr2dac * dac2fft * fft2out

        return result
    }

    private fun reverseGraph(connections: Map<String, Set<String>>): Map<String, Set<String>> {
        val incoming = HashMap<String, HashSet<String>>()

        for ((node, outputs) in connections) {
            for (outDevice in outputs) {
                incoming.computeIfAbsent(outDevice) { HashSet() }.add(node)
            }
        }

        return incoming
    }

    private fun topologicalSort(
        connections: Map<String, Set<String>>,
        incoming: Map<String, Set<String>>
    ): List<String> {
        val incomingCount = incoming.mapValues { it.value.size }.toMutableMap()
        val topoOrder = ArrayList<String>()

        val queue = ArrayDeque(
            connections.keys.filter { device ->
                (incomingCount[device] ?: 0) == 0
            }
        )

        while (queue.isNotEmpty()) {
            val device = queue.removeFirst()
            topoOrder.add(device)

            for (outDevice in connections[device] ?: emptyList()) {
                incomingCount[outDevice] = (incomingCount[outDevice] ?: 0) - 1
                if (incomingCount[outDevice] == 0) {
                    queue.add(outDevice)
                }
            }
        }

        return topoOrder
    }

    private fun countPaths(
        start: String,
        end: String,
        topoOrder: List<String>,
        incoming: Map<String, Set<String>>
    ): Long {
        val pathCounts = mutableMapOf<String, Long>()
        val startIdx = topoOrder.indexOf(start)
        val endIdx = topoOrder.indexOf(end)

        pathCounts[start] = 1

        for (i in startIdx + 1..endIdx) {
            val device = topoOrder[i]
            pathCounts[device] = incoming[device]
                ?.sumOf { inDevice -> pathCounts[inDevice] ?: 0 }
                ?: 0
        }

        return pathCounts[end] ?: 0
    }
}

fun main() {
    val day = Day11("Day 11: Reactor")
    day.solve()
    // Example part 1: 5
    // Example part 2: 2
    // Real part 1: 708
    // Real part 2: 545394698933400
}
