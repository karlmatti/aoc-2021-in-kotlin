fun main() {
    fun transformInput(rawInput: List<String>) = rawInput[0].split(',').map { it.toLong() }

    fun groupFishByTimer(fish: List<Long>): MutableMap<Long, Long> =
        fish
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()

    fun growFish(rawInput: List<String>, days: Int): Long {
        val fishByTimer: MutableMap<Long, Long> = groupFishByTimer(transformInput(rawInput))

        for (day in 1..days) {
            val currentFishByTimer = fishByTimer.toMutableMap()
            for (number in 7L downTo 0L) {
                if (number == 0L) {
                    val newFish = currentFishByTimer.getOrDefault(0L, 0L);
                    fishByTimer[6L] = currentFishByTimer.getOrDefault(7L, 0L) + newFish
                    fishByTimer[8L] = newFish
                }
                fishByTimer[number] = currentFishByTimer.getOrDefault(number + 1L, 0L)
            }
        }

        return fishByTimer.values.sum()
    }

    fun part1(rawInput: List<String>): Int {
        return growFish(rawInput, 80).toInt()
    }

    fun part2(rawInput: List<String>): Long {
        return growFish(rawInput, 256)
    }

    val day = "06"

    val input = readInput("day-$day")
    val testInput = readInput("day-$day-test")

    check(part1(testInput) == 5934)
    println("Part 1 answer: ${part1(input)}")

    check(part2(testInput) == 26984457539)
    println("Part 2 answer: ${part2(input)}")
}
