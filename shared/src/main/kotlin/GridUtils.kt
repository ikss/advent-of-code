typealias Point = Pair<Int, Int>
typealias LongPoint = Pair<Long, Long>

operator fun Point.plus(other: Point): Point = Point(this.first + other.first, this.second + other.second)
operator fun Point.minus(other: Point): Point = Point(this.first - other.first, this.second - other.second)

operator fun Point.plus(direction: Direction): Point = this + direction.next
operator fun Point.minus(direction: Direction): Point = this - direction.next

fun Point.invert(): Point {
    val (x, y) = this
    return -x to -y
}

fun Point.manhattanDistance(other: Point): Int {
    val (x1, y1) = this
    val (x2, y2) = other
    return Math.abs(x1 - x2) + Math.abs(y1 - y2)
}

val fourDirections = listOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0)
val diagDirections = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
val allDirections = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

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

    fun getOpposite(): Direction =
        when (this) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }

    fun getClockwise(): Direction =
        when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }

    fun getCounterClockwise(): Direction =
        when (this) {
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }

    fun getChar(): Char =
        when (this) {
            UP -> '^'
            RIGHT -> '>'
            DOWN -> 'v'
            LEFT -> '<'
        }

    companion object {
        fun fromChar(c: Char): Direction =
            when (c.lowercaseChar()) {
                'u', '^' -> UP
                'r', '>' -> RIGHT
                'd', 'v' -> DOWN
                'l', '<' -> LEFT
                else -> throw IllegalArgumentException("Invalid direction: $c")
            }
    }
}
