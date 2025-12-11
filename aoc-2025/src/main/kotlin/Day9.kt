class Day9(title: String) : DayX(title) {

    private data class Point2D(val x: Long, val y: Long)

    override fun part1(): Any {
        var result = 0L
        val points = input.map { line ->
            val (x, y) = line.split(",")
            Point2D(x.toLong(), y.toLong())
        }

        for (i in 0 until points.size - 1) {
            val (x1, y1) = points[i]
            for (j in i + 1 until points.size) {
                val (x2, y2) = points[j]
                val dx = Math.abs(x1 - x2) + 1
                val dy = Math.abs(y1 - y2) + 1

                val area = dx * dy
                result = maxOf(result, area)
            }
        }

        return result
    }

    override fun part2(): Any {
        val red = input.map { line ->
            val (x, y) = line.split(",")
            Point2D(x.toLong(), y.toLong())
        }

        val green = (0..<red.size).map { i ->
            val (a, b) = red[i]
            val (c, d) = red[(i + 1) % red.size]
            Rectangle(minOf(a, c), minOf(b, d), maxOf(a, c), maxOf(b, d))
        }

        var result = 0L

        for (i in 0 until red.size - 1) {
            for (j in i + 1 until red.size) {
                val (x1, y1) = red[i]
                val (x2, y2) = red[j]

                val dx = Math.abs(x1 - x2) + 1
                val dy = Math.abs(y1 - y2) + 1

                val area = dx * dy
                if (area < result) continue

                val xBottomLeft = minOf(x1, x2)
                val yBottomLeft = minOf(y1, y2)
                val xTopRight = maxOf(x1, x2)
                val yTopRight = maxOf(y1, y2)

                val intersects = green.any { rect ->
                    xBottomLeft < rect.xTopRight && yBottomLeft < rect.yTopRight &&
                    xTopRight > rect.xBottomLeft && yTopRight > rect.yBottomLeft
                }

                if (!intersects) {
                    result = maxOf(result, area)
                }
            }
        }

        return result
    }

    data class Rectangle(val xBottomLeft: Long, val yBottomLeft: Long, val xTopRight: Long, val yTopRight: Long)
}

fun main() {
    val day = Day9("Day 9: Movie Theater")
    day.solve()
    // Example part 1: 50
    // Example part 2: 24
    // Real part 1: 4772103936
    // Real part 2: 1529675217
}
