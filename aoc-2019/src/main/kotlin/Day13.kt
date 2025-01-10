import IntcodeComputer.Companion.POISON_PILL
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class Day13(title: String) : DayX(title) {
    private val numbers = input.readAllNumbers().toList()

    override fun part1(): Int {
        val computer = IntcodeComputer(numbers)
        computer.execute()
        val output = computer.output.filter { it != POISON_PILL }

        val board = HashMap<Point, Char>()
        output.chunked(3).forEach {
            val y = it[0].toInt()
            val x = it[1].toInt()
            val tile = it[2].toInt()
            board[Point(x, y)] = when (tile) {
                0 -> ' '
                1 -> '█'
                2 -> '#'
                3 -> '-'
                4 -> 'o'
                else -> throw IllegalArgumentException("Invalid tile: $tile")
            }
        }

        board.draw()

        return board.values.count { it == '#' }
    }

    override fun part2(): Long {
        val newProgram = numbers.toMutableList().also { it[0] = 2 }
        val input = LinkedBlockingQueue<Long>()
        val computer = IntcodeComputer(newProgram, input)

        var score = 0L
        var paddle = 0 to 0
        var ball = 0 to 0
        val board = HashMap<Point, Char>()

        thread { computer.execute() }

        while (true) {
            if (computer.output.isNotEmpty()) {
                val x = computer.output.take()

                if (x == POISON_PILL) {
                    break
                }
                val y = computer.output.take()
                val block = computer.output.take()
                if (x == -1L && y == 0L) {
                    score = block
                } else {
                    val newPoint = y.toInt() to x.toInt()
                    board[newPoint] = when (block) {
                        0L -> ' '
                        1L -> '█'
                        2L -> '#'
                        3L -> '-'
                        4L -> 'o'
                        else -> throw IllegalArgumentException("Invalid tile: $block")
                    }
                    when (block) {
                        3L -> paddle = newPoint
                        4L -> ball = newPoint
                    }
//                    if (block == 3L || block == 4L) {
//                        board.draw()
//                        println("-----------------------------------------")
//                        Thread.sleep(10)
//                    }
                }
            } else if (computer.waitingInput) {
                if (computer.output.isNotEmpty()) continue
                computer.waitingInput = false
                val joystick = when {
                    paddle.second < ball.second -> 1L
                    paddle.second > ball.second -> -1L
                    else -> 0L
                }
                input.put(joystick)
            }
        }
        board.draw()

        return score
    }
}

fun main() {
    val day = Day13("Day 13: Care Package")
    day.solve()
    // Part 1: 236
    // Part 2: 11040
}
