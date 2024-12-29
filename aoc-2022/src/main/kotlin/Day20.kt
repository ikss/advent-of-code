class Day20 : DayX() {

    private data class Node(val value: Long) {
        var prev: Node? = null
        var next: Node? = null
    }

    override fun part1(): Long {
        val numbers = input.joinToString(",").readAllNumbers().toList()
        return shuffle(numbers, 1)
    }

    override fun part2(): Long {
        val numbers = input.joinToString(",").readAllNumbers().map { it * 811589153 }.toList()
        return shuffle(numbers, 10)
    }

    private fun shuffle(numbers: List<Long>, repeats: Int): Long {
        val head = Node(numbers[0])
        val nodes = ArrayList<Node>(numbers.size)
        nodes.add(head)

        var curr = head
        for (i in 1 until numbers.size) {
            val node = Node(numbers[i])
            node.prev = curr
            curr.next = node
            curr = node
            nodes.add(curr)
        }
        curr.next = head
        head.prev = curr

        for (r in 0 until repeats) {
            for (node in nodes) {
                if (node.value == 0L) continue
                val oldPrev = node.prev
                val oldNext = node.next
                var curr = node
                val times = Math.abs((node.value % (numbers.size - 1L)).toInt())
                repeat(times) {
                    if (node.value > 0) {
                        curr = curr.next!!
                    } else {
                        curr = curr.prev!!
                    }
                }
                oldPrev!!.next = oldNext
                oldNext!!.prev = oldPrev

                if (node.value > 0) {
                    val next = curr.next
                    curr.next = node
                    node.prev = curr
                    node.next = next
                    next!!.prev = node
                } else {
                    val prev = curr.prev
                    curr.prev = node
                    node.prev = prev
                    node.next = curr
                    prev!!.next = node
                }
            }
        }
        curr = head
        while (curr.value != 0L) {
            curr = curr.next!!
        }
        var result = 0L
        repeat(1000) { curr = curr.next!! }
        result += curr.value
        repeat(1000) { curr = curr.next!! }
        result += curr.value
        repeat(1000) { curr = curr.next!! }
        result += curr.value
        while (curr.value != 0L) {
            curr = curr.next!!
        }
        return result
    }
}

fun main() {
    val day = Day20()
    day.solve()
    // Part 1: 2215
    // Part 2: 8927480683
}
            