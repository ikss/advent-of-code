import java.util.concurrent.Executors

class Day7(title: String) : DayX(title) {
    private val codes = input.readAllNumbers().toList()

    override fun part1(): Long {
        val permutations = (0..4).toList().permutations()

        var result = 0L
        for (p in permutations) {
            var out = 0L
            for (i in 0..4) {
                val a = IntcodeComputer(codes, listOf(p[i].toLong(), out).toBlockingQueue(), i)
                a.execute()
                out = a.output.poll()
            }
            result = maxOf(result, out)
        }

        return result
    }

    override fun part2(): Long {
        val permutations = (5..9).toList().permutations()
        var result = 0L
        val executor = Executors.newFixedThreadPool(5)

        for (p in permutations) {
            val computers = List(5) { IntcodeComputer(codes, listOf(p[it].toLong()).toBlockingQueue(), it, debugMode = false) }
            computers[0].input.add(0)
            for (i in 0..4) {
                computers[i].output = computers[(i + 1) % 5].input
            }
            (0..4).map {
                executor.submit {
                    computers[it].execute()
                }
            }.map { it.get() }
            result = maxOf(result, computers[4].output.take())
        }
        executor.shutdownNow()

        return result
    }
}

fun main() {
    val day = Day7("Day 7: Amplification Circuit")
    day.solve()
    // Part 1: 116680
    // Part 2: 89603079
}
