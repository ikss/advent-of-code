import java.util.*

class Day14(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val rolled = input.map { StringBuilder(it) }

        for (j in rolled[0].indices) {
            val nextSpace = PriorityQueue<Int>()
            for (i in rolled.indices) {
                val char = rolled[i][j]
                if (char == '#') {
                    nextSpace.clear()
                } else if (char == '.') {
                    nextSpace.offer(i)
                } else if (char == 'O') {
                    if (nextSpace.isNotEmpty()) {
                        rolled[nextSpace.poll()][j] = 'O'
                        rolled[i][j] = '.'
                        nextSpace.offer(i)
                    }
                }
            }
        }

        return getLoad(rolled)
    }

    fun part2(): Long {
        val cycles = 1000000000
        val rolled = input.map { StringBuilder(it) }
        val states = LinkedList<Pair<List<String>, Long>>()
        var cycle = -1 to -1

        for (i in 1..cycles) {
            //up
            for (j in rolled[0].indices) {
                val nextSpace = PriorityQueue<Int>()
                for (i in rolled.indices) {
                    val char = rolled[i][j]
                    if (char == '#') {
                        nextSpace.clear()
                    } else if (char == '.') {
                        nextSpace.offer(i)
                    } else if (char == 'O') {
                        if (nextSpace.isNotEmpty()) {
                            rolled[nextSpace.poll()][j] = 'O'
                            rolled[i][j] = '.'
                            nextSpace.offer(i)
                        }
                    }
                }
            }
            //left
            for (i in rolled.indices) {
                val nextSpace = PriorityQueue<Int>()
                for (j in rolled[0].indices) {
                    val char = rolled[i][j]
                    if (char == '#') {
                        nextSpace.clear()
                    } else if (char == '.') {
                        nextSpace.offer(j)
                    } else if (char == 'O') {
                        if (nextSpace.isNotEmpty()) {
                            rolled[i][nextSpace.poll()] = 'O'
                            rolled[i][j] = '.'
                            nextSpace.offer(j)
                        }
                    }
                }
            }
            //down
            for (j in rolled[0].indices) {
                val nextSpace = PriorityQueue<Int>(Collections.reverseOrder())
                for (i in rolled.size - 1 downTo 0) {
                    val char = rolled[i][j]
                    if (char == '#') {
                        nextSpace.clear()
                    } else if (char == '.') {
                        nextSpace.offer(i)
                    } else if (char == 'O') {
                        if (nextSpace.isNotEmpty()) {
                            rolled[nextSpace.poll()][j] = 'O'
                            rolled[i][j] = '.'
                            nextSpace.offer(i)
                        }
                    }
                }
            }
            //right
            for (i in rolled.indices) {
                val nextSpace = PriorityQueue<Int>(Collections.reverseOrder())
                for (j in rolled[0].length - 1 downTo 0) {
                    val char = rolled[i][j]
                    if (char == '#') {
                        nextSpace.clear()
                    } else if (char == '.') {
                        nextSpace.offer(j)
                    } else if (char == 'O') {
                        if (nextSpace.isNotEmpty()) {
                            rolled[i][nextSpace.poll()] = 'O'
                            rolled[i][j] = '.'
                            nextSpace.offer(j)
                        }
                    }
                }
            }
            val load = getLoad(rolled)
            val mapped = rolled.map { it.toString() }

            val indexOfFirst = states.indexOfFirst { it.first == mapped }
            if (indexOfFirst != -1) {
                cycle = indexOfFirst to i
                println("Cycle found $cycle $load")
                break
            }
            states.offer(mapped to load)
        }

        val cycleLength = cycle.second - cycle.first - 1

        val indexInCycle = (cycles - cycle.first) % cycleLength

        return states[cycle.first + indexInCycle - 1].second
    }

    private fun getLoad(rolled: List<StringBuilder>): Long {
        var result = 0L
        var mult = rolled.size

        for (i in rolled.indices) {
            for (j in rolled[0].indices) {
                if (rolled[i][j] == 'O') result += mult
            }
            mult--
        }
        return result
    }
}

fun main() {
    val daytest = Day14("day14_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 136
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 64

    val day = Day14("day14.txt")
    println("part1: ${day.part1()}")             //part1: 109385
    println("part2: ${day.part2()}")             //part2: 93102
}