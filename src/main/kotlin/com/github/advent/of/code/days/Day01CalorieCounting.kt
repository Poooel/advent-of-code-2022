package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day01CalorieCounting : Executable {
    override fun executePartOne(input: String): Any {
        val grossCaloriesByElves = input.split("\n\n")

        val caloriesByElves = grossCaloriesByElves.map { grossCalories ->
            grossCalories.split("\n").sumOf { it.toInt() }
        }

        return caloriesByElves.max()
    }

    override fun executePartTwo(input: String): Any {
        val grossCaloriesByElves = input.split("\n\n")

        val caloriesByElves = grossCaloriesByElves.map { grossCalories ->
            grossCalories.split("\n").sumOf { it.toInt() }
        }

        val sortedCaloriesByElves = caloriesByElves.sorted().reversed()

        val (first, second, third) = sortedCaloriesByElves

        return first + second + third
    }
}
