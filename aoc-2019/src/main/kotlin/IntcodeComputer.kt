import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class IntcodeComputer(
    codes: List<Long>,
    val input: BlockingQueue<Long> = LinkedBlockingQueue(),
    private val id: Int = 0,
    private val debugMode: Boolean = false,
) {
    val memory = codes.withIndex().associate { it.index.toLong() to it.value }.toMutableMap()
    var halted = false
        private set

    var output: BlockingQueue<Long> = LinkedBlockingQueue()

    fun execute() {
        if (debugMode) {
            println("Computer $id: starting execution")
        }
        var curr = 0L
        while (!halted) {
            if (debugMode) {
                println("Computer $id: executing at position $curr")
            }
            val (opcode, modes) = parseInstruction(memory[curr]!!.toInt())

            when (opcode) {
                1 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    memory[memory[curr + 3]!!] = op1 + op2
                    curr += 4
                }

                2 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    memory[memory[curr + 3]!!] = op1 * op2
                    curr += 4
                }

                3 -> {
                    if (input.isEmpty() && debugMode) {
                        println("Computer $id: waiting for input")
                    }
                    memory[memory[curr + 1]!!] = input.take()
                    curr += 2
                }

                4 -> {
                    output.offer(memory[memory[curr + 1]!!]!!)
                    curr += 2
                }

                5 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    if (op1 != 0L) {
                        curr = op2
                    } else {
                        curr += 3
                    }
                }

                6 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    if (op1 == 0L) {
                        curr = op2
                    } else {
                        curr += 3
                    }
                }

                7 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    memory[memory[curr + 3]!!] = if (op1 < op2) 1 else 0
                    curr += 4
                }

                8 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    memory[memory[curr + 3]!!] = if (op1 == op2) 1 else 0
                    curr += 4
                }

                99 -> {
                    halted = true
                    break
                }

                else -> {
                    throw IllegalArgumentException("Invalid opcode $opcode")
                }
            }
        }
    }

    private fun readParam(index: Int, curr: Long, modes: Map<Int, Int>): Long =
        when (modes[index]) {
            1 -> memory[curr + index + 1]!!
            else -> memory[memory[curr + index + 1]!!]!!
        }

    private fun parseInstruction(curr: Int): Pair<Int, Map<Int, Int>> {
        val opcode = curr % 100
        val modes = HashMap<Int, Int>()
        var curr = curr / 100

        var currIndex = 0
        while (curr > 0) {
            modes[currIndex++] = curr % 10
            curr /= 10
        }
        return opcode to modes
    }

}