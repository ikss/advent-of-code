import java.util.*

class Day15(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        var sum = 0L
        input.first().split(",").forEach { str ->
            sum += hash(str)
        }
        return sum
    }

    private fun hash(str: String): Long {
        var result = 0L
        str.forEach {
            result += it.toLong()
            result *= 17
            result %= 256
        }
        return result
    }

    fun part2(): Long {
        val operations = input.first().split(",")
        val boxes = Array(256) { LinkedList<Pair<String, Int>>() }

        for (o in operations) {
            if (o.last() == '-') {
                val lens = o.dropLast(1)
                val index = hash(lens).toInt()

                boxes[index].removeIf { it.first == lens }
            } else {
                val (lens, power) = o.split("=")
                val index = hash(lens).toInt()

                val box = boxes[index]
                val existing = box.indexOfFirst { it.first == lens }
                if (existing == -1) {
                    box.add(lens to power.toInt())
                } else {
                    box[existing] = lens to power.toInt()
                }
            }
        }

        var result = 0L

        for (i in boxes.indices) {
            val box = boxes[i]
            for (j in box.indices) {
                val item = box[j]
                val power = (1 + i) * (j + 1) * item.second
                result += power
            }
        }


        return result

    }
}

fun main() {
    val daytest = Day15("day15_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 1320
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 145

    val day = Day15("day15.txt")
    println("part1: ${day.part1()}")             //part1: 519603
    println("part2: ${day.part2()}")             //part2: 244342
}