class Day11(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()
    private val emptyRows = findEmptyRows()
    private val emptyCols = findEmptyCols()
    
    fun part1(): Long {
        val expanded = ArrayList<String>()
        val galaxies = LinkedHashSet<Point>()
        for (i in input.indices) {
            if (i in emptyRows) {
                expanded.add(".".repeat(input[0].length + emptyCols.size))
                expanded.add(".".repeat(input[0].length + emptyCols.size))
                continue
            }
            val row = StringBuilder()
            for (j in input[0].indices) {
                if (emptyCols.contains(j)) {
                    row.append('.').append('.')
                } else {
                    row.append(input[i][j])
                }
            }
            expanded.add(row.toString())
        }
        for (i in expanded.indices) {
            for (j in expanded[0].indices) {
                if (expanded[i][j] == '#') galaxies.add(i to j)
            }
        }

        var result = 0L

        for (g1 in 0 until galaxies.size) {
            for (g2 in g1 + 1 until galaxies.size) {
                val (x1, y1) = galaxies.elementAt(g1)
                val (x2, y2) = galaxies.elementAt(g2)
                val path = Math.abs(x1 - x2) + Math.abs(y1 - y2)

                result += path
            }
        }

        return result
    }

    fun part2(): Long {
        val expanded = ArrayList<String>()
        val galaxies = LinkedHashSet<Point>()
        for (i in input.indices) {
            if (i in emptyRows) {
                repeat(1_000_000) {
                    expanded.add(".")
                }
                continue
            }
            val row = StringBuilder()
            for (j in input[0].indices) {
                if (emptyCols.contains(j)) {
                    repeat(1_000_000) {
                        row.append('.')
                    }
                } else {
                    row.append(input[i][j])
                }
            }
            expanded.add(row.toString())
        }
        for (i in expanded.indices) {
            for (j in expanded[i].indices) {
                if (expanded[i][j] == '#') galaxies.add(i to j)
            }
        }

        var result = 0L

        for (g1 in 0 until galaxies.size) {
            for (g2 in g1 + 1 until galaxies.size) {
                val (x1, y1) = galaxies.elementAt(g1)
                val (x2, y2) = galaxies.elementAt(g2)
                val path = Math.abs(x1 - x2) + Math.abs(y1 - y2)

                result += path
            }
        }

        return result
    }

    private fun findEmptyRows(): Set<Int> {
        val emptyRows = HashSet<Int>()
        for (i in input.indices) {
            if (input[i].all { it == '.' }) {
                emptyRows.add(i)
            }
        }
        return emptyRows
    }

    private fun findEmptyCols(): Set<Int> {
        val emptyCols = HashSet<Int>()
        for (j in input[0].indices) {
            var empty = true
            for (i in input.indices) {
                if (input[i][j] != '.') {
                    empty = false
                    break
                }
            }
            if (empty) emptyCols.add(j)
        }
        return emptyCols
    }
}

fun main() {
    val daytest = Day11("day11_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 

    val day = Day11("day11.txt")
    println("part1: ${day.part1()}")             //part1: 
    println("part2: ${day.part2()}")             //part2: 
}