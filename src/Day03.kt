import kotlin.streams.toList

fun main() {
    fun toCharArray(input: List<String>): List<CharArray> {
        return input.map { it.toCharArray() };
    }

    fun reverseBitCounters(mostFrequentBitCounters: MutableList<Char>): MutableList<Char> {
        return mostFrequentBitCounters.map { if (it == '1') '0' else '1' }.toMutableList()
    }

    fun getMostFrequentBitByOneCount(oneCount: Int, totalCount: Int) = if (oneCount > totalCount / 2) '1' else '0'

    fun getLongValue(mostFrequentBitCounters: CharArray) = String(mostFrequentBitCounters).toLong()

    fun part1(input: List<String>): Int {
        val inputInCharArray = toCharArray(input)
        val mostFrequentBitCounters = mutableListOf<Char>()
        for (i in 0 until inputInCharArray[0].size) {
            val oneCount = inputInCharArray.map { it[i] }.count { it == '1' }
            val mostFrequentBit = getMostFrequentBitByOneCount(oneCount, input.size)
            mostFrequentBitCounters.add(mostFrequentBit)
        }
        val gammaRate = convertBinaryToDecimal(getLongValue(mostFrequentBitCounters.toCharArray()))
        val epsilonRate =
            convertBinaryToDecimal(getLongValue(reverseBitCounters(mostFrequentBitCounters).toCharArray()))
        return gammaRate * epsilonRate
    }

    fun count(list: List<Char>, char: Char) = list.count { it == char }

    fun filterByBitAtFixedPosition(
        list: List<CharArray>,
        position: Int,
        bit: Char
    ) = list.stream().filter { it[position] == bit }.toList().toMutableList()

    fun part2(rawInput: List<String>): Int {
        val input = toCharArray(rawInput)
        var oxygenGeneratorRatings = input
        for (i in 0 until input[0].size) {
            val characterAt = oxygenGeneratorRatings.map { it[i] }.toMutableList()
            val mostFrequentBit = if (count(characterAt, '1') >= count(characterAt, '0')) '1' else '0'
            oxygenGeneratorRatings = filterByBitAtFixedPosition(oxygenGeneratorRatings, i, mostFrequentBit)
            if (oxygenGeneratorRatings.size == 1) break
            if (oxygenGeneratorRatings.size == 2 && oxygenGeneratorRatings[0][i] != oxygenGeneratorRatings[1][i]) {
                oxygenGeneratorRatings = if (oxygenGeneratorRatings[0][i] == '1') {
                    listOf(oxygenGeneratorRatings[0])
                } else {
                    listOf(oxygenGeneratorRatings[1])
                }
            }
        }
        var scrubberRatings = input
        for (i in 0 until input[0].size) {
            val characterAt = scrubberRatings.map { it[i] }.toMutableList()
            val leastFrequentBit = if (count(characterAt, '0') <= count(characterAt, '1')) '0' else '1'
            scrubberRatings = filterByBitAtFixedPosition(scrubberRatings, i, leastFrequentBit)
            if (scrubberRatings.size == 1) break
            if (scrubberRatings.size == 2 && scrubberRatings[0][i] != scrubberRatings[1][i]) {
                scrubberRatings = if (scrubberRatings[0][i] == '0') {
                    listOf(scrubberRatings[0])
                } else {
                    listOf(scrubberRatings[1])
                }
            }
        }

        val oxygenGeneratorRating = convertBinaryToDecimal(getLongValue(oxygenGeneratorRatings[0]))
        val scrubberRating = convertBinaryToDecimal(getLongValue(scrubberRatings[0]))
        return oxygenGeneratorRating * scrubberRating
    }

    val testInput = readInput("day-03-test")
    check(part1(testInput) == 198)

    val input = readInput("day-03")
    println(part1(input))

    val part2TestResult = part2(testInput)
    println(part2TestResult)
    check(part2TestResult == 230)
    println(part2(input))
}
