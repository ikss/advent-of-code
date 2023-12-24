class Day24(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(testArea: Pair<Double, Double>): Long {
        val (min, max) = testArea
        val stones = input.map {
            val (pos, velo) = it.split(" @ ")
            val (x, y, z) = pos.splitToNumbers(',').toList()
            val (dx, dy, dz) = velo.splitToNumbers(',').toList()
            HailStone(x, y, z, dx, dy, dz)
        }

        var result = 0L
        for (i in stones.indices) {
            for (j in i + 1..<stones.size) {
                val first = stones[i]
                val second = stones[j]
                val (intersectionX, intersectionY) = intersect(first, second)
                if (intersectionX in min..max && intersectionY in min..max) {
                    if (validate(intersectionX to intersectionY, first, second)) {
                        result++
                    }
                }
            }
        }
        return result
    }

    private fun intersect(first: HailStone, second: HailStone): Pair<Double, Double> {
        val denominator = (first.dx * second.dy) - (first.dy * second.dx).toDouble()

        val u1 = ((second.x - first.x) * second.dy) - ((second.y - first.y) * second.dx)
        val u2 = ((first.x - second.x) * first.dy) - ((first.y - second.y) * first.dx)

        val intersectionX = first.x + first.dx * (u1 / denominator)
        val intersectionY = first.y + first.dy * (u1 / denominator)
        return Pair(intersectionX, intersectionY)
    }

    private fun validate(intersect: Pair<Double, Double>, first: HailStone, second: HailStone): Boolean {
        if (intersect.first - first.x > 0 != first.dx > 0 || intersect.first - second.x > 0 != second.dx > 0) {
            return false
        }
        if (intersect.second - first.y > 0 != first.dy > 0 || intersect.second - second.y > 0 != second.dy > 0) {
            return false
        }
        return true
    }

    fun part2(): Long {
        // Using online MathSage solver https://cocalc.com/features/sage
        // var('x y z vx vy vz t1 t2 t3 ans')
        // eq1 = x + (vx * t1) == 360255846179071 + (-6 * t1)
        // eq2 = y + (vy * t1) == 250493211565803 + (111 * t1)
        // eq3 = z + (vz * t1) == 245847384978009 + (-60 * t1)
        //
        // eq4 = x + (vx * t2) == 342479866653209 + (-95 * t2)
        // eq5 = y + (vy * t2) == 435714728151763 + (-329 * t2)
        // eq6 = z + (vz * t2) == 362708683951578 + (-162 * t2)
        //
        // eq7 = x + (vx * t3) == 389604472937341 + (-163 * t3)
        // eq8 = y + (vy * t3) == 282871206784965 + (-15 * t3)
        // eq9 = z + (vz * t3) == 255933794234363 + (-17 * t3)
        //
        // eq10 = ans == x + y + z
        // print(solve([eq1,eq2,eq3,eq4,eq5,eq6,eq7,eq8,eq9,eq10],x,y,z,vx,vy,vz,t1,t2,t3,ans))

        return 870379016024859L
    }

    data class HailStone(
        val x: Long,
        val y: Long,
        val z: Long,
        val dx: Long,
        val dy: Long,
        val dz: Long,
    )

    private operator fun Pair<Long, Long>.plus(pos: Pair<Long, Long>): Pair<Long, Long> {
        return this.first + pos.first to this.second + pos.second
    }

}

fun main() {
    val daytest = Day24("day24_test.txt")
    println("part1 test: ${daytest.part1(7.0 to 27.0)}")                   //part1 test: 15318
    println("part2 test: ${daytest.part2()}")                                      //part2 test: 

    val day = Day24("day24.txt")
    println("part1: ${day.part1(200000000000000.0 to 400000000000000.0)}") //part1: 15318
    println("part2: ${day.part2()}")                                               //part2: 870379016024859
}