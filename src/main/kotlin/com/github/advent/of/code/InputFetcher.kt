package com.github.advent.of.code

import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.io.File

object InputFetcher {
    private val cookies = File("cookies").readText()

    fun fetch(day: Int): String {
        val (_, _, result) = "https://adventofcode.com/2022/day/$day/input"
            .httpGet()
            .header(Headers.COOKIE to "session=$cookies")
            .responseString()

        when (result) {
            is Result.Failure -> {
                throw result.getException()
            }
            is Result.Success -> {
                return result.get().trimEnd()
            }
        }
    }
}
