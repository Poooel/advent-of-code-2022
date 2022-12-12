package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day11MonkeyInTheMiddle : Executable {
    override fun executePartOne(input: String): Any {
        val monkeys = parseMonkeys(input)

        playRounds(monkeys, 20, true)

        val (mostActiveMonkey, secondMostActiveMonkey) = monkeys.sortedBy { it.inspectedItems }.reversed()
        return mostActiveMonkey.inspectedItems * secondMostActiveMonkey.inspectedItems
    }

    override fun executePartTwo(input: String): Any {
        val monkeys = parseMonkeys(input)

        playRounds(monkeys, 10_000, false)

        val (mostActiveMonkey, secondMostActiveMonkey) = monkeys.sortedBy { it.inspectedItems }.reversed()
        return mostActiveMonkey.inspectedItems * secondMostActiveMonkey.inspectedItems
    }

    private fun parseMonkeys(input: String): List<Monkey> {
        val monkeys = input.split("\n\n").map { monkeyInputSegment ->
            val (_, startingItemsLine, operationLine, testLine, trueConditionLine, falseConditionLine) = monkeyInputSegment.lines()

            val startingItems = Regex("\\d+").findAll(startingItemsLine).map { it.value.toLong() }.toMutableList()

            val (operandA, operator, operandB) = Regex("=(.*)").find(operationLine)!!.value.drop(2).split(' ')
            val operation = parseOperation(operandA, operator, operandB)

            val divisibleBy = Regex("\\d+").find(testLine)!!.value.toLong()
            val trueMonkey = Regex("\\d").find(trueConditionLine)!!.value.toInt()
            val falseMonkey = Regex("\\d").find(falseConditionLine)!!.value.toInt()
            val test = { worry: Long -> if (worry % divisibleBy == 0L) trueMonkey else falseMonkey }

            Monkey(startingItems, operation, test)
        }

        return monkeys
    }

    private fun parseOperation(operandA: String, operator: String, operandB: String): (Long) -> Long {
        return when {
            operandA == operandB && operandA == "old" -> {
                { old -> old * old }
            }
            operator == "+" -> {
                { old -> old + operandB.toLong() }
            }
            operator == "*" -> {
                { old -> old * operandB.toLong() }
            }
            else -> throw Error("Unable to parse operation.")
        }
    }

    private fun playRounds(monkeys: List<Monkey>, rounds: Int, decreaseWorry: Boolean): List<Monkey> {
        val m = 9_699_690

        for (i in 0 until rounds) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { monkeyInspectedItem ->
                    var newWorryLevel = monkey.operation(monkeyInspectedItem)

                    if (decreaseWorry) {
                        newWorryLevel /= 3
                    } else {
                        // https://www.reddit.com/r/adventofcode/comments/zihouc/comment/izrimjo/
                        newWorryLevel %= m
                    }

                    val monkeyToSendTo = monkey.test(newWorryLevel)

                    monkeys[monkeyToSendTo].items.add(newWorryLevel)

                    monkey.inspectedItems += 1
                }
                monkey.items.clear()
            }
        }

        return monkeys
    }

    private operator fun <E> List<E>.component6(): E {
        return get(5)
    }

    private data class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val test: (Long) -> Int,
        var inspectedItems: Long = 0
    )
}
