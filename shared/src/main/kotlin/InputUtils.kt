val NUMBER_PATTERN = Regex("-?\\d+")
fun DayX.readInput(): List<String> = this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines()

fun String.readAllNumbers(): Sequence<Long> = NUMBER_PATTERN.findAll(this).map { it.value.toLong() }

typealias CharGrid = List<CharArray>

fun List<String>.toCharGrid(): CharGrid = this.map { it.toCharArray() }

operator fun CharGrid.contains(p: Point): Boolean = p.first in this.indices && p.second in this[p.first].indices
operator fun CharGrid.get(p: Point): Char {
    if (p !in this) throw IllegalArgumentException("Point $p is out of bounds ${this.size}x${this[0].size}")
    return this[p.first][p.second]
}
operator fun CharGrid.set(p: Point, char: Char) {
    this[p.first][p.second] = char
}

fun CharGrid.find(vararg startChars: Char): Point {
    for (r in this.indices) {
        for (c in this[r].indices) {
            if (this[r][c] in startChars) {
                return Point(r, c)
            }
        }
    }
    throw IllegalArgumentException("No ${startChars.concatToString()} found")
}

fun CharGrid.findAll(vararg startChars: Char): List<Point> {
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

fun CharGrid.print() {
    for (row in this) {
        println(row)
    }
}

fun mapToLong(s: String): Long? = s.trim().takeIf { it.isNotBlank() }?.toLong()

fun String.splitToNumbers(delimiter: Char = ' '): Sequence<Long> = this.splitToSequence(delimiter).mapNotNull(::mapToLong)

fun Long.concat(other: Long): Long = "$this$other".toLong()