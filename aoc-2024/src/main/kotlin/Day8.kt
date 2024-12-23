class Day8 : DayX() {
    private val antennas = buildMap<Char, MutableList<Point>> {
        for (r in input.indices) {
            for (c in input[r].indices) {
                val ch = input[r][c]
                if (ch != '.') {
                    computeIfAbsent(ch) { ArrayList() }.add(Point(r, c))
                }
            }
        }

    }

    override fun part1(): Any {
        return countNodes(countOnlyFirst = true)
    }

    override fun part2(): Any {
        return countNodes(countOnlyFirst = false)
    }

    private fun countNodes(countOnlyFirst: Boolean): Long {
        val antinodes = HashSet<Point>()

        for ((_, points) in antennas) {
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val p1 = points[i]
                    val p2 = points[j]
                    if (!countOnlyFirst) {
                        antinodes.add(p1)
                        antinodes.add(p2)
                    }
                    val delta = p2 - p1
                    
                    var node1 = p1 - delta
                    while (node1.first in input.indices && node1.second in input[0].indices) {
                        antinodes.add(node1)
                        node1 -= delta
                        if (countOnlyFirst) break
                    }

                    var node2 = p2 + delta
                    while (node2.first in input.indices && node2.second in input[0].indices) {
                        antinodes.add(node2)
                        node2 += delta
                        if (countOnlyFirst) break
                    }
                }
            }
        }
        return antinodes.size.toLong()
    }

}

fun main() {
    val day = Day8()
    day.solve()
    // Part 1: 278
    // Part 2: 1067
}
