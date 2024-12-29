import java.util.*

class Day21(title: String) : DayX(title) {

    private data class Monkey(val name: String, val operation: Char) {
        var value: Long? = null
        var leftMonkey: String? = null
        var rightMonkey: String? = null
        var left: Long? = null
        var right: Long? = null
    }

    private val monkeyGraph = HashMap<String, HashSet<String>>()
    private val monkeys = input.map {
        val (name, other) = it.split(": ")
        if (other.toLongOrNull() != null) {
            Monkey(name, 'v').apply {
                value = other.toLong()
            }
        } else {
            val (left, operation, right) = other.split(" ")
            Monkey(name, operation[0]).apply {
                this.leftMonkey = left
                this.rightMonkey = right

                monkeyGraph.computeIfAbsent(left) { hashSetOf() }.add(name)
                monkeyGraph.computeIfAbsent(right) { hashSetOf() }.add(name)
            }
        }
    }.associateBy { it.name }

    override fun part1(): Long {
        val monkeyValues = HashMap<String, Long>()

        monkeys.values.forEach {
            if (it.operation == 'v') {
                monkeyValues[it.name] = it.value!!
            }
        }
        simulate(monkeyValues)

        return monkeyValues["root"]!!
    }

    private fun simulate(monkeyValues: HashMap<String, Long>) {
        val queue = ArrayDeque<String>()
        queue.addAll(monkeyValues.keys)

        while (queue.isNotEmpty()) {
            val curr = queue.poll()
            val value = monkeyValues[curr]!!

            for (neighbour in monkeyGraph[curr] ?: continue) {
                val monkey = monkeys[neighbour]!!
                if (monkey.leftMonkey == curr) {
                    monkey.left = value
                } else {
                    monkey.right = value
                }

                if (monkey.left != null && monkey.right != null) {
                    when (monkey.operation) {
                        '+' -> monkey.value = monkey.left!! + monkey.right!!
                        '-' -> monkey.value = monkey.left!! - monkey.right!!
                        '*' -> monkey.value = monkey.left!! * monkey.right!!
                        '/' -> monkey.value = monkey.left!! / monkey.right!!
                        else -> throw Exception("Invalid operation")
                    }
                    monkeyValues[neighbour] = monkey.value!!
                    queue.add(neighbour)
                }
            }

        }
    }

    override fun part2(): Long {
        // root left should be 99433652936583
        val monkeyValues = HashMap<String, Long>()

        monkeys.values.forEach {
            if (it.operation == 'v') {
                monkeyValues[it.name] = it.value!!
            } else {
                it.left = null
                it.right = null
                it.value = null
            }
        }
        monkeyValues.remove("humn")
        simulate(monkeyValues)
        simulateBackwards(monkeyValues)

        return monkeyValues["humn"]!!
    }


    private fun simulateBackwards(monkeyValues: HashMap<String, Long>) {
        val queue = ArrayDeque<Pair<String, Long>>()
        val rootMonkey = monkeys["root"]!!
        if (rootMonkey.left == null) {
            queue.add(rootMonkey.leftMonkey!! to rootMonkey.right!!)
        } else {
            queue.add(rootMonkey.rightMonkey!! to rootMonkey.left!!)
        }

        while (queue.isNotEmpty()) {
            val (currMon, currVal) = queue.poll()
            if (currMon == "humn") {
                monkeyValues["humn"] = currVal
                break
            }

            val monkey = monkeys[currMon]!!
            val currOp = monkey.operation
            val filledLeft = monkey.left != null
            val nextMonkey = if (filledLeft) monkey.rightMonkey!! else monkey.leftMonkey!!
            val filledVal = if (filledLeft) monkey.left!! else monkey.right!!

            when (currOp) {
                '+' -> queue.add(nextMonkey to currVal - filledVal)
                '-' -> queue.add(nextMonkey to if (filledLeft) filledVal - currVal else currVal + filledVal)
                '*' -> queue.add(nextMonkey to currVal / filledVal)
                '/' -> queue.add(nextMonkey to if (filledLeft) filledVal / currVal else currVal * filledVal)
                else -> throw Exception("Invalid operation")
            }
        }
    }

}

fun main() {
    val day = Day21("Day 21: Monkey Math")
    day.solve()
    // Part 1: 364367103397416
    // Part 2: 3782852515583
}
            