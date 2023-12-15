class Day16(
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
    val daytest = Day16("day16_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 

    val day = Day16("day16.txt")
    println("part1: ${day.part1()}")             //part1: 
    println("part2: ${day.part2()}")             //part2: 
}