package util

class UnionFind<T : Any>(size: Int) {
    val root = HashMap<T, T>()
    val rank = HashMap<T, Int>()
    var maxRank = 0
    var components = size

    fun find(x: T): T {
        var r = root.getOrPut(x) { x }
        if (r != x) {
            r = find(r)
            root[x] = r
        }
        return r
    }

    fun union(x: T, y: T): Int {
        var rootX = find(x)
        var rootY = find(y)
        if (rootX == rootY) return 0
        if (rank.getOrPut(rootX, { 1 }) > rank.getOrPut(rootY, { 1 })) {
            val tmp = rootX
            rootX = rootY
            rootY = tmp
        }
        // Modify the root of the smaller group as the root of the
        // larger group, also increment the size of the larger group.
        components--
        root[rootX] = rootY
        rank[rootY] = rank.getOrPut(rootX, { 1 }) + rank.getOrPut(rootY, { 1 })
        rank[rootX] = 1
        maxRank = maxOf(maxRank, rank[rootY]!!)
        return 1
    }

    fun areConnected(node1: T, node2: T): Boolean {
        return find(node1) == find(node2)
    }

    fun allIsConnected(): Boolean = components == 1
}