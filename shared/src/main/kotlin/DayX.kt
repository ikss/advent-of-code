import com.google.common.base.Stopwatch

abstract class DayX(val title: String = "") {
    protected lateinit var input: List<String>

    protected abstract fun part1(): Any

    protected abstract fun part2(): Any

    private fun runPart1() {
        val sw = Stopwatch.createStarted()
        println("Part 1:")
        val result = part1()
        println(" - Result: $result")
        println(" - Time: $sw")
    }

    private fun runPart2() {
        val sw = Stopwatch.createStarted()
        println("Part 2:")
        val result = part2()
        println(" - Result: $result")
        println(" - Time: $sw")
    }

    fun solve() {
        println(title.ifEmpty { this.javaClass.simpleName })
        val exampleInput = readExampleInput()
        if (exampleInput != null) {
            println("=== EXAMPLE INPUT ===")
            input = exampleInput
            solveInternal()
        }
        input = readInput()
        println("=== REAL INPUT ===")
        println("-----------------")
        runPart1()
        println("-----------------")
        runPart2()
        println("-----------------")
    }

    private fun solveInternal() {
        println("-----------------")
        runPart1()
        println("-----------------")
        runPart2()
        println("-----------------")
    }
}
