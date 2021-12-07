fun main() {
    fun getCoordinates(rawInput: List<String>) = rawInput
        .map { it.split("->") }
        .flatten()
        .map { it.trim().split(',') }
        .flatten()
        .map { it.toInt() }
        .windowed(2, 2)
        .windowed(2, 2)

    fun getHighestCoordinate(coordinates: List<List<List<Int>>>) =
        coordinates.flatten().flatten().maxOrNull() ?: 0

    fun part1(rawInput: List<String>): Int {
        val allCoordinates = getCoordinates(rawInput)
        val highestCoordinate = getHighestCoordinate(allCoordinates)
        val matrix = Array(highestCoordinate + 1) { Array(highestCoordinate + 1) { 0 } }
        val filteredCoordinates = allCoordinates.filter { it[0][0] == it[1][0] || it[0][1] == it[1][1] }

        for (filteredCoordinate in filteredCoordinates) {
            val x1 = filteredCoordinate[0][0]
            val y1 = filteredCoordinate[0][1]

            val x2 = filteredCoordinate[1][0]
            val y2 = filteredCoordinate[1][1]

            if (x1 == x2) {
                val from = if (y1 <= y2) y1 else y2
                val to = if (y1 > y2) y1 else y2

                for (y in from..to) {
                    matrix[x1][y]++
                }
            } else if (y1 == y2) {
                val from = if (x1 <= x2) x1 else x2
                val to = if (x1 > x2) x1 else x2

                for (x in from..to) {
                    matrix[x][y1]++
                }
            }
        }
        return matrix.flatten().filter { it > 1 }.count()
    }

    fun part2(rawInput: List<String>): Int {


        return -1
    }

    val day = "05"

    val input = readInput("day-$day")
    val testInput = readInput("day-$day-test")

    check(part1(testInput) == 5)
    println("Part 1 answer: ${part1(input)}")

//    check(part2(testInput) == 1924)
//    println("Part 2 answer: ${part2(input)}")
}
