import Direction.DOWN
import Direction.RIGHT
import java.util.*

class Day17(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        return dp(-1, 3)
    }

    fun part2(): Long {
        return dp(4, 10)
    }

    private fun dp(minSteps: Int, maxSteps: Int): Long {
        val grid = Array(input.size) { IntArray(input[0].length) { Int.MAX_VALUE } }
        grid[0][0] = 0
        val visited = HashMap<Track, Int>()
        val queue = ArrayDeque<Pair<Track, Int>>()
        queue.offer(Track(0 to 1, RIGHT, 1) to 0)
        queue.offer(Track(1 to 0, DOWN, 1) to 0)

        while (queue.isNotEmpty()) {
            val (t, s) = queue.poll()
            val (p, d, c) = t
            val (x, y) = p
            if (x < 0 || x >= input.size || y < 0 || y >= input[0].length) {
                continue
            }
            val newSum = s + (input[x][y] - '0')

            val cache = visited[t]
            if (cache != null && cache <= newSum) {
                continue
            }
            visited[t] = newSum

            grid[x][y] = minOf(grid[x][y], newSum)
            if (minSteps != -1 && c < minSteps) {
                queue.offer(Track(p + d, d, c + 1) to newSum)
            } else {
                for (nd in Direction.entries) {
                    if (d.isOpposite(nd)) {
                        continue
                    }
                    if (nd == d) {
                        if (c < maxSteps) {
                            queue.offer(Track(p + d, d, c + 1) to newSum)
                        }
                    } else {
                        queue.offer(Track(p + nd, nd, 1) to newSum)
                    }
                }
            }
        }

        return grid[input.size - 1][input[0].length - 1].toLong()
    }

    private data class Track(
        val point: Point,
        val direction: Direction,
        val count: Int,
    )
}

fun main() {
    val daytest = Day17("day17_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 102
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 94

    val day = Day17("day17.txt")
    println("part1: ${day.part1()}")             //part1: 843
    println("part2: ${day.part2()}")             //part2: 1017
}