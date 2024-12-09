class Day9 : DayX() {
    private val line = input.joinToString("")

    sealed interface DiskNode {
        var startIndex: Int
        var size: Int
        var next: DiskNode?
        var prev: DiskNode?
    }

    data class EmptyNode(
        override var startIndex: Int,
        override var size: Int,
    ) : DiskNode {
        override var next: DiskNode? = null
        override var prev: DiskNode? = null

        override fun toString(): String {
            return "EmptyNode($startIndex, $size) -> $next"
        }
    }

    class DummyNode : DiskNode {
        override var startIndex: Int = -1
        override var size: Int = 0
        override var next: DiskNode? = null
        override var prev: DiskNode? = null

        override fun toString(): String {
            return "DummyNode() -> $next"
        }
    }

    data class DataNode(
        override var startIndex: Int,
        override var size: Int,
        val id: Int,
    ) : DiskNode {
        override var next: DiskNode? = null
        override var prev: DiskNode? = null
        var moved = false

        override fun toString(): String {
            return "DataNode($startIndex, $size, $id) -> $next"
        }
    }

    override fun part1(): Long {
        val sb = StringBuilder(line)
        val list = ArrayList<Int>()

        var left = 0
        var right = line.length - 1

        while (left <= right) {
            val l = (sb[left] - '0')
            val r = (sb[right] - '0')
            if (left % 2 == 0) {
                val ind = left / 2
                repeat(l) {
                    list.add(ind)
                }
                left++
                continue
            }
            if (right % 2 != 0) {
                right--
                continue
            }
            val ind = right / 2
            repeat(minOf(l, r)) {
                list.add(ind)
            }
            if (l > r) {
                right--
                sb[left] = '0' + l - r
            } else if (l < r) {
                left++
                sb[right] = '0' + r - l
            } else {
                left++
                right--
            }
        }

        var result = 0L
        for (i in list.indices) {
            result += i.toLong() * list[i]
        }
        return result
    }

    override fun part2(): Long {
        val head: DiskNode = DummyNode()
        var curr = head
        var start = 0
        for (i in line.indices) {
            val num = line[i] - '0'
            val node = if (i % 2 == 0) {
                DataNode(start, num, i / 2)
            } else {
                EmptyNode(start, num)
            }
            start += node.size
            curr.next = node
            node.prev = curr
            curr = node
        }

        val tail = DummyNode()
        curr.next = tail
        tail.prev = curr
        
        while (true) {
            var data: DiskNode? = tail
            while (data !is DataNode || data.moved) {
                if (data == null) break
                data = data.prev
            }
            if (data == null) {
                break
            }
            (data as DataNode).moved = true

            var emptySpace: DiskNode? = head
            while (emptySpace !is EmptyNode || emptySpace.size < data.size || emptySpace.startIndex > data.startIndex) {
                if (emptySpace == null) break
                emptySpace = emptySpace.next
            }
            if (emptySpace == null) {
                continue
            }
            data.startIndex = emptySpace.startIndex
            data.prev!!.next = data.next
            data.next!!.prev = data.prev

            val prev = emptySpace.prev!!
            val next = emptySpace.next!!
            prev.next = data
            data.prev = prev

            if (emptySpace.size > data.size) {
                val newEmptyNode = EmptyNode(emptySpace.startIndex + data.size, emptySpace.size - data.size)
                newEmptyNode.next = emptySpace.next
                newEmptyNode.prev = data
                data.next = newEmptyNode
                next.prev = newEmptyNode
            } else {
                data.next = next
                next.prev = data
            }
        }

        var result = 0L
        curr = head

        while (curr != tail) {
            if (curr is DataNode) {
                val id = curr.id.toLong()
                repeat(curr.size) {
                    result += id * (curr.startIndex + it)
                }
            }
            curr = curr.next!!
        }

        return result
    }
}

fun main() {
    val day = Day9()
    day.solve()
    // Part 1: 6344673854800
    // Part 2: 6360363199987
}
