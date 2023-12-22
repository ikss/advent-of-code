import java.util.*

class Day22(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val bricks = input.mapIndexed { index, it ->
            val (point1, point2) = it.split("~")
            val (x1, y1, z1) = point1.split(",").map { it.toInt() }
            val (x2, y2, z2) = point2.split(",").map { it.toInt() }
            Brick("Brick $index", Point3D(x1, y1, z1), Point3D(x2, y2, z2))
        }.sortedBy { minOf(it.point1.z, it.point2.z) }

        simulateFalling(bricks)

        val solid = HashMap<Point3D, String>()
        for (b in bricks) {
            for (p in b.points) {
                solid[p] = b.name
            }
        }

        val supporters = HashMap<String, HashSet<String>>()
        for (b in bricks) {
            val set = supporters.computeIfAbsent(b.name) { HashSet() }
            for (p in b.points) {
                val s = solid[Point3D(p.x, p.y, p.z - 1)] ?: continue
                if (s == b.name) continue
                set.add(s)
            }
        }

        val canRemove = bricks.map { it.name }.toHashSet()
        val cantRemove = HashSet<String>()

        for (s in supporters) {
            if (s.value.size == 1) {
                cantRemove.add(s.value.first())
            }
        }

        return (canRemove - cantRemove).size.toLong()
    }

    private fun simulateFalling(bricks: List<Brick>): Int {
        var moved: Boolean
        val solid = TreeSet<Point3D>(compareBy({ it.x }, { it.y }, { it.z }))
        for (b in bricks) {
            solid.addAll(b.points)
        }
        val movedBlocks = HashSet<String>()
        do {
            moved = false
            for (b in bricks) {
                if (b.point1.z <= 1) continue

                solid.removeAll(b.points)
                val newPoints = b.points.map { Point3D(it.x, it.y, it.z - 1) }
                if (newPoints.intersect(solid).isEmpty()) {
                    b.moveDown(1)
                    movedBlocks.add(b.name)
                    moved = true
                }

                solid.addAll(b.points)
            }

        } while (moved)
        return movedBlocks.size
    }

    fun part2(): Long {
        val bricks = input.mapIndexed { index, it ->
            val (point1, point2) = it.split("~")
            val (x1, y1, z1) = point1.split(",").map { it.toInt() }
            val (x2, y2, z2) = point2.split(",").map { it.toInt() }
            Brick("Brick $index", Point3D(x1, y1, z1), Point3D(x2, y2, z2))
        }.sortedBy { minOf(it.point1.z, it.point2.z) }

        simulateFalling(bricks)

        var result = 0L

        for (b in bricks) {
            val newBricks = bricks.mapNotNull { if (it.name != b.name) it.copy() else null }
            result += simulateFalling(newBricks)
        }

        return result
    }

    data class Brick(val name: String, val point1: Point3D, val point2: Point3D) {
        var points = rebuildPoints()

        private fun rebuildPoints() =
            buildSet {
                for (x in point1.x..point2.x) {
                    for (y in point1.y..point2.y) {
                        for (z in point1.z..point2.z) {
                            add(Point3D(x, y, z))
                        }
                    }
                }
            }

        fun moveDown(steps: Int) {
            point1.z -= steps
            point2.z -= steps
            points = rebuildPoints()
        }
        fun copy() = Brick(name, point1.copy(), point2.copy())
    }

    data class Point3D(val x: Int, val y: Int, var z: Int) {
        fun copy() = Point3D(x, y, z)
    }
}

fun main() {
    val daytest = Day22("day22_test.txt")
    println("part1 test: ${daytest.part1()}")     //part1 test: 5
    println("part2 test: ${daytest.part2()}")     //part2 test: 7

    val day = Day22("day22.txt")
    println("part1: ${day.part1()}")               //part1: 482
    println("part2: ${day.part2()}")               //part2: 103010
}