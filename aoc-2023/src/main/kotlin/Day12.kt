class Day12(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    private val memo = HashMap<Triple<Int, Int, Boolean>, Long>()

    fun part1(): Long {
        return solvePart(1)
    }

    fun part2(): Long {
        return solvePart(5)
    }

    private fun solvePart(repeat: Int) = input.asSequence()
        .map { line ->
            memo.clear()
            val (pattern, groupsLine) = line.split(' ')
            val groups = groupsLine.splitToNumbers(',').map { it.toInt() }.toList()

            val newPattern = buildString {
                repeat(repeat) {
                    append(pattern).append('?')
                }
                setLength(length - 1)
            }
            val newGroups = ArrayList<Int>(groups.size * repeat)
            repeat(repeat) {
                newGroups.addAll(groups)
            }

            findArrangements(0, 0, newPattern, newGroups)
        }.sum()

    private fun findArrangements(charIndex: Int, groupIndex: Int, pattern: String, groups: List<Int>): Long {
        memo[Triple(charIndex, groupIndex, false)]?.let { return it }
        if (charIndex == pattern.length) {
            return if (groupIndex == groups.size) {
                1
            } else {
                0
            }
        }

        val result = when (pattern[charIndex]) {
            '.' -> {
                findArrangements(charIndex + 1, groupIndex, pattern, groups)
            }

            '#' -> {
                take(charIndex, groupIndex, pattern, groups)
            }

            '?' -> {
                val skip = findArrangements(charIndex + 1, groupIndex, pattern, groups)
                val take = take(charIndex, groupIndex, pattern, groups)
                return skip + take
            }

            else -> {
                throw IllegalArgumentException("Unknown char ${pattern[charIndex]}")
            }
        }
        memo[Triple(charIndex, groupIndex, false)] = result
        return result
    }

    private fun take(charIndex: Int, groupIndex: Int, pattern: String, groups: List<Int>): Long {
        memo[Triple(charIndex, groupIndex, true)]?.let { return it }
        if (groupIndex >= groups.size) {
            return 0
        }

        val group = groups[groupIndex]
        val charsLeft = pattern.length - charIndex
        if (charsLeft < group) return 0
        for (i in charIndex until charIndex + group) {
            if (pattern[i] == '.') return 0
        }
        if (charsLeft == group) {
            if (groupIndex == groups.size - 1) return 1
            return 0
        }
        if (pattern[charIndex + group] == '#') return 0
        val result = findArrangements(charIndex + group + 1, groupIndex + 1, pattern, groups)

        memo[Triple(charIndex, groupIndex, true)] = result
        return result
    }

}

fun main() {
    val daytest = Day12("day12_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 21
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 

    val day = Day12("day12.txt")
    println("part1: ${day.part1()}")             //part1: 8419
    println("part2: ${day.part2()}")             //part2: 160500973317706
}