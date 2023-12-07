import Day5.ConversionType.*

class Day5(
    file: String,
) {
    private val input = this::class.java.getResourceAsStream(file)!!.bufferedReader().readLines()

    fun part1(): Long {
        val maps = getConversionMaps()

        val seeds = input.first().substringAfter("seeds: ").splitToNumbers()

        return seeds
            .map { convert(it, maps[SEED_SOIL]!!) }
            .map { convert(it, maps[SOIL_FERTILIZER]!!) }
            .map { convert(it, maps[FERTILIZER_WATER]!!) }
            .map { convert(it, maps[WATER_LIGHT]!!) }
            .map { convert(it, maps[LIGHT_TEMPERATURE]!!) }
            .map { convert(it, maps[TEMPERATURE_HUMIDITY]!!) }
            .map { convert(it, maps[HUMIDITY_LOCATION]!!) }
            .min()
    }

    fun part2(): Long {
        val maps = getConversionMaps()

        val seeds = input.first().substringAfter("seeds: ").splitToNumbers()
        val seedRanges = seeds.chunked(2).map { it.first() to it.first() + it.last() - 1 }.sortedBy { it.first }

        return seedRanges
            .flatMap { convertRange(it, maps[SEED_SOIL]!!) }
            .flatMap { convertRange(it, maps[SOIL_FERTILIZER]!!) }
            .flatMap { convertRange(it, maps[FERTILIZER_WATER]!!) }
            .flatMap { convertRange(it, maps[WATER_LIGHT]!!) }
            .flatMap { convertRange(it, maps[LIGHT_TEMPERATURE]!!) }
            .flatMap { convertRange(it, maps[TEMPERATURE_HUMIDITY]!!) }
            .flatMap { convertRange(it, maps[HUMIDITY_LOCATION]!!) }
            .minOf { it.first }
    }

    private fun getConversionMaps(): HashMap<ConversionType, List<SourceDestinationMap>> {
        val maps = HashMap<ConversionType, List<SourceDestinationMap>>()
        lateinit var type: ConversionType
        var list = ArrayList<SourceDestinationMap>()
        input.asSequence().drop(2).forEach { str ->
            if (str.isBlank()) return@forEach

            if (str[0].isDigit()) {
                val (d, s, r) = str.splitToNumbers().toList()
                list.add(SourceDestinationMap(d, s, r))
                return@forEach
            }
            if (list.isNotEmpty()) {
                maps[type] = list.sortedBy { it.sourceStart }
                list = ArrayList()
            }
            type = entries.find { it.type == str.substringBefore(" map:") }!!
        }
        if (list.isNotEmpty()) {
            maps[type] = list.sortedBy { it.sourceStart }
        }
        return maps
    }

    private fun convert(value: Long, list: List<SourceDestinationMap>): Long {
        for ((destinationStart, sourceStart, length) in list) {
            if (value >= sourceStart && value < sourceStart + length) {
                return destinationStart + value - sourceStart
            }
        }
        return value
    }

    private fun convertRange(range: Pair<Long, Long>, list: List<SourceDestinationMap>): List<Pair<Long, Long>> {
        var (start, end) = range
        val result = ArrayList<Pair<Long, Long>>()
        for ((destinationStart, sourceStart, length) in list) {
            val sourceEnd = sourceStart + length - 1

            if (start > sourceEnd) continue

            if (end < sourceStart) {
                result.add(start to end)
                break
            }
            
            if (start < sourceStart) {
                result.add(start to sourceStart - 1)
                start = sourceStart
            }
            
            if (end < sourceEnd) {
                result.add(destinationStart + start - sourceStart to destinationStart + end - sourceStart)
                break
            }
            
            result.add(destinationStart + start - sourceStart to destinationStart + sourceEnd - sourceStart)
            
            start = sourceEnd
        }
        if (result.isEmpty()) {
            result.add(start to end)
        }
        return result
    }

    private enum class ConversionType(val type: String) {
        SEED_SOIL("seed-to-soil"),
        SOIL_FERTILIZER("soil-to-fertilizer"),
        FERTILIZER_WATER("fertilizer-to-water"),
        WATER_LIGHT("water-to-light"),
        LIGHT_TEMPERATURE("light-to-temperature"),
        TEMPERATURE_HUMIDITY("temperature-to-humidity"),
        HUMIDITY_LOCATION("humidity-to-location"),
    }

    private data class SourceDestinationMap(
        val destinationStart: Long,
        val sourceStart: Long,
        val length: Long,
    )
}

fun main() {
    val daytest = Day5("day5_test.txt")
    println("part1 test: ${daytest.part1()}")    //part1 test: 35
    println("part2 test: ${daytest.part2()}\n")  //part2 test: 46

    val day = Day5("day5.txt")
    println("part1: ${day.part1()}")             //part1: 174137457
    println("part2: ${day.part2()}")             //part2: 1493866
}