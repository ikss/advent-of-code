fun String.splitToNumbers(): Sequence<Long> = this.splitToSequence(" ").mapNotNull(::mapToLong)

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