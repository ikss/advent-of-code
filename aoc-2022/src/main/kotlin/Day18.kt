class Day18(title: String) : DayX(title) {
    private val moves = listOf(-1, 1)
    private val grid = HashMap<Triple<Int, Int, Int>, Int>()
    private val droplets = input.map {
        val (x, y, z) = it.readAllNumbers().map { it.toInt() }.toList()
        Triple(x, y, z).also { droplet -> grid.put(droplet, 1) }
    }

    override fun part1(): Any {
        var result = 0
        for (droplet in droplets) {
            val (x, y, z) = droplet
            var sides = 0
            for (dx in moves) {
                if (Triple(x + dx, y, z) !in grid) {
                    sides++
                }
            }
            for (dy in moves) {
                if (Triple(x, y + dy, z) !in grid) {
                    sides++
                }
            }
            for (dz in moves) {
                if (Triple(x, y, z + dz) !in grid) {
                    sides++
                }
            }
            result += sides
        }


        return result
    }

    override fun part2(): Any {
        var maxx = 0
        var maxy = 0
        var maxz = 0

        for (droplet in droplets) {
            val (x, y, z) = droplet
            maxx = maxOf(maxx, x)
            maxy = maxOf(maxy, y)
            maxz = maxOf(maxz, z)
        }

        val queue = java.util.ArrayDeque<Triple<Int, Int, Int>>()
        queue.add(Triple(-1, -1, -1))
        grid.put(Triple(-1, -1, -1), -1)

        while (queue.isNotEmpty()) {
            val (cx, cy, cz) = queue.poll()

            for (x in moves) {
                val newx = cx + x
                if (newx < -1 || newx > maxx + 1 || grid.get(Triple(newx, cy, cz)) != null) {
                    continue
                }
                grid.put(Triple(newx, cy, cz), -1)
                queue.add(Triple(newx, cy, cz))
            }

            for (y in moves) {
                val newy = cy + y
                if (newy < -1 || newy > maxy + 1 || grid.get(Triple(cx, newy, cz)) != null) {
                    continue
                }
                grid.put(Triple(cx, newy, cz), -1)
                queue.add(Triple(cx, newy, cz))
            }

            for (z in moves) {
                val newz = cz + z
                if (newz < -1 || newz > maxz + 1 || grid.get(Triple(cx, cy, newz)) != null) {
                    continue
                }
                grid.put(Triple(cx, cy, newz), -1)
                queue.add(Triple(cx, cy, newz))
            }
        }

        var result = 0L
        for (droplet in droplets) {
            val (x, y, z) = droplet

            var sides = 0
            for (dx in moves) {
                if (grid[Triple(x + dx, y, z)] == -1) {
                    sides++
                }
            }
            for (dy in moves) {
                if (grid[Triple(x, y + dy, z)] == -1) {
                    sides++
                }
            }
            for (dz in moves) {
                if (grid[Triple(x, y, z + dz)] == -1) {
                    sides++
                }
            }
            result += sides
        }

        return result
    }
}

fun main() {
    val day = Day18("Day 18: Boiling Boulders")
    day.solve()
    // Part 1: 4192
    // Part 2: 2520
}
