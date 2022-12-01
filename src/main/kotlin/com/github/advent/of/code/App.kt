package com.github.advent.of.code

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.check
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.terminal.Terminal

class App : CliktCommand() {
    private val day by argument(help = "The number of the day you wish to execute").int().check("Value must be between 1 & 25") { it in 1..25 }
    private val part by argument(help = "The part of the day you wish to execute").int().check("Value must be 1 or 2") { it in 1..2 }

    override fun run() {
        val input = InputFetcher.fetch(day)
        val dayToExecute = DayFetcher.fetch(day)
        val result = dayToExecute.execute(part, input)

        val terminal = Terminal()
        val promptStyle = (TextStyles.bold + TextColors.brightBlue + TextStyles.underline)
        val resultStyle = (TextStyles.bold + TextColors.brightMagenta)
        terminal.println("${promptStyle("The answer for day #$day part #$part is:")} ${resultStyle("$result")}")
    }
}

fun main(args: Array<String>) = App().main(args)
