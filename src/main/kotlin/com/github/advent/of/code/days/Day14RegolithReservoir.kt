package com.github.advent.of.code.days

import com.github.advent.of.code.Executable
import kotlin.math.pow
import kotlin.math.sqrt

class Day14RegolithReservoir : Executable {
    override fun executePartOne(input: String): Any {
        val reservoirs = parseReservoirs(input)
        val stillSand = simulateSandFallingInAbyss(reservoirs)
        return stillSand.size
    }

    override fun executePartTwo(input: String): Any {
        val reservoirs = parseReservoirs(input)
        val stillSand = simulateSandFallingOnFloor(reservoirs)
        return stillSand.size
    }

    private fun parseReservoirs(input: String): List<Segment> {
        val rawReservoirs = input.lines()

        return rawReservoirs.map { rawReservoir ->
            val rawPoints = rawReservoir.split(" -> ")
            val points = rawPoints.map { rawPoint ->
                val (x, y) = rawPoint.split(',')
                Point(x.toInt(), y.toInt())
            }
            val rawlines = points.zipWithNext()
            val lines = rawlines.map { rawLine ->
                rawLine.first..rawLine.second
            }
            Segment(lines)
        }
    }

    private fun simulateSandFallingInAbyss(reservoirs: List<Segment>): List<Point> {
        val sand = Point(500, 0)
        val stillSand = mutableListOf<Point>()
        var current = sand

        val abyss = findLowestPoint(reservoirs) + 1

        while (true) {
            if (current.down().y >= abyss) {
                break
            }

            current = if (reservoirs.none { reservoir -> current.down() in reservoir } && current.down() !in stillSand) {
                current.down()
            } else if (reservoirs.none { reservoir -> current.down().left() in reservoir } && current.down().left() !in stillSand) {
                current.down().left()
            } else if (reservoirs.none { reservoir -> current.down().right() in reservoir } && current.down().right() !in stillSand) {
                current.down().right()
            } else {
                stillSand.add(current)
                sand
            }
        }

        return stillSand
    }

    private fun simulateSandFallingOnFloor(reservoirs: List<Segment>): List<Point> {
        val sand = Point(500, 0)
        val stillSand = mutableListOf<Point>()
        var current = sand

        val floorY = findLowestPoint(reservoirs) + 2
        val floor = Line(
            Point(-1_000_000.0, floorY),
            Point(1_000_000.0, floorY)
        )
        val reservoirsWithFloor = reservoirs + Segment(listOf(floor))

        while (true) {
            current = if (reservoirsWithFloor.none { reservoir -> current.down() in reservoir } && current.down() !in stillSand) {
                current.down()
            } else if (reservoirsWithFloor.none { reservoir -> current.down().left() in reservoir } && current.down().left() !in stillSand) {
                current.down().left()
            } else if (reservoirsWithFloor.none { reservoir -> current.down().right() in reservoir } && current.down().right() !in stillSand) {
                current.down().right()
            } else {
                stillSand.add(current)

                if (current == sand) {
                    break
                } else {
                    sand
                }
            }
        }

        return stillSand
    }

    private fun findLowestPoint(reservoirs: List<Segment>): Double {
        var max = 0.0

        for (reservoir in reservoirs) {
            for (line in reservoir.lines) {
                if (line.start.y > max) {
                    max = line.start.y
                }
                if (line.end.y > max) {
                    max = line.end.y
                }
            }
        }

        return max
    }

    private data class Point(
        val x: Double,
        val y: Double
    ) {
        constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

        fun distanceTo(other: Point): Double {
            return sqrt((other.x - this.x).pow(2) + (other.y - this.y).pow(2))
        }

        fun right(): Point {
            return Point(x + 1, y)
        }

        fun down(): Point {
            return Point(x, y + 1)
        }

        fun left(): Point {
            return Point(x - 1, y)
        }

        operator fun rangeTo(other: Point): Line {
            return Line(this, other)
        }
    }

    private data class Line(
        val start: Point,
        val end: Point
    ) {
        operator fun contains(value: Point): Boolean {
            return start.distanceTo(value) + end.distanceTo(value) == start.distanceTo(end)
        }
    }

    private data class Segment(
        val lines: List<Line>
    ) {
        operator fun contains(value: Point): Boolean {
            return lines.any { line -> value in line }
        }
    }
}
