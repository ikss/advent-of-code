class Day8(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val instructions = input.first()

        val map = HashMap<String, Pair<String, String>>()

        input.asSequence().drop(2).forEach {
            val (key, value) = it.split(" = ")
            val (l, r) = value.substring(1, value.length - 1).split(", ")
            map[key] = l to r
        }
        var curr = "AAA"
        var currDir = 0
        var result = 0L

        while (curr != "ZZZ") {
            val (l, r) = map[curr]!!
            val direction = instructions[currDir++]
            if (currDir == instructions.length) currDir = 0

            curr = if (direction == 'L') l else r
            result++
        }
        return result
    }

    fun part2(): Long {
        val instructions = input.first()

        val map = HashMap<String, Pair<String, String>>()
        val startingPoints = ArrayList<String>()

        input.asSequence().drop(2).forEach {
            val (key, value) = it.split(" = ")
            val (l, r) = value.substring(1, value.length - 1).split(", ")
            if (key.endsWith('A')) startingPoints.add(key)
            map[key] = l to r
        }

        val trips = startingPoints.map { startingPoint ->
            var curr = startingPoint
            var count = 0L
            while (!curr.endsWith('Z')) {
                val direction = instructions[count++.toInt() % instructions.length]
                
                val (l, r) = map[curr]!!
                curr = if (direction == 'L') l else r
                if (curr.endsWith('Z')) break
            }
            count
        }

        return trips.lcm()
    }
}

fun main() {
    val daytest11 = Day8("day8_test1.txt")
    println("part1 test1: ${daytest11.part1()}")    //part1 test1: 2
    val daytest12 = Day8("day8_test2.txt")
    println("part1 test2: ${daytest12.part1()}")    //part1 test2: 6

    val daytest21 = Day8("day8_test3.txt")
    println("part2 test1: ${daytest21.part2()}\n")   //part2 test1: 6

    val day = Day8("day8.txt")
    println("part1: ${day.part1()}")                //part1: 16343
    println("part2: ${day.part2()}")                //part2: 15299095336639
}