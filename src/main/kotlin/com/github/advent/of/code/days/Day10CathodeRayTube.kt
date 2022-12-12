package com.github.advent.of.code.days

import com.github.advent.of.code.Executable
import java.lang.StringBuilder

class Day10CathodeRayTube : Executable {
    override fun executePartOne(input: String): Any {
        val importantSignalStrengths = mutableListOf<Int>()

        runCpu(input.lines(), this::addToImportantSignalStrengths, importantSignalStrengths)

        return importantSignalStrengths.sum()
    }

    override fun executePartTwo(input: String): Any {
        val crt = List(6) { StringBuilder(".".repeat(40)) }

        runCpu(input.lines(), this::drawOnCrt, crt)

        return "\n" + crt.joinToString("\n")
    }

    private fun runCpu(instructions: List<String>, actionByCycle: (Any, Int, Int) -> Unit, data: Any) {
        var register = 1
        var cycles = 0

        instructions.forEach { instruction ->
            cycles += 1

            actionByCycle(data, cycles, register)

            if (instruction.startsWith("addx")) {
                val (_, value) = instruction.split(' ')
                cycles += 1

                actionByCycle(data, cycles, register)

                register += value.toInt()
            }
        }
    }

    private fun addToImportantSignalStrengths(importantSignalStrengths: Any, cycle: Int, register: Int) {
        @Suppress("UNCHECKED_CAST")
        importantSignalStrengths as MutableList<Int>

        val importantCycles = listOf(20, 60, 100, 140, 180, 220)

        if (cycle in importantCycles) {
            importantSignalStrengths.add(register * cycle)
        }
    }

    private fun drawOnCrt(crt: Any, cycle: Int, register: Int) {
        @Suppress("UNCHECKED_CAST")
        crt as List<StringBuilder>

        val pixelToDraw = cycle - 1

        if ((pixelToDraw % 40).closeTo(register, 1)) {
            crt[Math.floorDiv(pixelToDraw, 40)][pixelToDraw % 40] = '#'
        } else {
            crt[Math.floorDiv(pixelToDraw, 40)][pixelToDraw % 40] = '.'
        }
    }

    private fun Int.closeTo(target: Int, error: Int): Boolean {
        val targetRange = this - error..this + error
        return target in targetRange
    }
}
