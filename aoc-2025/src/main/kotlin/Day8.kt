import java.util.*
import kotlin.math.sqrt

class Day8(title: String) : DayX(title) {

    override fun part1(): Any {
        val points = input.map { line ->
            val (x, y, z) = line.split(",").map { it.toInt() }
            Point3D(x, y, z)
        }

        val uf = UnionFind(points.size)

        for (p in points) {
            uf.init(p)
        }

        val pq = PriorityQueue<Pair<Double, Pair<Point3D, Point3D>>>(compareBy { it.first })

        for (i in 0 until points.size - 1) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]
                val dist = getDistance(p1, p2)
                pq.add(Pair(dist, Pair(p1, p2)))
            }
        }

        for (i in 1..1000) {
            if (pq.isEmpty()) break
            val (p1, p2) = pq.poll().second
            uf.union(p1, p2)
        }

        var result = 1L

        for (r in uf.rank.values.sortedDescending().take(3)) {
            result *= r
        }

        return result
    }

    override fun part2(): Any {
        val points = input.map { line ->
            val (x, y, z) = line.split(",").map { it.toInt() }

            Point3D(x, y, z)
        }

        val uf = UnionFind(points.size)

        for (p in points) {
            uf.init(p)
        }

        val pq = PriorityQueue<Pair<Double, Pair<Point3D, Point3D>>>(compareBy { it.first })

        for (i in 0 until points.size - 1) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]
                val dist = getDistance(p1, p2)
                pq.add(Pair(dist, Pair(p1, p2)))
            }
        }

        var result = 1L
        while (true) {
            if (pq.isEmpty()) break
            val (p1, p2) = pq.poll().second
            uf.union(p1, p2)
            if (uf.components == 1) {
                result = p1.x.toLong() * p2.x.toLong()
                break
            }
        }

        return result
    }

    private data class Point3D(val x: Int, val y: Int, val z: Int)

    private fun getDistance(p1: Point3D, p2: Point3D): Double {
        val dx = (p2.x - p1.x).toDouble()
        val dy = (p2.y - p1.y).toDouble()
        val dz = (p2.z - p1.z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    private class UnionFind(var components: Int) {
        val root = HashMap<Point3D, Point3D>()
        val rank = HashMap<Point3D, Int>()

        fun init(x: Point3D) {
            root[x] = x
            rank[x] = 1
        }

        fun find(x: Point3D): Point3D {
            if (root[x] != x) {
                root[x] = find(root[x]!!)
            }
            return root[x]!!
        }

        fun union(x: Point3D, y: Point3D) {
            var rootX = find(x)
            var rootY = find(y)
            if (rootX == rootY) return

            if (rank[rootX]!! > rank[rootY]!!) {
                val tmp = rootX
                rootX = rootY
                rootY = tmp
            }
            components--
            root[rootX] = rootY
            rank[rootY] = rank[rootY]!! + rank[rootX]!!
            rank[rootX] = 1
        }
    }
}

fun main() {
    val day = Day8("Day 8: Playground")
    day.solve()
    // Example part 1: 40
    // Example part 2: 25272
    // Real part 1: 122430
    // Real part 2: 8135565324
}
