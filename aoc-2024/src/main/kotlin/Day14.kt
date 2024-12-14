class Day14 : DayX() {

    private data class Robot(
        var position: Point,
        val velocity: Point,
    )

    private val maxRow = 103
    private val maxCol = 101

    private fun getRobots(): List<Robot> {
        val regex = Regex("-?\\d+")
        return input.map { line ->
            val (c, r, dc, dr) = regex.findAll(line).map { it.value.toInt() }.toList()
            val position = Point(r, c)
            val velocity = Point(dr, dc)
            Robot(position, velocity)
        }.toList()
    }

    override fun part1(): Long {
        val robots = getRobots()
        for (r in robots) {
            val newPos = getPositionAfter(r, 100)
            r.position = newPos
        }

        val quadrants = IntArray(4)

        for (rob in robots) {
            val (r, c) = rob.position
            if (r < maxRow / 2 && c < maxCol / 2) {
                quadrants[0]++
            } else if (r < maxRow / 2 && c > maxCol / 2) {
                quadrants[1]++
            } else if (r > maxRow / 2 && c < maxCol / 2) {
                quadrants[2]++
            } else if (r > maxRow / 2 && c > maxCol / 2) {
                quadrants[3]++
            }
        }
        var result = 1L
        for (q in quadrants) {
            result *= q
        }

        return result
    }

    override fun part2(): Long {
        val robots = getRobots()
        var result = 0L
        while (true) {
            result++
            val grid = Array(maxRow) { CharArray(maxCol) { ' ' } }
            for (r in robots) {
                val newPos = getPositionAfter(r, 1)
                r.position = newPos
                grid[newPos.first][newPos.second] = '*'
            }

            var found = false
            for (r in 0 until grid.size - 5) {
                for (c in 5 until grid[r].size - 5) {
                    if (
                        grid[r][c] == '*' && grid[r][c + 1] == '*' && grid[r][c + 2] == '*' && grid[r][c + 3] == '*' &&
                        grid[r + 1][c] == '*' && grid[r + 2][c] == '*' && grid[r + 3][c] == '*' && grid[r + 2][c] == '*'
                    ) {
                        found = true
                        break
                    }
                }
                if (found) break
            }
            if (found) break
        }
        return result
    }


    private fun getPositionAfter(r: Robot, time: Int): Pair<Int, Int> {
        var newRow = r.position.first + r.velocity.first * time
        var newCol = r.position.second + r.velocity.second * time

        if (newRow >= maxRow) {
            newRow %= maxRow
        }
        if (newCol >= maxCol) {
            newCol %= maxCol
        }
        if (newRow < 0) {
            while (newRow < 0) {
                newRow += maxRow
            }
        }
        if (newCol < 0) {
            while (newCol < 0) {
                newCol += maxCol
            }
        }
        return Pair(newRow, newCol)
    }
}

fun main() {
    val day = Day14()
    day.solve()
    // Part 1: 229839456
    // Part 2: 7138
}
