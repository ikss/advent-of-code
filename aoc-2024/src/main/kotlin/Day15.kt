class Day15 : DayX() {
    private val moves = input.takeLastWhile { it.isNotBlank() }.joinToString("")

    override fun part1(): Any {
        val grid = input.takeWhile { it.isNotBlank() }.toCharGrid()
        val start = grid.find('@')

        move(start, grid, moves)

        grid.print()

        return count(grid, 'O')
    }

    private fun move(start: Point, grid: CharGrid, moves: String) {
        var curr = start
        for (move in moves) {
            val dir = Direction.fromChar(move)
            val next = curr + dir
            if (grid[next] == '#') {
                continue
            }
            if (grid[next] == 'O') {
                var found = false
                var checkNext = next + dir.next
                while (checkNext in grid && grid[checkNext] != '#') {
                    if (grid[checkNext] == '.') {
                        found = true
                        break
                    }
                    checkNext += dir.next
                }
                if (!found) continue
                grid[checkNext] = 'O'
            }
            grid[next] = '@'
            grid[curr] = '.'
            curr = next
        }
    }

    private fun count(grid: CharGrid, char: Char): Long {
        var result = 0L
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] == char) {
                    result += 100 * row + col
                }
            }
        }
        return result
    }

    override fun part2(): Any {
        val moves = input.takeLastWhile { it.isNotBlank() }.joinToString("")
        val grid = input.takeWhile { it.isNotBlank() }.toCharGrid().map {
            val new = CharArray(it.size * 2) { '.' }
            for (i in it.indices) {
                when (it[i]) {
                    'O' -> {
                        new[i * 2] = '['
                        new[i * 2 + 1] = ']'
                    }

                    '#' -> {
                        new[i * 2] = '#'
                        new[i * 2 + 1] = '#'
                    }

                    '@' -> new[i * 2] = '@'
                }
            }
            new
        }
        val start = grid.find('@')

        move2(start, grid, moves)

        grid.print()
        return count(grid, '[')
    }

    private fun move2(start: Point, grid: CharGrid, moves: String) {
        var curr = start
        var count = 0
        for (move in moves) {
            count++
            val dir = when (move) {
                '^' -> Direction.UP
                'v' -> Direction.DOWN
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> throw IllegalArgumentException("Invalid move: $move")
            }
            val next = curr + dir.next
            if (grid[next] == '#') {
                continue
            } else if (grid[next] == '.') {
                grid[next] = '@'
                grid[curr] = '.'
                curr = next
            } else if (grid[next] == '[' || grid[next] == ']') {
                if (dir == Direction.UP || dir == Direction.DOWN) {
                    val (moved, touched) = checkMove(grid, next, dir)
                    if (!moved) {
                        continue
                    }
                    for (point in touched.sortedBy { if (dir == Direction.UP) it.first else -it.first }) {
                        grid[point + dir] = grid[point]
                        grid[point] = '.'
                    }
                    grid[curr] = '.'
                    grid[next] = '@'
                    curr = next
                    continue
                }
                var found = false
                var checkNext = next + dir.next
                while (checkNext in grid && grid[checkNext] != '#') {
                    if (grid[checkNext] == '.') {
                        found = true
                        break
                    }
                    checkNext += dir.next
                }
                if (!found) continue
                while (checkNext != next) {
                    grid[checkNext] = grid[checkNext - dir.next]
                    checkNext -= dir.next
                }

                grid[curr] = '.'
                grid[next] = '@'
                curr = next
            }
        }
    }

    private fun checkMove(grid: List<CharArray>, next: Point, dir: Direction): Pair<Boolean, Set<Point>> {
        val touched = HashSet<Point>()
        val queue = java.util.ArrayDeque<Point>()
        queue.add(next)

        while (queue.isNotEmpty()) {
            val point = queue.poll()
            val isLeftSide = grid[point] == '['
            val alsoMoved = if (isLeftSide) Direction.RIGHT else Direction.LEFT
            touched.add(point)
            touched.add(point + alsoMoved)

            val nextMoved = point + dir

            if (grid[nextMoved] == '#' || grid[nextMoved + alsoMoved] == '#') {
                return false to emptySet()
            }
            if (grid[nextMoved] == '.' && grid[nextMoved + alsoMoved] == '.') {
                continue
            }

            if (grid[nextMoved] == grid[point]) {
                queue.add(nextMoved)
                continue
            }
            if (grid[nextMoved] != '.') {
                queue.add(nextMoved)
            }
            if (grid[nextMoved + alsoMoved] != '.') {
                queue.add(nextMoved + alsoMoved)
            }
        }
        return true to touched
    }
}

fun main() {
    val day = Day15()
    day.solve()
    // Part 1: 1559280
    // Part 2: 1576353
}
