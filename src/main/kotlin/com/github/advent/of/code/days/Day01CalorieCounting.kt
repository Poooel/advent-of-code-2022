package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day01CalorieCounting : Executable {
    override fun executePartOne(input: String): Any {
        val grossCaloriesByElves = grossCaloriesByElves(input)
        val caloriesByElves = caloriesByElves(grossCaloriesByElves)

        return caloriesByElves.max()
    }

    override fun executePartTwo(input: String): Any {
        val grossCaloriesByElves = grossCaloriesByElves(input)
        val caloriesByElves = caloriesByElves(grossCaloriesByElves)

        val sortedCaloriesByElves = caloriesByElves.sorted().reversed()
        val (first, second, third) = sortedCaloriesByElves

        return first + second + third
    }

    private fun grossCaloriesByElves(input: String): List<String> {
        return input.split("\n\n")
    }

    private fun caloriesByElves(grossCaloriesByElves: List<String>): List<Int> {
        return grossCaloriesByElves.map { grossCalories ->
            grossCalories.split("\n").sumOf { it.toInt() }
        }
    }
}
