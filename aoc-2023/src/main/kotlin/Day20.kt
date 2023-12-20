class Day20(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val modules = getModules()

        var lows = 0L
        var highs = 0L

        for (i in 1..1000) {
            val queue = java.util.ArrayDeque<Signal>()
            queue.offer(Signal("button", "broadcaster", false))

            while (queue.isNotEmpty()) {
                val (from, to, signal) = queue.poll()
                if (!signal) {
                    highs++
                } else {
                    lows++
                }
                val module = modules[to] ?: continue
                when (module) {
                    is FlipFlop -> {
                        if (signal) continue
                        module.state = !module.state
                        module.connections.forEach {
                            queue.offer(Signal(to, it, module.state))
                        }
                    }

                    is Conjunction -> {
                        module.received[from] = signal
                        if (module.received.values.all { it }) {
                            module.connections.forEach { queue.offer(Signal(to, it, false)) }
                        } else {
                            module.connections.forEach { queue.offer(Signal(to, it, true)) }
                        }
                    }

                    is Broadcast -> {
                        module.connections.forEach {
                            queue.offer(Signal(to, it, signal))
                        }
                    }
                }
            }
        }

        return lows * highs
    }

    fun part2(): Long {
        val modules = getModules()

        val rxInputs = modules.values.filter { it.connections.contains("rx") }
        println("RX inputs = $rxInputs")
        val rxConjunction = rxInputs.first() as Conjunction
        
        val registered = rxConjunction.received.entries.associate { it.key to 0L }.toMutableMap()

        var presses = 0L

        while (true) {
            val queue = java.util.ArrayDeque<Signal>()
            queue.offer(Signal("button", "broadcaster", false))
            presses++
            while (queue.isNotEmpty()) {
                val (from, to, signal) = queue.poll()
                val module = modules[to] ?: continue
                when (module) {
                    is FlipFlop -> {
                        if (signal) continue
                        module.state = !module.state
                        module.connections.forEach {
                            queue.offer(Signal(to, it, module.state))
                        }
                    }

                    is Conjunction -> {
                        if (module.name == rxConjunction.name && signal) {
                            registered[from] = presses
                            if (registered.values.all { it > 0 }) {
                                return registered.values.lcm()
                            }
                        }
                        module.received[from] = signal
                        if (module.received.values.all { it }) {
                            module.connections.forEach { queue.offer(Signal(to, it, false)) }
                        } else {
                            module.connections.forEach { queue.offer(Signal(to, it, true)) }
                        }
                    }

                    is Broadcast -> {
                        module.connections.forEach {
                            queue.offer(Signal(to, it, signal))
                        }
                    }
                }
            }
        }
    }

    private fun getModules(): HashMap<String, Module> {
        val modules = HashMap<String, Module>()

        for (line in input) {
            val (name, conn) = line.split(" -> ")
            val connections = conn.split(", ")
            val module = when {
                name.startsWith('%') -> {
                    FlipFlop(name.substring(1), connections)
                }

                name.startsWith('&') -> {
                    Conjunction(name.substring(1), connections, HashMap())
                }

                name == "broadcaster" -> {
                    Broadcast(name, connections)
                }

                else -> throw IllegalArgumentException("Unknown module: $name")
            }
            modules[module.name] = module
        }

        for ((name, start) in modules) {
            for (conn in start.connections) {
                val end = modules[conn] ?: continue
                if (end is Conjunction) {
                    end.received[name] = false
                }
            }
        }

        return modules
    }

    data class Signal(val from: String, val to: String, val signal: Boolean)

    sealed interface Module {
        val name: String
        val connections: List<String>
    }

    data class Broadcast(
        override val name: String,
        override val connections: List<String>,
    ) : Module

    data class FlipFlop(
        override val name: String,
        override val connections: List<String>,
    ) : Module {
        var state = false
    }

    data class Conjunction(
        override val name: String,
        override val connections: List<String>,
        val received: HashMap<String, Boolean>,
    ) : Module
}

fun main() {
    val daytest1 = Day20("day20_test1.txt")
    println("part1 test1: ${daytest1.part1()}")    //part1 test1: 32000000

    val daytest2 = Day20("day20_test2.txt")
    println("part1 test2: ${daytest2.part1()}")    //part1 test2: 11687500
    
    val day = Day20("day20.txt")
    println("part1: ${day.part1()}")               //part1: 681194780
    println("part2: ${day.part2()}")               //part2: 238593356738827
}