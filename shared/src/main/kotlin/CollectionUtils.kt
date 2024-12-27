fun <T> combinations(list: List<T>, k: Int): List<List<T>> {
    val result = ArrayList<List<T>>()

    fun backtrack(start: Int, current: ArrayList<T>) {
        if (current.size == k) {
            result.add(ArrayList(current))
            return
        }

        for (i in start until list.size) {
            current.add(list[i])
            backtrack(i + 1, current)
            current.removeLast()
        }
    }

    backtrack(0, ArrayList())
    return result
}