class Day22(title: String) : DayX(title) {
    private val instructions = input.takeLastWhile { it.isNotBlank() }.joinToString("")
    private val grid = input.takeWhile { it.isNotBlank() }.toCharGrid().let {
        val maxLength = it.maxOf { it.size }
        it.map { row -> if (row.size < maxLength) row.copyInto(CharArray(maxLength) { ' ' }) else row }
    }
    private val start = findStart()

    override fun part1(): Long {
        val (end, endDir) = traverse(Direction.RIGHT, ::wraparound)

        return (end.first + 1L) * 1000 + (end.second + 1L) * 4 + when (endDir) {
            Direction.RIGHT -> 0
            Direction.DOWN -> 1
            Direction.LEFT -> 2
            Direction.UP -> 3
        }
    }

    private fun traverse(startDir: Direction, wraparoundFunc: (Point, Direction) -> Pair<Point, Direction>): Pair<Point, Direction> {
        var curr = start
        var currDir = startDir

        var currChar = 0

        while (currChar < instructions.length) {
            if (!instructions[currChar].isDigit()) {
                currDir = if (instructions[currChar] == 'R') currDir.getClockwise() else currDir.getCounterClockwise()
                currChar++
                continue
            }
            var num = 0
            while (currChar < instructions.length && instructions[currChar].isDigit()) {
                num = num * 10 + (instructions[currChar] - '0')
                currChar++
            }
            for (step in 1..num) {
                val next = curr + currDir
                if (next !in grid || grid[next] == ' ') {
                    val new = wraparoundFunc(curr, currDir)
                    curr = new.first
                    currDir = new.second
                    continue
                }
                if (grid[next] == '.') {
                    curr = next
                } else {
                    break
                }
            }
        }
        return curr to currDir
    }

    private fun wraparound(curr: Point, currDir: Direction): Pair<Point, Direction> {
        when (currDir) {
            Direction.RIGHT -> {
                for (c in grid[0].indices) {
                    if (grid[curr.first][c] == '.') {
                        return curr.first to c to currDir
                    } else if (grid[curr.first][c] == '#') {
                        break
                    }
                }
            }

            Direction.DOWN -> {
                for (r in grid.indices) {
                    if (grid[r][curr.second] == '.') {
                        return r to curr.second to currDir
                    } else if (grid[r][curr.second] == '#') {
                        break
                    }
                }
            }

            Direction.LEFT -> {
                for (c in grid[0].size - 1 downTo 0) {
                    if (grid[curr.first][c] == '.') {
                        return curr.first to c to currDir
                    } else if (grid[curr.first][c] == '#') {
                        break
                    }
                }
            }

            Direction.UP -> {
                for (r in grid.size - 1 downTo 0) {
                    if (grid[r][curr.second] == '.') {
                        return r to curr.second to currDir
                    } else if (grid[r][curr.second] == '#') {
                        break
                    }
                }
            }
        }
        return curr to currDir
    }

    // Cube structure looks like this: 
    //  -  1  2
    //  -  4  -
    //  6  7  -
    //  9  -  -
    private fun wraparoundCube(curr: Point, currDir: Direction): Pair<Point, Direction> {
        val segment = curr.first / 50 * 3 + curr.second / 50
        val currRow = curr.first % 50
        val currCol = curr.second % 50

        var (newCurr, newDir) = when (currDir to segment) {
            //segment 1
            Direction.LEFT to 1 -> Point(149 - currRow, 0) to Direction.RIGHT      // 1 -> 6
            Direction.UP to 1 -> Point(150 + currCol, 0) to Direction.RIGHT        // 1 -> 9
            //segment 2
            Direction.DOWN to 2 -> Point(50 + currCol, 99) to Direction.LEFT       // 2 -> 4
            Direction.RIGHT to 2 -> Point(149 - currRow, 99) to Direction.LEFT     // 2 -> 7
            Direction.UP to 2 -> Point(199, currCol) to Direction.UP               // 2 -> 9
            //segment 4
            Direction.RIGHT to 4 -> Point(49, 50 + currRow) to Direction.UP        // 4 -> 2
            Direction.LEFT to 4 -> Point(100, 50 - currRow) to Direction.DOWN      // 4 -> 6
            //segment 6
            Direction.LEFT to 6 -> Point(49 - currRow, 50) to Direction.RIGHT      // 6 -> 1
            Direction.UP to 6 -> Point(50 + currCol, 50) to Direction.RIGHT        // 6 -> 4
            //segment 7
            Direction.RIGHT to 7 -> Point(149 - curr.first, 149) to Direction.LEFT // 7 -> 2
            Direction.DOWN to 7 -> Point(100 + curr.second, 49) to Direction.LEFT  // 7 -> 9
            //segment 9
            Direction.LEFT to 9 -> Point(0, curr.first - 100) to Direction.DOWN    // 9 -> 1
            Direction.DOWN to 9 -> Point(0, curr.second + 100) to Direction.DOWN   // 9 -> 2
            Direction.RIGHT to 9 -> Point(149, curr.first - 100) to Direction.UP   // 9 -> 7
            else -> throw IllegalStateException("Invalid state")
        }

        while (grid[newCurr] == ' ') {
            newCurr += newDir
        }

        return if (grid[newCurr] == '.') {
            newCurr to newDir
        } else {
            curr to currDir
        }
    }

    private fun findStart(): Point {
        for (c in grid[0].indices) {
            if (grid[0][c] == '.') {
                return 0 to c
            }
        }
        throw IllegalArgumentException("No start found")
    }

    override fun part2(): Long {
        val (end, endDir) = traverse(Direction.RIGHT, ::wraparoundCube)

        return (end.first + 1L) * 1000 + (end.second + 1L) * 4 + when (endDir) {
            Direction.RIGHT -> 0
            Direction.DOWN -> 1
            Direction.LEFT -> 2
            Direction.UP -> 3
        }
    }
}

fun main() {
    val day = Day22("Day 22: Monkey Map")
    day.solve()
    // Part 1: 13566
    // Part 2: 11451
}
