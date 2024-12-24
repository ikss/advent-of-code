import java.util.*

class Day24 : DayX() {
    enum class Operation(val operation: (Int, Int) -> Int) {
        AND({ a, b -> a and b }),
        OR({ a, b -> a or b }),
        XOR({ a, b -> a xor b }), ;

        companion object {
            fun byOperation(operation: String): Operation {
                return when (operation) {
                    "AND" -> AND
                    "OR" -> OR
                    "XOR" -> XOR
                    else -> throw IllegalStateException("Unknown operation: $operation")
                }
            }
        }
    }

    data class Rule(
        val left: String,
        val right: String,
        val operation: Operation,
        var output: String,
    ) {
        var leftValue: Int? = null
        var rightValue: Int? = null
    }

    override fun part1(): Any {
        val initial = getInitial()
        val rules = parseRules()
        val rulesMap = HashMap<String, ArrayList<Rule>>()
        rules.forEach {
            rulesMap.computeIfAbsent(it.left) { ArrayList() }.add(it)
            rulesMap.computeIfAbsent(it.right) { ArrayList() }.add(it)
        }
        return calculatez(initial, rulesMap).toLong(2)
    }

    private fun calculatez(initial: List<Pair<String, Int>>, rulesMap: HashMap<String, ArrayList<Rule>>): String {
        val queue = ArrayDeque<Pair<String, Int>>()
        queue.addAll(initial)

        val zops = ArrayList<Pair<String, Int>>()
        val seen = HashSet<String>()
        while (queue.isNotEmpty()) {
            val (gate, value) = queue.poll()
            if (gate[0] == 'z') {
                try {
                    zops.add(gate to value)
                } catch (e: Throwable) {
                    println("hmmm")
                }
                continue
            }
            val rules = rulesMap[gate] ?: continue
            for (rule in rules) {
                if (rule.left == gate) {
                    rule.leftValue = value
                } else {
                    rule.rightValue = value
                }
                if (rule.leftValue != null && rule.rightValue != null && rule.output !in seen) {
                    val result = rule.operation.operation(rule.leftValue!!, rule.rightValue!!)
                    queue.offer(rule.output to result)
                    seen.add(rule.output)
                }
            }
        }
        return zops.sortedByDescending { it.first }.map { it.second }.joinToString("")
    }

    private fun getInitial(): ArrayList<Pair<String, Int>> {
        val initialInput = input.takeWhile { it.isNotBlank() }
        val initial = ArrayList<Pair<String, Int>>()
        for (line in initialInput) {
            val (gate, value) = line.split(": ")
            initial.add(gate to value.toInt())
        }
        return initial
    }

    private fun parseRules(): ArrayList<Rule> {
        val rulesInput = input.takeLastWhile { it.isNotBlank() }
        return rulesInput.mapTo(ArrayList()) {
            val (left, command, right, _, output) = it.split(" ")
            Rule(
                left,
                right,
                Operation.byOperation(command),
                output
            )
        }
    }

    //    const BIT_LENGTH = 45;
//
//// for my input, no carry flags were swapped
//    const incorrect: string[] = [];
//    for (let i = 0; i < BIT_LENGTH; i++) {
//        const id = i.toString().padStart(2, '0');
//        const xor1 = instructions.find(instruction => ((instruction.a === `x${id}` && instruction.b === `y${id}`) || (instruction.a === `y${id}` && instruction.b === `x${id}`)) && instruction.operation === 'XOR');
//        const and1 = instructions.find(instruction => ((instruction.a === `x${id}` && instruction.b === `y${id}`) || (instruction.a === `y${id}` && instruction.b === `x${id}`)) && instruction.operation === 'AND');
//        const z = instructions.find(instruction => instruction.c === `z${id}`);
//
//        if (xor1 === undefined || and1 === undefined || z === undefined) continue;
//
//        // each z must be connected to an XOR
//        if (z.operation !== 'XOR') incorrect.push(z.c);
//
//        // each AND must go to an OR (besides the first case as it starts the carry flag)
//        const or = instructions.find(instruction => instruction.a === and1.c || instruction.b === and1.c);
//        if (or !== undefined && or.operation !== 'OR' && i > 0) incorrect.push(and1.c);
//
//        // the first XOR must to go to XOR or AND
//        const after = instructions.find(instruction => instruction.a === xor1.c || instruction.b === xor1.c);
//        if (after !== undefined && after.operation === 'OR') incorrect.push(xor1.c);
//    }
    override fun part2(): Any {
        val initial = getInitial()
        val rules = parseRules()
        val length = 45

        val incorrect = mutableListOf<String>()
        for (i in 0 until length) {
            val id = i.toString().padStart(2, '0')

            val xor1 = rules.find {
                it.operation == Operation.XOR && ((it.left == "x$id" && it.right == "y$id") || (it.left == "y$id" && it.right == "x$id"))
            }
            val and1 = rules.find {
                it.operation == Operation.AND && ((it.left == "x$id" && it.right == "y$id") || (it.left == "y$id" && it.right == "x$id"))
            }
            val z = rules.find { it.output == "z$id" }

            if (xor1 == null || and1 == null || z == null) continue

            // Each z must be connected to an XOR
            if (z.operation != Operation.XOR) {
                incorrect.add(z.output)
            }

            // Each AND must go to an OR (besides the first case as it starts the carry flag)
            val or = rules.find { it.left == and1.output || it.right == and1.output }
            if (or != null && or.operation != Operation.OR && i > 0) {
                incorrect.add(and1.output)
            }

            // The first XOR must go to XOR or AND
            val after = rules.find { it.left == xor1.output || it.right == xor1.output }
            if (after != null && after.operation == Operation.OR) {
                incorrect.add(xor1.output)
            }
        }

        // Each XOR must be connected to an x, y, or z
        incorrect.addAll(
            rules.filter {
                it.operation == Operation.XOR &&
                        !it.left.startsWith('x') && !it.left.startsWith('y') &&
                        !it.right.startsWith('x') && !it.right.startsWith('y') &&
                        !it.output.startsWith('z')
            }.map { it.output }
        )

        return incorrect.sorted().joinToString(",")
    }
}

fun main() {
    val day = Day24()
    day.solve()
    // Part 1: 57632654722854
    // Part 2: ckj,dbp,fdv,kdf,rpp,z15,z23,z39
}