class Day6(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val times = input[0].substringAfter(": ").splitToNumbers().toList()
        val distances = input[1].substringAfter(": ").splitToNumbers().toList()

        var result = 1L
        for (i in times.indices) {
            val time = times[i]
            val distance = distances[i]
            var count = 0L

            for (t in 0..time) {
                val travel = (time - t) * t
                if (travel > distance) count++
            }
            result *= count
        }
        return result
    }

    fun part2(): Long {
        val time = input[0].substringAfter(": ").replace(" ", "").toLong()
        val distance = input[1].substringAfter(": ").replace(" ", "").toLong()

        val (left, right) = search(time, distance)
        return right - left + 1
    }

    private fun search(time: Long, distance: Long): Pair<Long, Long> {
        var left = -1L
        var right = -1L
        for (t in 0..time) {
            val travel = (time - t) * t
            if (travel > distance) {
                left = t
                break
            }
        }

        for (t in time downTo 0) {
            val travel = (time - t) * t
            if (travel > distance) {
                right = t
                break
            }
        }

        return left to right
    }

    private companion object {
        fun String.splitToNumbers(): Sequence<Long> = this.splitToSequence(" ").mapNotNull(::mapToLong)
        fun mapToLong(s: String): Long? = s.trim().takeIf { it.isNotBlank() }?.toLong()
    }
}

fun main() {
    val daytest = Day6("day6_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 288
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 71503

    val day = Day6("day6.txt")
    println("part1: ${day.part1()}")             //part1: 1159152
    println("part2: ${day.part2()}")             //part2: 41513103
}