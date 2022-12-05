package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day05SupplyStacks : Executable {
    override fun executePartOne(input: String): Any {
        val (rawCrates, rawMoves) = input.split("\n\n")
        val crates = parseCrates(rawCrates)
        val moves = parseMoves(rawMoves)
        val newCrates = operateCrane(crates, moves)
        val topMostCrates = getTopMostCrates(newCrates)
        return topMostCrates.joinToString("")
    }

    override fun executePartTwo(input: String): Any {
        val (rawCrates, rawMoves) = input.split("\n\n")
        val crates = parseCrates(rawCrates)
        val moves = parseMoves(rawMoves)
        val newCrates = operateNewCrane(crates, moves)
        val topMostCrates = getTopMostCrates(newCrates)
        return topMostCrates.joinToString("")
    }

    private fun parseCrates(rawCrates: String): List<List<Char>> {
        val rawCratesLines = rawCrates.lines()
        val rawCratesLinesWithoutFooter = rawCratesLines.dropLast(1)
        val crateStacks = mutableMapOf<Int, List<Char>>()

        rawCratesLinesWithoutFooter.forEach { rawCratesLine ->
            for (i in 1 until rawCratesLine.length step 4) {
                val possibleCrate = rawCratesLine.elementAt(i)

                if (possibleCrate.isLetter()) {
                    crateStacks.compute(i) { _, value ->
                        if (value == null) {
                            listOf(possibleCrate)
                        } else {
                            listOf(possibleCrate) + value
                        }
                    }
                }
            }
        }

        return crateStacks.toSortedMap().values.toList()
    }

    private fun parseMoves(rawMoves: String): List<Triple<Int, Int, Int>> {
        val rawMovesLines = rawMoves.lines()
        val movesRegex = Regex("\\d+")

        return rawMovesLines.map { rawMovesLine ->
            val matches = movesRegex.findAll(rawMovesLine)
            val (count, from, to) = matches.map { it.value }.toList()
            Triple(count.toInt(), from.toInt(), to.toInt())
        }
    }

    private fun operateCrane(crates: List<List<Char>>, moves: List<Triple<Int, Int, Int>>): List<List<Char>> {
        val mutableCrates = crates.map { list -> list.toMutableList() }

        moves.forEach { move ->
            val (count, from, to) = move

            for (i in 0 until count) {
                val crate = mutableCrates[from - 1].removeLast()
                mutableCrates[to - 1].add(crate)
            }
        }

        return mutableCrates.map { mutableList -> mutableList.toList() }
    }

    private fun operateNewCrane(crates: List<List<Char>>, moves: List<Triple<Int, Int, Int>>): List<List<Char>> {
        val mutableCrates = crates.map { list -> list.toMutableList() }

        moves.forEach { move ->
            val (count, from, to) = move

            val temporaryCrates = mutableListOf<Char>()

            for (i in 0 until count) {
                temporaryCrates.add(mutableCrates[from - 1].removeLast())
            }

            mutableCrates[to - 1].addAll(temporaryCrates.reversed())
        }

        return mutableCrates.map { mutableList -> mutableList.toList() }
    }

    private fun getTopMostCrates(crates: List<List<Char>>): List<Char> {
        return crates.map { stack -> stack.last() }
    }
}
