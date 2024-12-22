class Day22 : DayX() {


    override fun part1(): Long {
        var result = 0L
        for (line in input) {
            val secretNumber = line.toLong()
            result += generateNextNumber(secretNumber, 2000)
        }

        return result
    }

    override fun part2(): Long {
        var result = 0L
        for ((i, line) in input.withIndex()) {
            map[i] = HashMap()
            val secretNumber = line.toLong()
            result += generateNextNumberAndStore(secretNumber, 2000, i)
        }
        val sumMap = HashMap<List<Int>, Int>()

        for ((_, values) in map) {
            for ((k, v) in values) {
                sumMap.merge(k, v, Int::plus)
            }
        }
        return sumMap.values.max().toLong()
    }

    val map = HashMap<Int, HashMap<List<Int>, Int>>()

    private fun generateNextNumberAndStore(currentNumber: Long, repeats: Int, buyer: Int): Long {
        var num = currentNumber
        val buyerMap = map[buyer]!!
        val list = ArrayList<Int>(4)

        for (i in 0 until repeats) {
            val newNum = generateNextNumber(num)
            val newPrice = (newNum % 10).toInt()

            list.add(newPrice - num.toInt() % 10)
            if (list.size == 4) {
                if (list !in buyerMap) {
                    buyerMap[ArrayList(list)] = newPrice
                }
                list.removeAt(0)
            }
            
            num = newNum
        }
        return num
    }


    private fun generateNextNumber(currentNumber: Long, repeats: Int): Long {
        var num = currentNumber

        for (i in 0 until repeats) {
            num = generateNextNumber(num)
        }
        return num
    }

    private fun generateNextNumber(currentNumber: Long): Long {
        val step1 = prune(mix(currentNumber, currentNumber * 64))
        val step2 = prune(mix(step1, step1 / 32))

        return prune(mix(step2, step2 * 2048))
    }

    private fun mix(currentNumber: Long, newNumber: Long): Long {
        return currentNumber xor newNumber
    }

    private fun prune(currentNumber: Long): Long {
        return currentNumber % 16777216
    }
}

fun main() {
    val day = Day22()
    day.solve()
    // Part 1: 12664695565
    // Part 2: 1444
}