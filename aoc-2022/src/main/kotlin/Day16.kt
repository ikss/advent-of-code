class Day16 : DayX() {
    private data class InputLine(
        val sensor: String,
        val flow: Long,
        val adjacent: List<String>,
    ) {
        var bit = 0L
    }

    private val inputLines = input.map { line ->
        val sensor = line.substringAfter("Valve ").substringBefore(' ')
        val adjacent = line.substringAfter("to valve").substringAfter(' ').split(", ")
        val flow = line.readAllNumbers().first()
        InputLine(sensor, flow, adjacent)
    }

    private val countedDistances = HashMap<Pair<String, String>, Int>()
    private val nodes = HashMap<String, InputLine>()
    private val graph = HashMap<String, ArrayList<String>>()

    init {
        var curBit = 2L
        for (line in inputLines) {
            graph[line.sensor] = ArrayList(line.adjacent)
            if (line.flow > 0) {
                nodes[line.sensor] = line
                if (line.sensor == "AA") {
                    line.bit = 1L
                } else {
                    line.bit = curBit
                    curBit = curBit shl 1
                }
            }
        }

        countAllGraphDistances(graph, countedDistances)
    }

    override fun part1(): Any {
        val start = "AA"
        val currMask = 0L

        val state = State(start, 30, currMask)
        return dfs(state, 0L, nodes)
    }

    private data class State(
        val curr: String,
        val left: Int,
        val bitmask: Long,
    )

    private fun dfs(
        state: State,
        flow: Long,
        nodes: HashMap<String, InputLine>,
    ): Long {
        if (state.left < 2) {
            return flow
        }

        var result = flow
        for ((k, v) in nodes) {
            if (k == state.curr || state.bitmask and v.bit != 0L) {
                continue
            }

            val move2node2 = countedDistances[state.curr to k]!! + 1

            if (move2node2 <= state.left) {
                val timeFlow2 = state.left - move2node2

                val newFlow = flow + (v.flow * timeFlow2)
                val newState = State(k, timeFlow2, state.bitmask or v.bit)

                result = maxOf(result, dfs(newState, newFlow, nodes))
            }
        }

        return result
    }

    override fun part2(): Any {
        var result = 0L
        val count = nodes.size / 2

        val combinations = combinations(nodes.keys.toList(), count)

        for (combination in combinations) {
            val first = combination.toSet()
            val second = nodes.keys - first

            result = maxOf(
                result,
                dfs(State("AA", 26, 1L), 0L, nodes.filter { it.key in first } as HashMap<String, InputLine>) +
                        dfs(State("AA", 26, 1L), 0L, nodes.filter { it.key in second } as HashMap<String, InputLine>),
            )
        }

        return result
    }
}

fun main() {
    val day = Day16()
    day.solve()
    // Part 1: 1991
    // Part 2: 2705
}
