class Day22(title: String) : DayX(title) {

    override fun part1(): Any {
        var result = 0L
        for (line in input) {
            val secretNumber = line.toLong()
            result += generateNextNumber(secretNumber, 2000)
        }

        return result
    }

    override fun part2(): Any {
        val map = HashMap<List<Int>, Int>()
        for (line in input) {
            val secretNumber = line.toLong()
            generateNextNumberAndStore(secretNumber, 2000, map)
        }
        
        return map.values.max()
    }

    private fun generateNextNumberAndStore(currentNumber: Long, repeats: Int, map: HashMap<List<Int>, Int>): Long {
        var num = currentNumber
        val buyerMap = HashSet<List<Int>>()
        val list = ArrayList<Int>(4)

        for (i in 0 until repeats) {
            val newNum = generateNextNumber(num)
            val newPrice = (newNum % 10).toInt()

            list.add(newPrice - num.toInt() % 10)
            if (list.size == 4) {
                val key = ArrayList(list)
                if (buyerMap.add(key)) {
                    map.merge(key, newPrice, Int::plus)
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
    val day = Day22("Day 22: Monkey Market")
    day.solve()
    // Part 1: 12664695565
    // Part 2: 1444
}