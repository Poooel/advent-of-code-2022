package com.github.advent.of.code.days

import com.github.advent.of.code.Executable
import kotlin.math.min

class Day13DistressSignal : Executable {
    override fun executePartOne(input: String): Any {
        val pairs = input.split("\n\n").map { pair ->
            val (left, right) = pair.lines()
            left to right
        }

        val validPairs = pairs.mapIndexed { index, pair ->
            val result = isPairInRightOrder(pair.first, pair.second)

            if (result == true) {
                index + 1
            } else {
                0
            }
        }

        return validPairs.sum()
    }

    override fun executePartTwo(input: String): Any {
        val inputWithDividerPackets = "$input\n\n[[2]]\n[[6]]"

        val pairs = inputWithDividerPackets.split("\n\n").map { pair ->
            val (left, right) = pair.lines()
            left to right
        }

        val packets = pairs.map { it.toList() }.flatten()

        val sortedPackets = packets.sortedWith { a, b ->
            when (isPairInRightOrder(a, b)) {
                null -> 0
                true -> -1
                else -> 1
            }
        }

        val firstDividerPacket = sortedPackets.indexOf("[[2]]") + 1
        val secondDividerPacket = sortedPackets.indexOf("[[6]]") + 1

        return firstDividerPacket * secondDividerPacket
    }

    private fun isPairInRightOrder(leftString: String, rightString: String): Boolean? {
        var i = 0
        var j = 0

        while (i < leftString.length && j < rightString.length) {
            if (leftString[i] == '[' && rightString[j] == '[') {
                val left = extractNestedList(leftString, i)
                val right = extractNestedList(rightString, j)

                val result = isPairInRightOrder(
                    left,
                    right
                )

                result?.let { return it }

                i += left.length + 2
                j += right.length + 2
            } else if (leftString[i] == '[' && rightString[j].isDigit()) {
                val left = extractNestedList(leftString, i)
                var right = rightString[j].toString()

                if (j + 1 < rightString.length && rightString[j + 1].isDigit()) {
                    right = rightString.substring(j..j + 1)
                }

                val result = isPairInRightOrder(
                    left,
                    right
                )

                result?.let { return it }

                i += left.length + 2
            } else if (leftString[i].isDigit() && rightString[j] == '[') {
                var left = leftString[i].toString()

                if (i + 1 < leftString.length && leftString[i + 1].isDigit()) {
                    left = leftString.substring(i..i + 1)
                }

                val right = extractNestedList(rightString, j)

                val result = isPairInRightOrder(
                    left,
                    right
                )

                result?.let { return it }

                j += right.length + 2
            } else if (leftString[i].isDigit() && rightString[j].isDigit() && leftString.contains(Regex("[\\[\\]]")) && rightString.contains(Regex("[\\[\\]]"))) {
                val left = extractTrailingNumbers(leftString, i)
                val right = extractTrailingNumbers(rightString, j)

                val result = isPairInRightOrder(
                    left,
                    right
                )

                result?.let { return it }

                i += left.length
                j += right.length
            } else if (!leftString.contains(Regex("[\\[\\]]")) && !rightString.contains(Regex("[\\[\\]]"))) {
                val left = leftString.split(',').map { it.toInt() }
                val right = rightString.split(',').map { it.toInt() }

                for (k in 0 until min(left.size, right.size)) {
                    if (left[k] < right[k]) {
                        return true
                    } else if (left[k] > right[k]) {
                        return false
                    }
                }

                if (left.size < right.size) {
                    return true
                } else if (left.size > right.size) {
                    return false
                }

                break
            } else if (leftString[i].isDigit() && rightString[j].isDigit()) {
                var left = leftString[i].toString()

                if (i + 1 < leftString.length && leftString[i + 1].isDigit()) {
                    left = leftString.substring(i..i + 1)
                }

                var right = rightString[j].toString()

                if (j + 1 < rightString.length && rightString[j + 1].isDigit()) {
                    right = rightString.substring(j..j + 1)
                }

                if (left.toInt() < right.toInt()) {
                    return true
                } else if (left.toInt() > right.toInt()) {
                    return false
                }

                ++i
                ++j
            }

            ++i
            ++j
        }

        if (leftString.isEmpty() && rightString.isNotEmpty()) {
            return true
        } else if (leftString.isNotEmpty() && rightString.isEmpty()) {
            return false
        }

        if (i < leftString.length && j >= rightString.length) {
            return false
        } else if (i >= leftString.length && j < rightString.length) {
            return true
        }

        return null
    }

    private fun extractNestedList(string: String, startingIndex: Int): String {
        if (string[startingIndex] != '[') {
            throw Error("Unable to extract nested list if startingIndex is not the start of the list.")
        }

        var openingBrackets = 0
        var closingBrackets = 0

        for (index in startingIndex until string.length) {
            val char = string[index]

            if (char == '[') {
                ++openingBrackets
            }

            if (char == ']') {
                if (openingBrackets == closingBrackets + 1) {
                    return string.substring(startingIndex + 1 until index)
                } else {
                    ++closingBrackets
                }
            }
        }

        throw Error("Unable to extract nested list from string.")
    }

    private fun extractTrailingNumbers(string: String, startingIndex: Int): String {
        var endingIndex = string.indexOf(",[", startingIndex)

        if (endingIndex == -1) {
            endingIndex = string.indexOf(']', startingIndex)
        }

        if (endingIndex == -1) {
            endingIndex = string.length
        }

        return string.substring(startingIndex until endingIndex)
    }
}
