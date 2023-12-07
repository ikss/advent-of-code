fun String.splitToNumbers(): Sequence<Long> = this.splitToSequence(" ").mapNotNull(::mapToLong)

fun mapToLong(s: String): Long? = s.trim().takeIf { it.isNotBlank() }?.toLong()