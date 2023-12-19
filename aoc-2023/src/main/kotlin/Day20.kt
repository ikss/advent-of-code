class Day20(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {

        var result = 0L


        return result
    }

    fun part2(): Long {

        var result = 0L

        return result
    }
}

fun main() {
    val daytest = Day20("day20_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 

    val day = Day20("day20.txt")
    println("part1: ${day.part1()}")             //part1: 
    println("part2: ${day.part2()}")             //part2: 
}