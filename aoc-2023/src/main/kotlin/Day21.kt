import java.util.*

class Day21(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        return traverse(steps = 64, infinite = false)
    }

    fun part2(): Long {
        return traverse(steps = 5000, infinite = true)
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
    println("part2: ${day.part2()}")               //part2: 
}