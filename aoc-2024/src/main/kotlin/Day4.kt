class Day4 {
    private val input = run {
        this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines()
    }

    private val chars = listOf('X', 'M', 'A', 'S')

    fun part1(): Long {
        var result = 0L
        for (r in input.indices) {
            for (c in input[r].indices) {
                if (input[r][c] == chars[0]) {
                    result += found(r, c, input)
                }
            }
        }
        return result
    }
    
    private data class WordPoint(
        val curr: Point,
        val direction: Point,
        val charIndex: Int,
    ) 

    private fun found(r: Int, c: Int, input: List<String>): Int {
        val queue = java.util.ArrayDeque<WordPoint>()
        var result = 0
        for ((dr, dc) in allDirections) {
            val newr = r + dr
            val newc = c + dc
            if (newr in input.indices && newc in input[newr].indices && input[newr][newc] == chars[1]) {
                queue.offer(WordPoint(newr to newc, dr to dc, 1))
            }
        }
        
        while (queue.isNotEmpty()) {
            val wp = queue.poll()
            val (curr, dir, index) = wp
            
            if (index == chars.size - 1) {
                result++
                continue
            }
            val newr = curr.first + dir.first
            val newc = curr.second + dir.second
            if (newr in input.indices && newc in input[0].indices && input[newr][newc] == chars[index + 1]) {
                queue.offer(WordPoint(newr to newc, dir, index + 1))
            }
        }
        return result
    }

    fun part2(): Long {
        var result = 0L
        for (r in 1 until input.size - 1) {
            for (c in 1 until input[r].length - 1) {
                if (input[r][c] == 'A') {
                    if (found2(r, c, input)) {
                        result++
                    }
                }
            }
        }
        return result
    }

    private fun found2(r: Int, c: Int, input: List<String>): Boolean {
        var count = 0
        
        for (dir in diagonalDirections) {
            val (dr, dc) = dir
            val (drinv, dcinv) = dir.invert()
            if (input[r + dr][c + dc] == 'M' && input[r + drinv][c + dcinv] == 'S') {
                count++
            }
        }
        return count == 2
    }
}

fun main() {
    val day = Day4()
    println("part1: " + day.part1()) // 2543
    println("part2: " + day.part2()) // 1930
}
            