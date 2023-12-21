import java.util.*

class Day21(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        return traverse(steps = 64, infinite = false)
    }

    fun part2(): Long {
        val steps = input.size / 2
        
        // find coefficients for quadratic polynom
        val c1 = traverse(steps = steps, infinite = true)
        val c2 = traverse(steps = steps + input.size, infinite = true)
        val c3 = traverse(steps = steps + input.size * 2, infinite = true)

        // after finding coefficients Wolfram Alpha was used to find the polynom
        // https://www.wolframalpha.com/input?i=quadratic+fit+calculator&assumption=%7B%22F%22%2C+%22QuadraticFitCalculator%22%2C+%22data3x%22%7D+-%3E%22%7B0%2C+1%2C+2%7D%22&assumption=%7B%22F%22%2C+%22QuadraticFitCalculator%22%2C+%22data3y%22%7D+-%3E%22%7B3889%2C+34504%2C+95591%7D%22
        val polynom = { x: Int -> 3889L + 15379L * x + 15236L * x * x }

        val x = (26501365 - steps) / input.size
        return polynom(x)
    }

    private fun traverse(steps: Int, infinite: Boolean): Long {
        val grid = input.map { StringBuilder(it) }
        var start = -1 to -1
        loop@ for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == 'S') {
                    grid[i][j] = '.'
                    start = i to j
                    break@loop
                }
            }
        }

        val queue = ArrayDeque<Point>()
        queue.offer(start)
        val visited = mutableSetOf<Point>()
        for (step in 1..steps) {
            val size = queue.size
            for (item in 0 until size) {
                val curr = queue.poll()
                for (direction in Direction.entries) {
                    val (nexti, nextj) = curr + direction

                    if (visited.contains(nexti to nextj)) continue

                    if (!infinite) {
                        if (nexti < 0 || nexti >= grid.size || nextj < 0 || nextj >= grid[nexti].length || grid[nexti][nextj] != '.') {
                            continue
                        }
                    } else {
                        val mappedi = adjust(nexti, grid.size)
                        val mappedj = adjust(nextj, grid[0].length)

                        if (grid[mappedi][mappedj] != '.') {
                            continue
                        }
                    }
                    if ((steps % 2 == 0 && step % 2 == 0) || (steps % 2 == 1 && step % 2 == 1)) {
                        visited.add(nexti to nextj)
                    }
                    queue.offer(nexti to nextj)
                }
            }
        }

        return visited.size.toLong()
    }

    private fun adjust(nexti: Int, max: Int): Int {
        val diff = nexti % max
        return if (nexti >= 0) diff else minOf(max + diff, max - 1)
    }

}

fun main() {
    val daytest = Day21("day21_test.txt")
    println("part1 test: ${daytest.part1()}")     //part1 test: 42
    println("part2 test: ${daytest.part2()}")     //part2 test: 

    val day = Day21("day21.txt")
    println("part1: ${day.part1()}")               //part1: 3746
    println("part2: ${day.part2()}")               //part2: 623540829615589
}