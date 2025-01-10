import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class IntcodeComputer(
    codes: List<Long>,
    val input: BlockingQueue<Long> = LinkedBlockingQueue(),
    private val id: Int = 0,
    private val debugMode: Boolean = false,
) {
    val memory = codes.withIndex().associate { it.index.toLong() to it.value }.toMutableMap()
    @Volatile
    var halted = false
        private set

    @Volatile
    var waitingInput = false

    var output: BlockingQueue<Long> = LinkedBlockingQueue()
    var relativeBase = 0L

    fun execute() {
        if (debugMode) {
            println("Computer $id: starting execution")
        }
        var curr = 0L
        while (!halted) {
            val (opcode, modes) = parseInstruction(memory[curr]!!.toInt())

            if (debugMode) {
                println("Computer $id: executing [$opcode] at position $curr")
            }
            when (opcode) {
                1 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    memory[readIndex(2, curr, modes)] = op1 + op2
                    curr += 4
                }

                2 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    memory[readIndex(2, curr, modes)] = op1 * op2
                    curr += 4
                }

                3 -> {
                    if (input.isEmpty()) {
                        if (debugMode) println("Computer $id: waiting for input")
                        waitingInput = true
                    }
                    memory[readIndex(0, curr, modes)] = input.take()
                    waitingInput = false
                    curr += 2
                }

                4 -> {
                    output.offer(readParam(0, curr, modes))
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
                    memory[readIndex(2, curr, modes)] = if (op1 < op2) 1 else 0
                    curr += 4
                }

                8 -> {
                    val op1 = readParam(0, curr, modes)
                    val op2 = readParam(1, curr, modes)
                    memory[readIndex(2, curr, modes)] = if (op1 == op2) 1 else 0
                    curr += 4
                }

                9 -> {
                    relativeBase += readParam(0, curr, modes)
                    curr += 2
                }

                99 -> {
                    halted = true
                    output.offer(POISON_PILL)
                    break
                }

                else -> {
                    throw IllegalArgumentException("Invalid opcode $opcode")
                }
            }
        }
    }

    private fun readParam(index: Int, curr: Long, modes: Map<Int, Int>): Long {
        val value = memory[curr + index + 1]!!

        return when (modes[index]) {
            1 -> value
            2 -> memory.getOrDefault(value + relativeBase, 0)
            else -> memory.getOrDefault(value, 0)
        }
    }

    private fun readIndex(index: Int, curr: Long, modes: Map<Int, Int>): Long {
        val value = memory[curr + index + 1]!!

        return when (modes[index]) {
            2 -> value + relativeBase
            else -> value
        }
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

    companion object {
        const val POISON_PILL = Long.MIN_VALUE
    }
}