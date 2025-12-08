class Day7(title: String) : DayX(title) {

    override fun part1(): Any {
        var result = 0L

        val start = input.first().indexOf('S')

        var beams = setOf(start)

        for (i in input.indices) {
            if (i == 0) continue

            val line = input[i].withIndex().filter { it.value == '^' }.map { it.index }.toSet()
            val newBeams = HashSet<Int>()

            for (beam in beams) {
                if (beam in line) {
                    newBeams.add(beam - 1)
                    newBeams.add(beam + 1)
                    result++
                } else {
                    newBeams.add(beam)
                }
            }

            beams = newBeams
        }

        return result
    }

    override fun part2(): Any {
        val start = input.first().indexOf('S')

        var beams = hashMapOf(start to 1L)

        for (i in input.indices) {
            if (i == 0) continue

            val line = input[i].withIndex().filter { it.value == '^' }.map { it.index }.toSet()
            val newBeams = HashMap<Int, Long>()
            for ((index, timelines) in beams) {

                if (index in line) {
                    newBeams[index - 1] = (newBeams[index - 1] ?: 0L) + timelines
                    newBeams[index + 1] = (newBeams[index + 1] ?: 0L) + timelines
                } else {
                    newBeams[index] = (newBeams[index] ?: 0L) + timelines
                }
            }
            beams = newBeams
        }

        return beams.values.sum()
    }
}

fun main() {
    val day = Day7("Day 7: Laboratories")
    day.solve()
    // Example part 1: 21
    // Example part 2: 40
    // Real part 1: 1590
    // Real part 2: 20571740188555
}
