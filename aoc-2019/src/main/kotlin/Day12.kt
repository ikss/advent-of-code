import kotlin.math.abs

class Day12(title: String) : DayX(title) {
    private data class Moon(var x: Long, var y: Long, var z: Long, var vx: Long = 0, var vy: Long = 0, var vz: Long = 0) {
        fun energy(): Long = (abs(x) + abs(y) + abs(z)) * (abs(vx) + abs(vy) + abs(vz))
    }

    private val originalMoons = input.map {
        val (x, y, z) = Regex("""<x=(-?\d+), y=(-?\d+), z=(-?\d+)>""").find(it)!!.destructured
        Moon(x.toLong(), y.toLong(), z.toLong())
    }

    override fun part1(): Long {
        val moons = originalMoons.map { it.copy() }

        repeat(1000) {
            applyGravity(moons)
            applyVelocity(moons)
        }

        return moons.sumOf { it.energy() }
    }

    private fun applyGravity(moons: List<Moon>) {
        for (i in moons.indices) {
            val first = moons[i]
            for (j in moons.indices) {
                if (i == j) continue
                val second = moons[j]
                if (first.x < second.x) first.vx++
                if (first.x > second.x) first.vx--
                if (first.y < second.y) first.vy++
                if (first.y > second.y) first.vy--
                if (first.z < second.z) first.vz++
                if (first.z > second.z) first.vz--
            }
        }
    }

    private fun applyVelocity(moons: List<Moon>) {
        for (moon in moons) {
            moon.x += moon.vx
            moon.y += moon.vy
            moon.z += moon.vz
        }
    }

    override fun part2(): Long {
        val moons = originalMoons.map { it.copy() }

        var steps = 0L
        val seen = HashSet<Int>()
        val repeatedIn = ArrayList<Long>()

        while (repeatedIn.size < 3) {
            applyGravity(moons)
            applyVelocity(moons)
            steps++
            val axisCount = IntArray(3)
            for (i in moons.indices) {
                val moon = moons[i]

                if (moon.x == originalMoons[i].x && moon.vx == 0L) axisCount[0]++
                if (moon.y == originalMoons[i].y && moon.vy == 0L) axisCount[1]++
                if (moon.z == originalMoons[i].z && moon.vz == 0L) axisCount[2]++
            }

            for (i in axisCount.indices) {
                if (axisCount[i] == moons.size && seen.add(i)) {
                    repeatedIn.add(steps)
                    println("Axis $i: repeated in $steps")
                }
            }
        }

        return repeatedIn.lcm()
    }
}

fun main() {
    val day = Day12("Day 12: The N-Body Problem")
    day.solve()
    // Part 1: 14780
    // Part 2: 279751820342592
}
