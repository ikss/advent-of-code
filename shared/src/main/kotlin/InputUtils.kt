val NUMBER_PATTERN = Regex("-?\\d+")
fun DayX.readInput(): List<String> = this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + ".txt")!!.bufferedReader().readLines()
fun DayX.readExampleInput(): List<String>? = this::class.java.getResourceAsStream(this::class.simpleName!!.lowercase() + "_example.txt")?.bufferedReader()?.readLines()

fun String.readAllNumbers(): Sequence<Long> = NUMBER_PATTERN.findAll(this).map { it.value.toLong() }
fun List<String>.readAllNumbers(): Sequence<Long> = this.joinToString(",").readAllNumbers()

fun mapToLong(s: String): Long? = s.trim().takeIf { it.isNotBlank() }?.toLong()

fun String.splitToNumbers(delimiter: Char = ' '): Sequence<Long> = this.splitToSequence(delimiter).mapNotNull(::mapToLong)

fun Long.concat(other: Long): Long = "$this$other".toLong()