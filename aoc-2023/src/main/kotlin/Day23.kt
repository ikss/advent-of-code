class Day23(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        return -1
    }

    fun part2(): Long {
        return -1
    }
}

fun main() {
    val daytest = Day23("day23_test.txt")
    println("part1 test: ${daytest.part1()}")     //part1 test: 
    println("part2 test: ${daytest.part2()}")     //part2 test: 

    val day = Day23("day23.txt")
    println("part1: ${day.part1()}")               //part1: 
    println("part2: ${day.part2()}")               //part2: 
}