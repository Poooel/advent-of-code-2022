package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day03RucksackReorganization : Executable {
    override fun executePartOne(input: String): Any {
        val rucksacks = input.split('\n')

        return rucksacks.sumOf { rucksack ->
            val firstCompartment = rucksack.subSequence(0, rucksack.length / 2).toString()
            val secondCompartment = rucksack.subSequence(rucksack.length / 2, rucksack.length).toString()

            val itemInBothCompartments = findItemInBothCompartments(firstCompartment, secondCompartment)

            getPriorityForItem(itemInBothCompartments)
        }
    }

    override fun executePartTwo(input: String): Any {
        val rucksacks = input.split('\n')

        val rucksacksByGroup = rucksacks.chunked(3)

        return rucksacksByGroup.sumOf { group ->
            val (firstElf, secondElf, thirdElf) = group

            val securityBadge = findBadgeInRucksacks(firstElf, secondElf, thirdElf)

            getPriorityForItem(securityBadge)
        }
    }

    private fun findItemInBothCompartments(firstCompartment: String, secondCompartment: String): Char {
        for (item in firstCompartment) {
            if (secondCompartment.contains(item)) {
                return item
            }
        }

        throw Error("Unable to find same item in both compartments")
    }

    private fun findBadgeInRucksacks(firstRucksack: String, secondRucksack: String, thirdRucksack: String): Char {
        for (item in firstRucksack) {
            if (secondRucksack.contains(item) && thirdRucksack.contains(item)) {
                return item
            }
        }

        throw Error("Unable to find security badge in rucksacks")
    }

    private fun getPriorityForItem(item: Char): Int {
        return if (item.isLowerCase()) {
            item.code - 96
        } else {
            item.code - 38
        }
    }
}
