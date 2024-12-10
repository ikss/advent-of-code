fun DayX.readInput(): List<String> = this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines()

fun List<String>.toCharGrid(): List<CharArray> = this.map { it.toCharArray() }

fun List<String>.findStart(vararg startChars: Char): Point {
    for (r in this.indices) {
        for (c in this[r].indices) {
            if (this[r][c] in startChars) {
                return Point(r, c)
            }
        }
    }
    throw IllegalArgumentException("No start found")
}


fun List<String>.findStarts(vararg startChars: Char): List<Point> {
    val result = ArrayList<Point>()
    for (r in this.indices) {
        for (c in this[r].indices) {
            if (this[r][c] in startChars) {
                result.add(Point(r, c))
            }
        }
    }
    if (result.isEmpty()) throw IllegalArgumentException("No start found")
    return result
}

fun mapToLong(s: String): Long? = s.trim().takeIf { it.isNotBlank() }?.toLong()

fun String.splitToNumbers(delimiter: Char = ' '): Sequence<Long> = this.splitToSequence(delimiter).mapNotNull(::mapToLong)

fun Long.concat(other: Long): Long = "$this$other".toLong()