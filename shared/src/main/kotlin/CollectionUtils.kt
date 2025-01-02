import java.util.concurrent.BlockingQueue

fun <T> List<T>.combinations(k: Int): List<List<T>> {
    val result = ArrayList<List<T>>()

    fun backtrack(start: Int, current: ArrayList<T>) {
        if (current.size == k) {
            result.add(ArrayList(current))
            return
        }

        for (i in start until this.size) {
            current.add(this[i])
            backtrack(i + 1, current)
            current.removeLast()
        }
    }

    backtrack(0, ArrayList())
    return result
}

fun <T> List<T>.permutations(): List<List<T>> {
    val ret: MutableList<List<T>> = mutableListOf()
    backtrack(ret, mutableSetOf(), this)
    return ret
}

private fun <T> backtrack(ret: MutableList<List<T>>, used: MutableSet<T>, nums: List<T>) {
    if (used.size == nums.size) {
        ret.add(used.toList())
        return
    }
    for (n in nums) {
        if (!used.add(n)) continue

        backtrack(ret, used, nums)
        used.remove(n)
    }
}

fun <E> List<E>.toBlockingQueue(): BlockingQueue<E> {
    return java.util.concurrent.LinkedBlockingQueue(this)
}

fun countAllGraphDistances(graph: HashMap<String, ArrayList<String>>, countedDistances: HashMap<Pair<String, String>, Int>) {
    for (node in graph.keys) {
        countBfs(node, graph, countedDistances)
    }
}

private fun countBfs(node: String, graph: HashMap<String, ArrayList<String>>, countedDistances: HashMap<Pair<String, String>, Int>) {
    val queue = java.util.ArrayDeque<Pair<String, Int>>()
    queue.offer(node to 0)
    val visited = HashSet<String>()

    while (queue.isNotEmpty()) {
        val (curr, currDist) = queue.poll()
        val newDist = currDist + 1
        visited.add(curr)

        for (next in graph[curr]!!) {
            if (next !in visited) {
                queue.offer(next to newDist)
                countedDistances[node to next] = newDist
                countedDistances[next to node] = newDist
            }
        }
    }
}