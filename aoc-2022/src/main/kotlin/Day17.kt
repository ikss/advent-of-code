class Day17 : DayX() {
    private val movements = input[0]

    private val rocks = listOf(
        listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3),
        listOf(0 to 1, 1 to 0, 1 to 1, 1 to 2, 2 to 1),
        listOf(0 to 0, 0 to 1, 0 to 2, 1 to 2, 2 to 2),
        listOf(0 to 0, 1 to 0, 2 to 0, 3 to 0),
        listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1),
    )

    override fun part1(): Any {
        var currMove = 0
        var maxLevel = 0
        val grid = HashMap<Point, Char>()

        for (i in 0 until 2022) {
            var rock = rocks[i % rocks.size].map { (r, c) -> r + maxLevel + 4 to c + 2 }
            while (true) {
                val side = Direction.fromChar(movements[currMove])
                if (rock.all {
                        val (nr, nc) = it + side
                        nr > 0 && nc in 0..6 && grid[nr to nc] != '#'
                    }
                ) {
                    rock = rock.map { it + side }
                }
                currMove = (currMove + 1) % movements.length
                val up = Direction.UP
                if (rock.all {
                        val (nr, nc) = it + up
                        nr > 0 && nc in 0..6 && grid[nr to nc] != '#'
                    }
                ) {
                    rock = rock.map { it + up }
                } else {
                    for ((r, c) in rock) {
                        grid[r to c] = '#'
                    }
                    break
                }
            }
            maxLevel = maxOf(maxLevel, rock.maxOf { it.first })
        }
        return maxLevel
    }
    
    private data class State(
        val row: String,
        val movement: Int,
        val rock: Int,
    )

    override fun part2(): Any {
        var currMove = 0
        var maxLevel = 0L
        val grid = HashMap<Pair<Long, Int>, Char>()
        
        val cycleState = HashMap<State, Long>()
        val heightState = HashMap<State, Long>()
        for (i in 0 until 1000000L) {
            val rockIndex = (i % rocks.size).toInt()

            val level = (0..6).map { grid[maxLevel to it] }.map { it ?: '.' }.joinToString("")
            val currState = State(level, currMove, rockIndex)
            if (currState in cycleState) {
                val cycleLength = i - cycleState[currState]!!
                val cycleHeight = maxLevel - heightState[currState]!!
                println("Found cycle at $i with state $currState, cycle length: $cycleLength, cycle height: $cycleHeight")
                
                if ((1000000000000L - i) % cycleLength == 0L) {
                    return maxLevel + (1000000000000L - i) / cycleLength * cycleHeight
                }
            }
            cycleState[currState] = i
            heightState[currState] = maxLevel
            
            var rock = rocks[rockIndex].map { (r, c) -> r + maxLevel + 4 to c + 2 }
            while (true) {
                val side = Direction.fromChar(movements[currMove])
                if (rock.all {
                        val nr = it.first + side.next.first
                        val nc = it.second + side.next.second
                        nr > 0 && nc in 0..6 && grid[nr to nc] != '#'
                    }
                ) {
                    rock = rock.map { (r, c) -> r + side.next.first to c + side.next.second }
                }
                currMove = (currMove + 1) % movements.length
                val up = Direction.UP
                if (rock.all {
                        val nr = it.first + up.next.first
                        val nc = it.second + up.next.second
                        nr > 0 && nc in 0..6 && grid[nr to nc] != '#'
                    }
                ) {
                    rock = rock.map { (r, c) -> r + up.next.first to c + up.next.second }
                } else {
                    for ((r, c) in rock) {
                        grid[r to c] = '#'
                    }
                    break
                }
            }
            maxLevel = maxOf(maxLevel, rock.maxOf { it.first })
        }
        throw IllegalStateException("Cycle not found")
    }
}

fun main() {
    val day = Day17()
    day.solve()
    // Part 1: 3127
    // Part 2: 1542941176480
}
