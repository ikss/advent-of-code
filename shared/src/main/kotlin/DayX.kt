import com.google.common.base.Stopwatch

abstract class DayX(val title: String = "") {
    protected val input = readInput()

    protected abstract fun part1(): Any

    protected abstract fun part2(): Any

    private fun runPart1() {
        val sw = Stopwatch.createStarted()
        val result = part1()
        println("Part 1:")
        println(" - Result: $result")
        println(" - Time: $sw")
    }

    private fun runPart2() {
        val sw = Stopwatch.createStarted()
        val result = part2()
        println("Part 2:")
        println(" - Result: $result")
        println(" - Time: $sw")
    }

    fun solve() {
        println(if (title.isEmpty()) this.javaClass.simpleName else title)
        println("-----------------")
        runPart1()
        println("-----------------")
        runPart2()
        println("-----------------")
    }
}
