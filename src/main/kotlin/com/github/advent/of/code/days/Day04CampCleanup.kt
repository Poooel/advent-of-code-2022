package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day04CampCleanup : Executable {
    override fun executePartOne(input: String): Any {
        return getElvesRangesPairs(input).count { elvesRangesPair ->
            val (firstRange, secondRange) = elvesRangesPair
            firstRange.all { secondRange.contains(it) } || secondRange.all { firstRange.contains(it) }
        }
    }

    override fun executePartTwo(input: String): Any {
        return getElvesRangesPairs(input).count { elvesRangesPair ->
            val (firstRange, secondRange) = elvesRangesPair
            firstRange.any { secondRange.contains(it) }
        }
    }

    private fun getElvesRangesPairs(input: String): List<Pair<IntRange, IntRange>> {
        val elvesPairs = input.split('\n')

        return elvesPairs.map { elvesPair ->
            val (firstElf, secondElf) = elvesPair.split(',')

            val (firstRangeStart, firstRangeEnd) = firstElf.split('-')
            val (secondRangeStart, secondRangeEnd) = secondElf.split('-')

            val firstRange = firstRangeStart.toInt()..firstRangeEnd.toInt()
            val secondRange = secondRangeStart.toInt()..secondRangeEnd.toInt()

            Pair(firstRange, secondRange)
        }
    }
}
