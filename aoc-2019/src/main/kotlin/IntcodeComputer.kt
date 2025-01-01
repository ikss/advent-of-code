class IntcodeComputer(
    val codes: MutableList<Long>,
    val input: Int = 0,
) {

    fun execute(): Long {
        var output = 0L

        var curr = 0
        while (curr < codes.size) {
            val (opcode, modes) = parseInstruction(codes[curr].toInt())
            if (opcode == 99) break

            when (opcode) {
                1 -> {
                    val op1 = readParam(0, curr, modes, codes)
                    val op2 = readParam(1, curr, modes, codes)
                    codes[codes[curr + 3].toInt()] = op1 + op2
                    curr += 4
                }

                2 -> {
                    val op1 = readParam(0, curr, modes, codes)
                    val op2 = readParam(1, curr, modes, codes)
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
                    val op1 = readParam(0, curr, modes, codes)
                    val op2 = readParam(1, curr, modes, codes)
                    if (op1 != 0L) {
                        curr = op2.toInt()
                    } else {
                        curr += 3
                    }
                }

                6 -> {
                    val op1 = readParam(0, curr, modes, codes)
                    val op2 = readParam(1, curr, modes, codes)
                    if (op1 == 0L) {
                        curr = op2.toInt()
                    } else {
                        curr += 3
                    }
                }

                7 -> {
                    val op1 = readParam(0, curr, modes, codes)
                    val op2 = readParam(1, curr, modes, codes)
                    codes[codes[curr + 3].toInt()] = if (op1 < op2) 1 else 0
                    curr += 4
                }

                8 -> {
                    val op1 = readParam(0, curr, modes, codes)
                    val op2 = readParam(1, curr, modes, codes)
                    codes[codes[curr + 3].toInt()] = if (op1 == op2) 1 else 0
                    curr += 4
                }
            }
        }

        return output
    }

    private fun readParam(index: Int, curr: Int, modes: List<Boolean>, codes: List<Long>): Long =
        if (modes.getOrNull(index) == true) codes[curr + index + 1] else codes[codes[curr + index + 1].toInt()]

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

}