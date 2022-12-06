package com.github.advent.of.code.days

import com.github.advent.of.code.Executable
import kotlin.streams.toList

class Day06TuningTrouble : Executable {
    override fun executePartOne(input: String): Any {
        return findFirstUniqueSubString(input, 4)
    }

    override fun executePartTwo(input: String): Any {
        return findFirstUniqueSubString(input, 14)
    }

    private fun findFirstUniqueSubString(string: String, length: Int): Int {
        var charactersBeforeString = 0

        string.windowed(length).forEach { data ->
            if (stringContainsOnlyUniqueCharacters(data)) {
                return charactersBeforeString + length
            } else {
                charactersBeforeString += 1
            }
        }

        throw Error("Unable to find unique substring")
    }

    private fun stringContainsOnlyUniqueCharacters(string: String): Boolean {
        return string.chars().distinct().toList().size == string.length
    }
}
