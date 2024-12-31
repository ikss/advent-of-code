class Day3(title: String) : DayX(title) {
    private val wire1commands = input[0].split(',')
    private val wire2commands = input[1].split(',')

    override fun part1(): Int {
        val grid = HashMap<Point, Int>()
        var curr = 0 to 0
        for (command1 in wire1commands) {
            val dir = Direction.fromChar(command1[0])
            val steps = command1.substring(1).toInt()
            repeat(steps) {
                curr += dir
                grid[curr] = 1
            }
        }

        curr = 0 to 0
        var result = Int.MAX_VALUE
        for (command2 in wire2commands) {
            val dir = Direction.fromChar(command2[0])
            val steps = command2.substring(1).toInt()
            repeat(steps) {
                curr += dir
                if (grid[curr] == 1) {
                    result = minOf(result, curr.manhattanDistance(0 to 0))
                }
            }
        }

        return result
    }

    override fun part2(): Int {
        val grid = HashMap<Point, Int>()
        var curr = 0 to 0
        var currSteps = 0
        for (command1 in wire1commands) {
            val dir = Direction.fromChar(command1[0])
            val steps = command1.substring(1).toInt()
            repeat(steps) {
                curr += dir
                currSteps++
                grid[curr] = currSteps
            }
        }

        curr = 0 to 0
        currSteps = 0
        var result = Int.MAX_VALUE
        for (command2 in wire2commands) {
            val dir = Direction.fromChar(command2[0])
            val steps = command2.substring(1).toInt()
            repeat(steps) {
                curr += dir
                currSteps++
                if (grid.contains(curr)) {
                    result = minOf(result, currSteps + grid[curr]!!)
                }
            }
        }

        return result
    }
}

fun main() {
    val day = Day3("Day 3: Crossed Wires")
    day.solve()
    // Part 1: 280
    // Part 2: 10554
}
