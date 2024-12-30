import java.util.*
import kotlin.math.ceil

class Day19(title: String) : DayX(title) {
    private val blueprints = input.map { Blueprint.from(it) }

    private enum class Mineral {
        ORE, CLAY, OBSIDIAN, GEODE,
    }

    private data class Robot(
        val mineral: Mineral,
        val cost: IntArray,
    )

    private data class Blueprint(val id: Int, val oreRobot: Robot, val clayRobot: Robot, val obsidianRobot: Robot, val geodeRobot: Robot) {
        val maxOre = maxOf(oreRobot.cost[Mineral.ORE], clayRobot.cost[Mineral.ORE], obsidianRobot.cost[Mineral.ORE], geodeRobot.cost[Mineral.ORE])
        val maxClay = obsidianRobot.cost[Mineral.CLAY]
        val maxObsidian = geodeRobot.cost[Mineral.OBSIDIAN]

        companion object {
            fun from(line: String): Blueprint {
                val numbers = line.readAllNumbers().map { it.toInt() }.toList()
                return Blueprint(
                    id = numbers[0],
                    oreRobot = Robot(Mineral.ORE, intArrayOf(numbers[1], 0, 0, 0)),
                    clayRobot = Robot(Mineral.CLAY, intArrayOf(numbers[2], 0, 0, 0)),
                    obsidianRobot = Robot(Mineral.OBSIDIAN, intArrayOf(numbers[3], numbers[4], 0, 0)),
                    geodeRobot = Robot(Mineral.GEODE, intArrayOf(numbers[5], 0, numbers[6], 0)),
                )
            }
        }
    }

    private data class State(
        val robots: IntArray,
        val inventory: IntArray,
        val minutes: Int,
    ) : Comparable<State> {

        fun getNextStates(blueprint: Blueprint): List<State> = buildList {
            if (blueprint.maxOre > robots[Mineral.ORE]) {
                add(buildRobot(blueprint.oreRobot))
            }
            if (blueprint.maxClay > robots[Mineral.CLAY]) {
                add(buildRobot(blueprint.clayRobot))
            }
            if (robots[Mineral.CLAY] > 0 && blueprint.maxObsidian > robots[Mineral.OBSIDIAN]) {
                add(buildRobot(blueprint.obsidianRobot))
            }
            if (robots[Mineral.OBSIDIAN] > 0) {
                add(buildRobot(blueprint.geodeRobot))
            }
        }.filter { it.minutes > 0 }

        fun buildRobot(robot: Robot): State {
            val minutes = timeToBuild(robot)
            return State(
                minutes = this.minutes - minutes,
                inventory = inventory.copyOf().apply {
                    for (i in robots.indices) {
                        this[i] += minutes * robots[i] - robot.cost[i]
                    }
                },
                robots = robots.copyOf().apply {
                    this[robot.mineral.ordinal]++
                },
            )
        }

        fun canOutproduce(best: Int): Boolean {
            val potentialProduction = (0 until minutes - 1).sumOf { it + robots[Mineral.GEODE] }
            return inventory[Mineral.GEODE] + potentialProduction > best
        }

        private fun timeToBuild(robot: Robot): Int {
            val remainingOre = maxOf(0, robot.cost[Mineral.ORE] - inventory[Mineral.ORE])
            val remainingClay = maxOf(0, robot.cost[Mineral.CLAY] - inventory[Mineral.CLAY])
            val remainingObsidian = maxOf(0, robot.cost[Mineral.OBSIDIAN] - inventory[Mineral.OBSIDIAN])
            return maxOf(
                ceil(remainingOre / robots[Mineral.ORE].toFloat()).toInt(),
                ceil(remainingClay / robots[Mineral.CLAY].toFloat()).toInt(),
                ceil(remainingObsidian / robots[Mineral.OBSIDIAN].toFloat()).toInt(),
            ) + 1
        }

        override fun compareTo(other: State): Int {
            return other.inventory[Mineral.GEODE] - this.inventory[Mineral.GEODE]
        }
    }

    override fun part1(): Any {
        var result = 0L

        for (blueprint in blueprints) {
            result += blueprint.id * processBlueprint(blueprint, 24)
        }

        return result
    }

    override fun part2(): Any {
        var result = 1L

        for (blueprint in blueprints.take(3)) {
            result *= processBlueprint(blueprint, 32)
        }

        return result
    }

    private fun processBlueprint(blueprint: Blueprint, minutes: Int): Int {
        val state = State(
            intArrayOf(1, 0, 0, 0),
            intArrayOf(1, 0, 0, 0),
            minutes,
        )
        return findBest(state, blueprint)
    }

    private fun findBest(state: State, blueprint: Blueprint): Int {
        var currentBest = 0
        val queue = PriorityQueue<State>()
        queue.add(state)

        while (queue.isNotEmpty()) {
            val nextState = queue.poll()
            if (nextState.canOutproduce(currentBest)) {
                val elements = nextState.getNextStates(blueprint)
                queue.addAll(elements)
            }
            currentBest = maxOf(currentBest, nextState.inventory[Mineral.GEODE] + nextState.robots[Mineral.GEODE] * (nextState.minutes - 1))
        }

        return currentBest
    }

    private companion object {
        private operator fun IntArray.get(mineral: Mineral) = this[mineral.ordinal]
    }
}

fun main() {
    val day = Day19("Day 19: Not Enough Minerals")
    day.solve()
    // Part 1: 1427
    // Part 2: 4400
}
