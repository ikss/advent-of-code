class Day21 : DayX() {
//    +---+---+---+
//    | 7 | 8 | 9 |
//    +---+---+---+
//    | 4 | 5 | 6 |
//    +---+---+---+
//    | 1 | 2 | 3 |
//    +---+---+---+
//        | 0 | A |
//        +---+---+

//        +---+---+
//        | ^ | A |
//    +---+---+---+
//    | < | v | > |
//    +---+---+---+
    
    private val numericMoves = mapOf(
        'A' to listOf('0' to '<', '3' to '^'),
        '0' to listOf('A' to '>', '2' to '^'),
        '1' to listOf('2' to '>', '4' to '^'),
        '2' to listOf('1' to '<', '3' to '>', '5' to '^', '0' to 'v'),
        '3' to listOf('2' to '<', '6' to '^', 'A' to 'v'),
        '4' to listOf('1' to 'v', '7' to '^', '5' to '>'),
        '5' to listOf('2' to 'v', '4' to '<', '8' to '^', '6' to '>'),
        '6' to listOf('3' to 'v', '9' to '^', '5' to '<'),
        '7' to listOf('4' to 'v', '8' to '>'),
        '8' to listOf('5' to 'v', '7' to '<', '9' to '>'),
        '9' to listOf('6' to 'v', '8' to '<'),
    )

    private val directionalMoves = mapOf(
        'A' to listOf('^' to '<', '>' to 'v'),
        '^' to listOf('A' to '>', 'v' to 'v'),
        'v' to listOf('^' to '^', '<' to '<', '>' to '>'),
        '<' to listOf('v' to '>'),
        '>' to listOf('v' to '<', 'A' to '^'),
    )

    override fun part1(): Long {
        return calculatePaths(3)
    }

    private fun calc(from: Char, to: Char, level: Int, max: Int, cache: HashMap<LevelState, Long>): Long {
        if (level == max) return 1
        val state = LevelState(from, to, level)
        cache[state]?.let { return it }

        var result = Long.MAX_VALUE
        for (path in getAllPaths(from, to, level == 0)) {
            var len = 0L
            var cur = 'A'
            for (goal in path) {
                len += calc(cur, goal, level + 1, max, cache)
                cur = goal
            }
            result = minOf(len, result)
        }
        cache[state] = result

        return result
    }

    private data class LevelState(val from: Char, val to: Char, val level: Int)

    private fun calculatePaths(max: Int): Long {
        var result = 0L
        val cache = HashMap<LevelState, Long>()

        for (line in input) {
            var curr = 'A'
            var count = 0L
            for (c in line) {
                count += calc(curr, c, 0, max, cache)
                curr = c
            }
            val num = line.removePrefix("0").removeSuffix("A").toLong()
            result += num * count
        }
        return result
    }

    private data class PathState(val from: Char, val to: Char, val numeric: Boolean)

    private val pathCache = HashMap<PathState, List<String>>()

    private fun getAllPaths(from: Char, to: Char, numeric: Boolean): List<String> {
        val state = PathState(from, to, numeric)
        pathCache[state]?.let { return it }

        val moves = if (numeric) numericMoves else directionalMoves
        if (from == to) return listOf("A")

        val result = ArrayList<String>()
        val queue = java.util.ArrayDeque<Pair<Char, String>>()
        queue.add(from to "")

        while (queue.isNotEmpty()) {
            val (curr, path) = queue.poll()
            if (curr == to) {
                val endPath = path + 'A'
                if (result.isEmpty() || result.last.length == endPath.length) {
                    result.add(endPath)
                } else {
                    break
                }
            }
            for ((next, move) in moves[curr]!!) {
                queue.offer(next to path + move)
            }
        }
        if (result.isEmpty()) {
            throw IllegalStateException("No path found from $from to $to")
        }
        pathCache[state] = result
        return result
    }

    override fun part2(): Long {
        return calculatePaths(26)
    }

}

fun main() {
    val day = Day21()
    day.solve()
    // Part 1: 176870
    // Part 2: 223902935165512
}