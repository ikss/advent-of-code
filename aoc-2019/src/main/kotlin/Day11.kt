import IntcodeComputer.Companion.POISON_PILL
import kotlin.concurrent.thread

class Day11(title: String) : DayX(title) {
    private val numbers = input.readAllNumbers().toList()

    override fun part1(): Int {
        val colors = HashMap<Point, Long>()
        val painted = HashSet<Point>()
        paint(colors, painted)

        return painted.size
    }

    private fun paint(colors: HashMap<Point, Long>, painted: HashSet<Point>) {
        var direction = Direction.UP
        val computer = IntcodeComputer(numbers)
        thread { computer.execute() }

        var position = Point(0, 0)

        while (!computer.halted) {
            val currentColor = colors.getOrDefault(position, 0L)
            computer.input.add(currentColor)

            val newColor = computer.output.take()
            if (newColor == POISON_PILL) break
            val rotation = computer.output.take()
            if (rotation == POISON_PILL) break

            if (newColor != currentColor) {
                painted.add(position)
                colors[position] = newColor
            }
            if (rotation == 0L) {
                direction = direction.getCounterClockwise()
            } else {
                direction = direction.getClockwise()
            }
            position += direction
        }
    }

    override fun part2(): Long {
        val colors = HashMap<Point, Long>()
        val painted = HashSet<Point>()
        colors[0 to 0] = 1
        paint(colors, painted)

        val minr = colors.minOf { it.key.first }
        val maxr = colors.maxOf { it.key.first }
        val minc = colors.minOf { it.key.second}
        val maxc = colors.maxOf { it.key.second }
        
        val grid = Array(maxr - minr + 1) { CharArray(maxc - minc + 1) { ' ' } }
        
        for (i in minr..maxr) {
            for (j in minc..maxc) {
                val color = colors.getOrDefault(i to j, 0L)
                grid[i - minr][j - minc] = if (color == 1L) '#' else ' '
            }
        }
        
        for (i in grid.indices) {
            println(grid[i])
        }

        return -1L
    }
}

fun main() {
    val day = Day11("Day 11: Space Police")
    day.solve()
    // Part 1: 1985
    // Part 2: BLCZCJLZ
}
