class Day17 : DayX() {

    private class State {
        var registerA: Long = 0
        var registerB: Long = 0
        var registerC: Long = 0

        val output = ArrayList<Long>()

        fun apply(command: Long, operand: Long): Int {
            val combo = when (operand) {
                0L -> 0
                1L -> 1
                2L -> 2
                3L -> 3
                4L -> registerA
                5L -> registerB
                6L -> registerC
                else -> throw IllegalArgumentException("Invalid combo: $operand")
            }
            when (command) {
                0L -> {
                    registerA = registerA / Math.pow(2.0, combo.toDouble()).toLong()
                }

                1L -> {
                    registerB = registerB xor operand
                }

                2L -> {
                    registerB = combo % 8
                }

                3L -> {
                    if (registerA != 0L) {
                        return operand.toInt()
                    }
                }

                4L -> {
                    registerB = registerB xor registerC
                }

                5L -> {
                    output.add(combo % 8)
                }

                6L -> {
                    registerB = registerA / Math.pow(2.0, combo.toDouble()).toInt()
                }

                7L -> {
                    registerC = registerA / Math.pow(2.0, combo.toDouble()).toInt()
                }
            }
            return -1
        }
    }

    override fun part1(): Long {
        val commands = input.takeLastWhile { it.isNotBlank() }.joinToString().readAllNumbers().toList()
        val registerA = input[0].readAllNumbers().first()

        println(applyCommands(registerA, commands).joinToString(","))
        return -1
    }

    private fun applyCommands(registerA: Long, commands: List<Long>): List<Long> {
        var i = 0
        val state = State()
        state.registerA = registerA
        while (i < commands.size) {
            val command = commands[i]
            val combo = commands[i + 1]
            val res = state.apply(command, combo)
            if (res == -1) {
                i += 2
            } else {
                i = res
            }
        }
        return state.output
    }

    override fun part2(): Long {
        val commandString = input.takeLastWhile { it.isNotBlank() }.joinToString().substringAfter(": ")
        val commands = commandString.readAllNumbers().toList()

        return findInput(commands, commands)
    }

    private fun findInput(commands: List<Long>, output: List<Long>): Long {
        var regAStart = if (output.size == 1) {
            0
        } else {
            8 * findInput(commands, output.drop(1))
        }

        while (applyCommands(regAStart, commands) != output) {
            regAStart++
        }

        return regAStart
    }


}

fun main() {
    val day = Day17()
    day.solve()
    // Part 1: 4,1,7,6,4,1,0,2,7
    // Part 2: 164279024971453
}
