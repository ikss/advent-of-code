class Day5(title: String) : DayX(title) {

    override fun part1(): Long {
        val codes = input.joinToString(",").readAllNumbers().toMutableList()
        val input = 1

        return executeProgram(codes, input)
    }

    private fun executeProgram(codes: MutableList<Long>, input: Int): Long {
        var output = 0L

        var curr = 0
        while (curr < codes.size) {
            val (opcode, modes) = parseInstruction(codes[curr].toInt())
            if (opcode == 99) break

            when (opcode) {
                1 -> {
                    val op1 = if (modes.getOrNull(0) == true) codes[curr + 1] else codes[codes[curr + 1].toInt()]
                    val op2 = if (modes.getOrNull(1) == true) codes[curr + 2] else codes[codes[curr + 2].toInt()]
                    codes[codes[curr + 3].toInt()] = op1 + op2
                    curr += 4
                }

                2 -> {
                    val op1 = if (modes.getOrNull(0) == true) codes[curr + 1] else codes[codes[curr + 1].toInt()]
                    val op2 = if (modes.getOrNull(1) == true) codes[curr + 2] else codes[codes[curr + 2].toInt()]
                    codes[codes[curr + 3].toInt()] = op1 * op2
                    curr += 4
                }

                3 -> {
                    codes[codes[curr + 1].toInt()] = input.toLong()
                    curr += 2
                }

                4 -> {
                    output = codes[codes[curr + 1].toInt()]
                    curr += 2
                }

                5 -> {
                    val op1 = if (modes.getOrNull(0) == true) codes[curr + 1] else codes[codes[curr + 1].toInt()]
                    val op2 = if (modes.getOrNull(1) == true) codes[curr + 2] else codes[codes[curr + 2].toInt()]
                    if (op1 != 0L) {
                        curr = op2.toInt()
                    } else {
                        curr += 3
                    }
                }

                6 -> {
                    val op1 = if (modes.getOrNull(0) == true) codes[curr + 1] else codes[codes[curr + 1].toInt()]
                    val op2 = if (modes.getOrNull(1) == true) codes[curr + 2] else codes[codes[curr + 2].toInt()]
                    if (op1 == 0L) {
                        curr = op2.toInt()
                    } else {
                        curr += 3
                    }
                }

                7 -> {
                    val op1 = if (modes.getOrNull(0) == true) codes[curr + 1] else codes[codes[curr + 1].toInt()]
                    val op2 = if (modes.getOrNull(1) == true) codes[curr + 2] else codes[codes[curr + 2].toInt()]
                    codes[codes[curr + 3].toInt()] = if (op1 < op2) 1 else 0
                    curr += 4
                }

                8 -> {
                    val op1 = if (modes.getOrNull(0) == true) codes[curr + 1] else codes[codes[curr + 1].toInt()]
                    val op2 = if (modes.getOrNull(1) == true) codes[curr + 2] else codes[codes[curr + 2].toInt()]
                    codes[codes[curr + 3].toInt()] = if (op1 == op2) 1 else 0
                    curr += 4
                }
            }
        }

        return output
    }

    private fun parseInstruction(curr: Int): Pair<Int, List<Boolean>> {
        val opcode = curr % 100
        val modes = ArrayList<Boolean>()
        var curr = curr / 100
        while (curr > 0) {
            modes.add(curr % 10 == 1)
            curr /= 10
        }
        return opcode to modes
    }

    override fun part2(): Long {
        val codes = input.joinToString(",").readAllNumbers().toMutableList()
        val input = 5

        return executeProgram(codes, input)
    }
}

fun main() {
    val day = Day5("Day 5: Sunny with a Chance of Asteroids")
    day.solve()
    // Part 1: 13294380
    // Part 2: 11460760
}
