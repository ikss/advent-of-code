typealias Point = Pair<Int, Int>

operator fun Point.plus(other: Point): Point = Point(this.first + other.first, this.second + other.second)
operator fun Point.minus(other: Point): Point = Point(this.first - other.first, this.second - other.second)

operator fun Point.plus(direction: Direction): Point {
    val (dx, dy) = direction.next
    return first + dx to second + dy
}

fun Point.invert(): Point {
    val (x, y) = this
    return -x to -y
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

    fun getOpposite(other: Direction): Direction =
        when (other) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }
    
    fun getRight(): Direction =
        when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    
    fun getLeft(): Direction =
        when (this) {
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }
}
