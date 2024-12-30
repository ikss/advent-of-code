class Day25(title: String) : DayX(title) {

    override fun part1(): Any {
        val blocks = input.joinToString("\n").split("\n\n").map { it.split("\n") }

        val locks = blocks.filter { it[0].all { it == '#' } }
        val keys = blocks.filter { it[0].all { it == '.' } }
        
        var result = 0L
        
        for (key in keys) {
            for (lock in locks) {
                var intersect = false
                for (ki in 0 until key.size) {
                    if (intersect) break
                    for (j in 0 until key[ki].length) {
                        if (key[ki][j] == '#' && lock[ki][j] == '#') {
                            intersect = true
                            break
                        }
                    }
                }
                if (!intersect) {
                    result++
                }
            }
        }

        return result
    }

    override fun part2(): Any {
        var result = 0L

        return result
    }
}

fun main() {
    val day = Day25("Day 25: Code Chronicle")
    day.solve()
    // Part 1: 3196
    // Part 2: 
}