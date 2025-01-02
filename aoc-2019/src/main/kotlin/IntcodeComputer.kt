import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class IntcodeComputer(
    val codes: MutableList<Long>,
    val input: BlockingQueue<Long> = LinkedBlockingQueue(),
    private val id: Int = 0,
    private val debugMode: Boolean = false,
) {
    var halted = false
        private set

    var output: BlockingQueue<Long> = LinkedBlockingQueue()

    fun execute() {
        if (debugMode) {
            println("Computer $id: starting execution")
        }
        var curr = 0
        while (curr < codes.size) {
            if (debugMode) {
                println("Computer $id: executing at position $curr")
            }
            val (opcode, modes) = parseInstruction(codes[curr].toInt())
            if (opcode == 99) {
                halted = true
                break
            }

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
                    if (input.isEmpty() && debugMode) {
                        println("Computer $id: waiting for input")
                    }
                    codes[codes[curr + 1].toInt()] = input.take()
                    curr += 2
                }

                4 -> {
                    output.offer(codes[codes[curr + 1].toInt()])
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