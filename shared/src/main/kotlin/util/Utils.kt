fun String.splitToNumbers(delimiter: Char = ' '): Sequence<Long> = this.splitToSequence(delimiter).mapNotNull(::mapToLong)

fun mapToLong(s: String): Long? = s.trim().takeIf { it.isNotBlank() }?.toLong()

fun Iterable<Long>.lcm(): Long = this.reduce { acc, l -> lcm(acc, l) }

private fun lcm(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger

    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

typealias Point = Pair<Int, Int>

operator fun Point.plus(other: Point): Point = Point(this.first + other.first, this.second + other.second)

operator fun Point.plus(direction: Direction): Point {
    val (dx, dy) = direction.next
    return first + dx to second + dy
}

fun Point.invert(): Point {
    val (x, y) = this
    return (if (x == 1) -1 else 1) to (if (y == 1) -1 else 1)
}

val diagonalDirections = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
val allDirections = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
val straightDirections = listOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0)

enum class Direction(val next: Point) {
    UP(-1 to 0),
    RIGHT(0 to 1),
    DOWN(1 to 0),
    LEFT(0 to -1),
    ;

    fun isOpposite(other: Direction): Boolean {
        return (this == UP && other == DOWN)
                || (this == RIGHT && other == LEFT)
                || (this == LEFT && other == RIGHT)
                || (this == DOWN && other == UP)
    }

    fun getOpposite(other: Direction): Direction =
        when (other) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }
}
