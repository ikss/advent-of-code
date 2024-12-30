class Day23(title: String) : DayX(title) {
    private data class Elf(var point: Point) {
        var consideredMove: Point? = null
    }

    private data class Move(val main: Direction, val free: List<Direction>)

    private val moves = listOf(
        Move(Direction.UP, listOf(Direction.LEFT, Direction.RIGHT)),
        Move(Direction.DOWN, listOf(Direction.LEFT, Direction.RIGHT)),
        Move(Direction.LEFT, listOf(Direction.UP, Direction.DOWN)),
        Move(Direction.RIGHT, listOf(Direction.UP, Direction.DOWN))
    )
    private val grid = input.toCharGrid()

    override fun part1(): Long {
        var elves = grid.findAll('#').associateWith { Elf(it) }

        var currMove = 0
        for (i in 0 until 10) {
            elves.values.forEach { v -> considerMove(v, currMove, elves) }
            val currConsiders = elves.values.groupBy { it.consideredMove  }.mapValues { it.value.size }
            elves = elves.values.associateBy { elf ->
                if (elf.consideredMove != null && currConsiders[elf.consideredMove] == 1) {
                    elf.point = elf.consideredMove!!
                }
                elf.consideredMove = null
                elf.point
            }
            currMove++
            currMove %= moves.size
        }
        return getFreeCells(elves)
    }

    private fun getFreeCells(elves: Map<Point, Elf>): Long {
        var minRow = Int.MAX_VALUE
        var maxRow = Int.MIN_VALUE
        var minCol = Int.MAX_VALUE
        var maxCol = Int.MIN_VALUE

        for (v in elves.values) {
            val (r, c) = v.point
            minRow = minOf(minRow, r)
            maxRow = maxOf(maxRow, r)
            minCol = minOf(minCol, c)
            maxCol = maxOf(maxCol, c)
        }
        var result = 0L
        for (r in minRow..maxRow) {
            for (c in minCol..maxCol) {
                if (elves[r to c] == null) {
                    result++
                }
            }
        }
        return result
    }

    private fun considerMove(elf: Elf, currMove: Int, elves: Map<Point, Elf>) {
        if (allDirections.map { elf.point + it }.all { it !in elves }) {
            return
        }

        for (i in 0 until 4) {
            val move = moves[(currMove + i) % moves.size]
            val shouldBeFree = listOf(elf.point + move.main) + move.free.map { elf.point + move.main + it }
            if (shouldBeFree.all { it !in elves }) {
                elf.consideredMove = elf.point + move.main
                break
            }
        }

    }

    override fun part2(): Int {
        var elves = grid.findAll('#').associateWith { Elf(it) }

        var currMove = 0
        while (true) {
            var moved = false
            elves.values.forEach { v -> considerMove(v, currMove, elves) }
            val currConsiders = elves.values.groupBy { it.consideredMove }.mapValues { it.value.size }
            elves = elves.values.associateBy { elf ->
                if (elf.consideredMove != null && currConsiders[elf.consideredMove] == 1) {
                    elf.point = elf.consideredMove!!
                    moved = true
                }
                elf.consideredMove = null
                elf.point
            }
            currMove++
            if (!moved) {
                break
            }
        }
        return currMove
    }
}

fun main() {
    val day = Day23("Day 23: Unstable Diffusion")
    day.solve()
    // Part 1: 4336
    // Part 2: 1005
}
