class Day25(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()
    val graph = buildGraph()

    private fun buildGraph(): Graph {
        val graph = LinkedHashMap<String, LinkedHashSet<String>>()
        val vertices = LinkedHashSet<String>()
        val edges = LinkedHashSet<Pair<String, String>>()
        input.forEach {
            val (from, toList) = it.split(": ")
            vertices.add(from)
            for (to in toList.split(" ")) {
                vertices.add(to)
                edges.add(listOf(from, to).sorted().let { (a, b) -> Pair(a, b) })
                graph.computeIfAbsent(from) { LinkedHashSet() }.add(to)
                graph.computeIfAbsent(to) { LinkedHashSet() }.add(from)
            }
        }
        return Graph(graph, vertices, edges)
    }

    data class Graph(
        val graph: LinkedHashMap<String, LinkedHashSet<String>>,
        val vertices: LinkedHashSet<String>,
        val edges: LinkedHashSet<Pair<String, String>>,
    )

    fun part1UfBruteforce(): Long {
        val graph = buildGraph()
        for (i in (0..<graph.edges.size)) {
            val edge1 = graph.edges.elementAt(i)
            for (j in (i + 1..<graph.edges.size)) {
                val edge2 = graph.edges.elementAt(j)
                for (k in (j + 1..<graph.edges.size)) {
                    val edge3 = graph.edges.elementAt(k)
                    val uf = UnionFind<String>(graph.vertices.size)
                    val remove = setOf(edge1, edge2, edge3)
                    fillUf(uf, graph, remove)
                    if (uf.components == 2) {
                        return uf.rank.filterValues { it > 1 }.map { it.value }.reduce { acc, i -> acc * i }.toLong()
                    }
                }
            }
        }

        return -1
    }

    fun part1Hackish(): Long {
        val graph = buildGraph()
        val remove = setOf(
            "krx" to "lmg",
            "tqn" to "tvf",
            "tnr" to "vzb"
        )
        val uf = UnionFind<String>(graph.vertices.size)
        fillUf(uf, graph, remove)
        return uf.rank.filterValues { it > 1 }.map { it.value }.reduce { acc, i -> acc * i }.toLong()
    }

    private fun fillUf(uf: UnionFind<String>, graph: Graph, of: Set<Pair<String, String>>) {
        for (edge in graph.edges) {
            if (edge in of) continue
            uf.union(edge.first, edge.second)
        }
    }
}

fun main() {
    val daytest = Day25("day25_test.txt")
    println("part1 test: ${daytest.part1UfBruteforce()}")       //part1 test: 54

    val day = Day25("day25.txt")
    println("part1: ${day.part1Hackish()}")                     //part1: 582626
}