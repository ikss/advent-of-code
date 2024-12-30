class Day15(title: String) : DayX(title) {
    private data class InputLine(
        val sensor: Point,
        val beacon: Point,
    ) {
        val distance = sensor.manhattanDistance(beacon)
    }

    private val inputLines = input.map { line ->
        val (sensorc, sensorr, beaconc, beaconr) = line.readAllNumbers().map(Long::toInt).toList()
        InputLine(sensorr to sensorc, beaconr to beaconc)
    }

    override fun part1(): Any {
        val goal = 2000000
        val taken = HashSet<Point>()

        for (b in inputLines) {
            val (r, c) = b.sensor
            val distance = b.distance

            if (goal !in r - distance..r + distance) continue

            val dis = Math.abs(r - goal)
            for (i in c - (distance - dis)..c + (distance - dis)) {
                val next = goal to i
                if (next == b.beacon || next == b.sensor) continue
                taken.add(next)
            }
        }

        return taken.size.toLong()
    }

    override fun part2(): Any {
        val max = 4000000L
        for (line in inputLines) {
            val surroundings = getSurrondings(line, max)
            for (p in surroundings) {
                if (isValid(p, max)) {
                    return p.second * max + p.first
                }
            }
        }
        return -1
    }

    private fun getSurrondings(inputLine: InputLine, max: Long): ArrayList<Point> {
        val (sr, sc) = inputLine.sensor
        val distance = inputLine.distance + 1
        val result = ArrayList<Point>()

        for (r in sr - distance..sr + distance) {
            if (r !in 0..max) continue

            val delta = distance - Math.abs(sr - r)
            if (sc + delta in 0..max) {
                result.add(r to sc + delta)
            }
            if (sc - delta in 0..max) {
                result.add(r to sc - delta)
            }
        }

        return result
    }

    private fun isValid(p: Point, max: Long): Boolean {
        if (p.first !in 0..max || p.second !in 0..max) return false

        for (s in inputLines) {
            if (s.distance >= s.sensor.manhattanDistance(p)) return false
        }
        return true
    }
}

fun main() {
    val day = Day15("Day 15: Beacon Exclusion Zone")
    day.solve()
    // Part 1: 5108096
    // Part 2: 10553942650264
}
