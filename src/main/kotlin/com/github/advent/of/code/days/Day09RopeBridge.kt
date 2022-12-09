package com.github.advent.of.code.days

import com.github.advent.of.code.Executable
import kotlin.math.pow
import kotlin.math.sqrt

class Day09RopeBridge : Executable {
    override fun executePartOne(input: String): Any {
        return simulateRope(input.lines(), 2)
    }

    override fun executePartTwo(input: String): Any {
        return simulateRope(input.lines(), 10)
    }

    private fun simulateRope(motions: List<String>, knots: Int): Int {
        val pointsVisitedByTail = mutableSetOf<Point>()

        val rope = MutableList(knots) { Point(0.0, 0.0) }

        pointsVisitedByTail.add(rope.last())

        motions.forEach { motion ->
            val (direction, steps) = motion.split(' ')

            for (step in 0 until steps.toInt()) {
                rope[0] = when (direction) {
                    "U" -> rope[0].up()
                    "R" -> rope[0].right()
                    "D" -> rope[0].down()
                    "L" -> rope[0].left()
                    else -> throw Error("Unsupported direction")
                }

                for (i in 1 until rope.size) {
                    val head = rope[i - 1]
                    val knot = rope[i]

                    if (knot.distanceTo(head) >= 2) {
                        when {
                            head.x == knot.x && head.y > knot.y -> { // Up
                                rope[i] = rope[i].up()
                            }
                            head.x == knot.x && head.y < knot.y -> { // Down
                                rope[i] = rope[i].down()
                            }
                            head.x > knot.x && head.y == knot.y -> { // Right
                                rope[i] = rope[i].right()
                            }
                            head.x < knot.x && head.y == knot.y -> { // Left
                                rope[i] = rope[i].left()
                            }
                            head.x > knot.x && head.y > knot.y -> { // Up Right
                                rope[i] = rope[i].up()
                                rope[i] = rope[i].right()
                            }
                            head.x > knot.x && head.y < knot.y -> { // Down Right
                                rope[i] = rope[i].down()
                                rope[i] = rope[i].right()
                            }
                            head.x < knot.x && head.y > knot.y -> { // Up Left
                                rope[i] = rope[i].up()
                                rope[i] = rope[i].left()
                            }
                            head.x < knot.x && head.y < knot.y -> { // Down Left
                                rope[i] = rope[i].down()
                                rope[i] = rope[i].left()
                            }
                        }

                        if (i == rope.lastIndex) { // Is the knot the tail
                            pointsVisitedByTail.add(rope[i])
                        }
                    }
                }
            }
        }

        return pointsVisitedByTail.size
    }
}

private data class Point(
    val x: Double,
    val y: Double
) {
    fun distanceTo(other: Point): Double {
        return sqrt((other.x - this.x).pow(2) + (other.y - this.y).pow(2))
    }

    fun up(): Point {
        return Point(x, y + 1)
    }

    fun right(): Point {
        return Point(x + 1, y)
    }

    fun down(): Point {
        return Point(x, y - 1)
    }

    fun left(): Point {
        return Point(x - 1, y)
    }
}
