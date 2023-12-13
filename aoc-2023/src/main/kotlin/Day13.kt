class Day13(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val patterns = getPatterns()

        var result = 0L
        for (pattern in patterns) {
            val (reflectionCount, vertical) = findReflections(pattern).first()
            result += if (vertical) reflectionCount else reflectionCount * 100
        }
        return result
    }

    private fun getPatterns(): ArrayList<ArrayList<StringBuilder>> {
        val patterns = ArrayList<ArrayList<StringBuilder>>()

        var curr = ArrayList<StringBuilder>()
        for (line in input) {
            if (line.isBlank()) {
                if (curr.isNotEmpty()) {
                    patterns.add(curr)
                    curr = ArrayList()
                }
                continue
            }
            curr.add(StringBuilder(line))
        }
        if (curr.isNotEmpty()) {
            patterns.add(curr)
        }
        return patterns
    }

    private fun findReflections(pattern: ArrayList<StringBuilder>): List<Pair<Int, Boolean>> {
        val reflections = ArrayList<Pair<Int, Boolean>>()
        reflections.addAll(getVerticalReflections(pattern).map { it to true })
        reflections.addAll(getHorizontalReflections(pattern).map { it to false })
        return reflections
    }

    private fun getHorizontalReflections(pattern: ArrayList<StringBuilder>): List<Int> {
        val result = ArrayList<Int>()
        loop@ for (i in 1 until pattern.size) {
            val bound = minOf(i, pattern.size - i)
            for (row in 0 until bound) {
                if (pattern[i - row - 1].toString() != pattern[i + row].toString()) {
                    continue@loop
                }
            }
            result.add(i)
        }
        return result
    }

    private fun getVerticalReflections(pattern: ArrayList<StringBuilder>): List<Int> {
        val result = ArrayList<Int>()
        val l = pattern[0].length
        loop@ for (i in 1 until l) {
            val bound = minOf(i, pattern[0].length - i)
            for (col in 0 until bound) {
                for (j in 0 until pattern.size) {
                    if (pattern[j][i - col - 1] != pattern[j][i + col]) {
                        continue@loop
                    }
                }
            }
            result.add(i)
        }
        return result
    }

    fun part2(): Long {
        val patterns = getPatterns()

        val oldReflections = HashMap<Int, Pair<Int, Boolean>>()
        for (i in patterns.indices) {
            val pattern = patterns[i]
            val (reflectionCount, vertical) = findReflections(pattern).first()
            oldReflections[i] = reflectionCount to vertical
        }
        var result = 0L

        for (i in patterns.indices) {
            val pattern = patterns[i]
            val (reflectionCount, vertical) = findNewReflection(pattern, oldReflections[i]!!)
            result += if (vertical) reflectionCount else reflectionCount * 100
        }


        return result
    }

    private fun findNewReflection(pattern: ArrayList<StringBuilder>, old: Pair<Int, Boolean>): Pair<Int, Boolean> {
        for (i in pattern.indices) {
            for (j in pattern[0].indices) {
                val char = pattern[i][j]
                pattern[i][j] = if (char == '#') '.' else '#'

                val newReflections = findReflections(pattern).filter { it != old }
                if (newReflections.isNotEmpty()) {
                    return newReflections.first()
                }

                pattern[i][j] = char
            }
        }
        throw IllegalStateException("No new reflection found")
    }
}

fun main() {
    val daytest = Day13("day13_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 405
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 400

    val day = Day13("day13.txt")
    println("part1: ${day.part1()}")             //part1: 34100
    println("part2: ${day.part2()}")             //part2: 33106
}