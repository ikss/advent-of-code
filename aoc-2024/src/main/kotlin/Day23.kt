class Day23 : DayX() {
    private val graph = HashMap<String, HashSet<String>>()

    init {
        for (line in input) {
            val (first, second) = line.split('-')

            graph.computeIfAbsent(first) { HashSet() }.add(second)
            graph.computeIfAbsent(second) { HashSet() }.add(first)
        }
    }

    override fun part1(): Any {
        val connected = getConnected(graph, 3, true)

        return connected.count { it.any { it.startsWith("t") } }
    }

    override fun part2(): Any {
        val getConnected = getConnected(graph, 13, false)
        
        return getConnected.maxBy { it.size }.joinToString(",")
    }

    private fun getConnected(graph: HashMap<String, HashSet<String>>, size: Int, all: Boolean): HashSet<List<String>> {
        val foundGroups = HashSet<List<String>>()
        var groups = ArrayList<HashSet<String>>()
        for (k in graph.keys) {
            groups.add(hashSetOf(k))
        }

        for ((k, v) in graph) {
            val newGroups = ArrayList<HashSet<String>>()

            for (group in groups) {
                var found = false

                if (group.all { it in v }) {
                    found = true
                    val newGroup = HashSet(group)
                    newGroup.add(k)
                    if (newGroup.size == size) {
                        foundGroups.add(newGroup.sorted())
                    } else if (newGroup.size < size) {
                        newGroups.add(newGroup)
                    }
                }

                if (all || !found) {
                    newGroups.add(group)
                }
            }
            groups = newGroups
        }
        return foundGroups
    }
}

fun main() {
    val day = Day23()
    day.solve()
    // Part 1: 1306
    // Part 2: bd,dk,ir,ko,lk,nn,ob,pt,te,tl,uh,wj,yl
}