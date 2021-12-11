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

    fun generateMatrix(highestCoordinate: Int): Array<Array<Int>> {
        return Array(highestCoordinate + 1) { Array(highestCoordinate + 1) { 0 } }
    }

    fun getOverlappingMoreThanOnceCount(matrix: Array<Array<Int>>) =
        matrix.flatten().filter { it > 1 }.count()

    fun isDiagonalVector(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
        return kotlin.math.abs(x1 - x2) - kotlin.math.abs(y1 - y2) == 0
    }

    fun part1(rawInput: List<String>): Int {
        val allCoordinates = getCoordinates(rawInput)
        val matrix = generateMatrix(getHighestCoordinate(allCoordinates))
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
        return getOverlappingMoreThanOnceCount(matrix)
    }


    fun part2(rawInput: List<String>): Int {
        val allCoordinates = getCoordinates(rawInput)
        val matrix = generateMatrix(getHighestCoordinate(allCoordinates))

        for (vector in allCoordinates) {
            val x1 = vector[0][0]
            val y1 = vector[0][1]

            val x2 = vector[1][0]
            val y2 = vector[1][1]

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
            } else if (isDiagonalVector(x1, y1, x2, y2)) {
                var startX = x1
                var startY = y1
                var endY = y2
                if (x1 > x2) {
                    startX = x2
                    startY = y2
                    endY = y1
                }

                var currentX = startX
                if (startY < endY) {
                    for (y in startY..endY) {
                        matrix[currentX][y]++
                        currentX++
                    }
                } else if (endY < startY) {
                    for (y in startY downTo endY) {
                        matrix[currentX][y]++
                        currentX++
                    }
                }
            }
        }

        return getOverlappingMoreThanOnceCount(matrix)
    }

    val day = "05"

    val input = readInput("day-$day")
    val testInput = readInput("day-$day-test")

    check(part1(testInput) == 5)
    println("Part 1 answer: ${part1(input)}")

    check(part2(testInput) == 12)
    println("Part 2 answer: ${part2(input)}")
}
