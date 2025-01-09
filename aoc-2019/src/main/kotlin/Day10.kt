class Day10(title: String) : DayX(title) {
    private val grid = input.toCharGrid()
    private val asteroids = grid.findAll('#')

    override fun part1(): Long {
        var result = 0L

        for (a in asteroids) {
            result = maxOf(result, countAsteroids(a, asteroids))
        }

        return result
    }

    private fun countAsteroids(curr: Point, asteroids: List<Point>): Long {
        var result = 0L
        val seen = HashSet<Point>()

        for (a in asteroids) {
            if (a == curr) continue
            val dr = a.first - curr.first
            val dc = a.second - curr.second
            val gcd = Math.abs(gcd(dr, dc))
            if (!seen.add(dr / gcd to dc / gcd)) continue
            result++
        }
        return result
    }

    override fun part2(): Long {
        var result = 0L
        var startingPoint = 0 to 0

        for (a in asteroids) {
            val count = countAsteroids(a, asteroids)
            if (count > result) {
                startingPoint = a
                result = count
            }
        }

        val sortedAsteroids = asteroids.filter { it != startingPoint }.map { angle(startingPoint, it) to it }
            .sortedWith(Comparator.comparingDouble<Pair<Double, Point>> { it.first }.thenComparingInt { startingPoint.manhattanDistance(it.second) })

        val queue = java.util.ArrayDeque(sortedAsteroids)

        val seen = HashSet<Point>()
        var prevAngle = 0.0
        var currAsteroid = 0

        while (queue.isNotEmpty()) {
            val (angle, asteroid) = queue.poll()

            if (angle < prevAngle) {
                seen.clear()
            }
            prevAngle = angle
            val dr = asteroid.first - startingPoint.first
            val dc = asteroid.second - startingPoint.second
            val gcd = Math.abs(gcd(dr, dc))

            if (!seen.add(dr / gcd to dc / gcd)) {
                queue.offer(angle to asteroid)
                continue
            }
            if (++currAsteroid == 200) {
                return asteroid.second * 100L + asteroid.first
            }
        }

        return -1
    }


    private fun angle(startingPoint: Pair<Int, Int>, it: Pair<Int, Int>): Double {
        val (y1, x1) = startingPoint
        val (y2, x2) = it
        val angle = Math.toDegrees(Math.atan2((y2 - y1).toDouble(), (x2 - x1).toDouble()))
        val adjustedAngle = angle + 90

        return if (adjustedAngle < 0) adjustedAngle + 360 else adjustedAngle
    }
}

fun main() {
    val day = Day10("Day 10: Monitoring Station")
    day.solve()
    // Part 1: 221
    // Part 2: 806
}
