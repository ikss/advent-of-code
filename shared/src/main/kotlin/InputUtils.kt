fun DayX.readInput(): List<String> = this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines()

fun List<String>.toCharGrid(): List<CharArray> = this.map { it.toCharArray() }

fun List<String>.findStart(vararg startChar: Char): Point {
    for (r in this.indices) {
        for (c in this[r].indices) {
            if (this[r][c] in startChar) {
                return Point(r, c)
            }
        }
    }
    throw IllegalArgumentException("No start found")
}

fun mapToLong(s: String): Long? = s.trim().takeIf { it.isNotBlank() }?.toLong()

fun String.splitToNumbers(delimiter: Char = ' '): Sequence<Long> = this.splitToSequence(delimiter).mapNotNull(::mapToLong)
