class Day19(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val details = getDetails()
        val workflows = getWorkflows()

        var result = 0L

        for (d in details) {
            val valid = validateDetail(d, workflows)
            if (valid) {
                for (param in d.values) {
                    result += param
                }
            }
        }

        return result
    }

    fun part2(): Long {
        val workflows = getWorkflows()
        
        val resultRanges = ArrayList<DetailRange>()
        val queue = java.util.ArrayDeque<Pair<String, DetailRange>>()
        queue.add("in" to DetailRange(hashMapOf('x' to 1..4000, 'm' to 1..4000, 'a' to 1..4000, 's' to 1..4000)))

        while (queue.isNotEmpty()) {
            val (workflow, curr) = queue.poll()
            if (workflow == "R") {
                continue
            } else if (workflow == "A") {
                resultRanges.add(curr)
                continue
            }
            val leftRange = curr.clone()

            for (condition in workflows[workflow]!!) {
                when (condition) {
                    is SimpleRule -> {
                        queue.add(condition.value to leftRange)
                    }

                    is InterimRule -> {
                        val range = leftRange.ranges[condition.param]!!
                        if (condition.cond == '<') {
                            if (range.first >= condition.value) {
                                continue
                            }
                            val r = leftRange.clone()
                            r.ranges[condition.param] = IntRange(range.first, condition.value - 1)
                            queue.offer(condition.next to r)
                            leftRange.ranges[condition.param] = IntRange(condition.value, range.last)
                        } else {
                            if (range.last <= condition.value) {
                                continue
                            }
                            val r = leftRange.clone()
                            r.ranges[condition.param] = IntRange(condition.value + 1, range.last)
                            queue.offer(condition.next to r)
                            leftRange.ranges[condition.param] = IntRange(range.first, condition.value)
                        }
                    }
                }
            }
        }
        var result = 0L

        for (r in resultRanges) {
            if (r.isValid()) {
                result += r.size()
            }
        }

        return result
    }

    private fun validateDetail(
        d: Map<Char, Int>,
        workflows: Map<String, List<Rule>>,
    ): Boolean {
        var workflow = "in"
        while (true) {
            if (workflow == "R") {
                return false
            } else if (workflow == "A") {
                return true
            }

            for (condition in workflows[workflow]!!) {
                when (condition) {
                    is SimpleRule -> {
                        workflow = condition.value
                        break
                    }

                    is InterimRule -> {
                        val value = d[condition.param]!!
                        if (condition.cond == '<') {
                            if (value < condition.value) {
                                workflow = condition.next
                                break
                            }
                        } else {
                            if (value > condition.value) {
                                workflow = condition.next
                                break
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getWorkflows(): Map<String, List<Rule>> {
        val result = hashMapOf<String, List<Rule>>()
        val lines = input.takeWhile { it.isNotBlank() }
        for (l in lines) {
            val name = l.substringBefore('{')
            val rules = l.substringAfter('{').dropLast(1).split(",").map {
                if (!it.contains(":")) {
                    SimpleRule(it.trim())
                } else {
                    val (cond, nextRule) = it.split(":")
                    var condIndex = cond.indexOf('<')
                    if (condIndex == -1) condIndex = cond.indexOf('>')

                    InterimRule(
                        cond[0],
                        cond[condIndex],
                        cond.substring(condIndex + 1).toInt(),
                        nextRule
                    )
                }
            }
            result[name] = rules

        }
        return result
    }

    data class DetailRange(val ranges: HashMap<Char, IntRange>) {
        fun size(): Long = ranges.values.map { it.size() }.fold(1L) { acc, it -> it * acc }

        private fun IntRange.size() = this.last - this.first + 1

        fun clone(): DetailRange {
            val newRanges = hashMapOf<Char, IntRange>()
            for ((k, v) in ranges) {
                newRanges[k] = IntRange(v.first, v.last)
            }
            return DetailRange(newRanges)
        }

        fun isValid(): Boolean {
            return ranges.values.all { it.first <= it.last }
        }
    }

    private fun getDetails(): List<Map<Char, Int>> {
        return input.dropWhile { it.isNotBlank() }.drop(1).map {
            val props = it.dropLast(1).drop(1).split(",")
            val result = hashMapOf<Char, Int>()
            for (p in props) {
                val (key, value) = p.split("=")
                result[key[0]] = value.toInt()
            }
            result
        }
    }

    private sealed interface Rule
    private data class SimpleRule(val value: String) : Rule
    private data class InterimRule(val param: Char, val cond: Char, val value: Int, val next: String) : Rule
}

fun main() {
    val daytest = Day19("day19_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 19114
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 167409079868000

    val day = Day19("day19.txt")
    println("part1: ${day.part1()}")             //part1: 263678
    println("part2: ${day.part2()}")             //part2: 125455345557345
}