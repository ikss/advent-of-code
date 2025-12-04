class Day4(title: String) : DayX(title) {

    override fun part1(): Any {
        val grid = input.toCharGrid()
        var result = 0L

        for (r in grid.indices) {
            for (c in grid[r].indices) {
                val cell = grid[r][c]
                if (cell == '@' && canMove(grid, r, c)) {
                    result += 1
                }
            }
        }

        return result
    }

    private fun canMove(grid: CharGrid, r: Int, c: Int): Boolean {
        var sum = 0
        for ((dr, dc) in allDirections) {
            var newr = r + dr
            var newc = c + dc
            if (newr in grid.indices && newc in grid[newr].indices && grid[newr][newc] == '@') {
                sum += 1
            }
        }
        return sum < 4
    }

    override fun part2(): Any {
        val grid = input.toCharGrid()
        var result = 0L

        var removed: Boolean
        do {
            removed = false
            for (r in grid.indices) {
                for (c in grid[r].indices) {
                    val cell = grid[r][c]
                    if (cell == '@' && canMove(grid, r, c)) {
                        removed = true
                        result += 1
                        grid[r][c] = '.'
                    }
                }
            }
        } while (removed)
        return result
    }
}

fun main() {
    val day = Day4("Day 4: Printing Department")
    day.solve()
    // Example part 1: 13
    // Example part 2: 43
    // Real part 1: 1320
    // Real part 2: 8354
}
