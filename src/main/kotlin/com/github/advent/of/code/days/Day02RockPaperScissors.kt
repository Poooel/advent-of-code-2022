package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day02RockPaperScissors : Executable {
    override fun executePartOne(input: String): Any {
        val rounds = input.split("\n")

        return rounds.sumOf { round ->
            val (opponent, you) = round.split(' ')

            val score = when (you) {
                "X" -> 1
                "Y" -> 2
                "Z" -> 3
                else -> 0
            }

            val outcomeScore = when {
                opponent == "A" && you == "X" -> 3
                opponent == "B" && you == "Y" -> 3
                opponent == "C" && you == "Z" -> 3
                opponent == "A" && you == "Y" -> 6
                opponent == "A" && you == "Z" -> 0
                opponent == "B" && you == "X" -> 0
                opponent == "B" && you == "Z" -> 6
                opponent == "C" && you == "X" -> 6
                opponent == "C" && you == "Y" -> 0
                else -> 0
            }

            score + outcomeScore
        }
    }

    override fun executePartTwo(input: String): Any {
        val rounds = input.split("\n")

        return rounds.sumOf { round ->
            val (opponent, you) = round.split(' ')

            val score = when (you) {
                "X" -> 0
                "Y" -> 3
                "Z" -> 6
                else -> 0
            }

            val outcomeScore = when (you) {
                "X" -> {
                    when (opponent) {
                        "A" -> 3 // Z
                        "B" -> 1 // X
                        "C" -> 2 // Y
                        else -> 0
                    }
                }
                "Y" -> {
                    when (opponent) {
                        "A" -> 1 // X
                        "B" -> 2 // Y
                        "C" -> 3 // Z
                        else -> 0
                    }
                }
                "Z" -> {
                    when (opponent) {
                        "A" -> 2 // Y
                        "B" -> 3 // Z
                        "C" -> 1 // X
                        else -> 0
                    }
                }
                else -> 0
            }

            score + outcomeScore
        }
    }
}
