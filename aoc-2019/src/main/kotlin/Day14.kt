class Day14(title: String) : DayX(title) {
    data class Reaction(val outputAmount: Long, val inputAmounts: Map<String, Long>)

    private val reactions = input.map { 
        val (inputs, output) = it.split(" => ")
        val (outputAmount, outputName) = output.split(" ")
        val inputsList = inputs.split(", ").map { input ->
            val (inputAmount, inputName) = input.split(" ")
            inputName to inputAmount.toLong()
        }
        outputName to Reaction(outputAmount.toLong(), inputsList.toMap())
    }

    override fun part1(): Long {


        return -1L
    }

    override fun part2(): Long {

        return -1L
    }
}

fun main() {
    val day = Day14("Day 14: Space Stoichiometry")
    day.solve()
    // Part 1: 
    // Part 2: 
}
