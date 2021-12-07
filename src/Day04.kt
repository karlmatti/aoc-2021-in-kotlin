fun main() {
    fun getBingoBoards(boards: List<String>) = boards
        .filter { it.isNotEmpty() }
        .windowed(5, 5)
        .map {
            it.map {
                it.split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
            }
        }

    fun getBingoNumbers(numbers: String) = numbers.split(',').map { it.toInt() }

    fun sum(numbers: List<List<Int>>) = numbers
        .filter { it.isNotEmpty() }
        .map { it.reduce { sum, b -> sum + b } }
        .reduce { sum, b -> sum + b }

    fun removeDrawnNumbers(numbers: List<List<Int>>, numbersToRemove: MutableList<Int>): List<List<Int>> {
        return numbers.map {
            it.filter {
                val bingoNumber = it
                !numbersToRemove.any { bingoNumber == it }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val bingoNumbers = getBingoNumbers(input[0])
        val bingoBoards = getBingoBoards(input.subList(1, input.size))
        val rowLength = bingoBoards[0][0].size
        val colLength = bingoBoards[0].size
        val drawnNumbers = mutableListOf<Int>()

        bingoNumbers.forEach {
            val drawnNumber = it
            drawnNumbers.add(drawnNumber)
            val bingoBoardsWithoutDrawnNumbers = bingoBoards.map {
                it.map {
                    it.filter {
                        val bingoNumber = it
                        !drawnNumbers.any { bingoNumber == it }
                    }
                }.filter { it.isNotEmpty() }
            }
            bingoBoardsWithoutDrawnNumbers.forEach {
                if (it.size != rowLength) {
                    return sum(it) * drawnNumber
                }
            }
            bingoBoards.forEach {
                val bingoBoard = it
                Array(rowLength) { it }.forEach {
                    val colIndex = it
                    var matchedNumbers = 0
                    Array(colLength) { it }.forEach {
                        val rowIndex = it
                        if (drawnNumbers.any { bingoBoard[rowIndex][colIndex] == it }) {
                            matchedNumbers++
                        }
                    }
                    if (matchedNumbers == colLength) {
                        val leftoverNumbers = removeDrawnNumbers(bingoBoard, drawnNumbers)
                        return sum(leftoverNumbers) * drawnNumber
                    }
                }
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val bingoNumbers = getBingoNumbers(input[0])
        val bingoBoards = getBingoBoards(input.subList(1, input.size)).toMutableList()
        val rowLength = bingoBoards[0][0].size
        val colLength = bingoBoards[0].size
        val drawnNumbers = mutableListOf<Int>()

        bingoNumbers.forEach {
            val drawnNumber = it
            drawnNumbers.add(drawnNumber)
            val bingoBoardsWithoutDrawnNumbers = bingoBoards.map {
                it.map {
                    it.filter {
                        val bingoNumber = it
                        !drawnNumbers.any { bingoNumber == it }
                    }
                }
                    .filter { it.isNotEmpty() }
            }.toMutableList()
            for (bingoBoardsWithoutDrawnNumber in bingoBoardsWithoutDrawnNumbers.toList()) {
                if (bingoBoardsWithoutDrawnNumber.size != rowLength) {
                    if (bingoBoards.size > 1) {
                        val index = bingoBoardsWithoutDrawnNumbers.indexOf(bingoBoardsWithoutDrawnNumber)
                        bingoBoards.removeAt(index)
                        bingoBoardsWithoutDrawnNumbers.removeAt(index)
                    } else {
                        return sum(bingoBoardsWithoutDrawnNumber) * drawnNumber
                    }
                }
            }
            for (currentBingoBoard in bingoBoards.toList()) {
                for (colIndex in Array(rowLength) { it }) {
                    val matchedNumbers = Array(colLength) { it }.filter {
                        val rowIndex = it
                        drawnNumbers.any { currentBingoBoard[rowIndex][colIndex] == it }
                    }
                        .count()
                    if (matchedNumbers == colLength) {
                        if (bingoBoards.size > 1) {
                            bingoBoards.remove(currentBingoBoard)
                        } else {
                            return sum(removeDrawnNumbers(currentBingoBoard, drawnNumbers)) * drawnNumber
                        }
                    }
                }
            }
        }

        return -1
    }

    val day = "04"
    val input = readInput("day-$day")
    val testInput = readInput("day-$day-test")
    val testInputColWin = readInput("day-$day-test-col-win")

    check(part1(testInput) == 4512)
    check(part1(testInputColWin) == 220)
    println("Part 1 answer: ${part1(input)}")

    check(part2(testInput) == 1924)
    check(part2(testInputColWin) == 483)
    println("Part 2 answer: ${part2(input)}")
}
