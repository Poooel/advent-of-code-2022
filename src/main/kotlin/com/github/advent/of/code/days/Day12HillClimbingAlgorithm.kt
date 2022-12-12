package com.github.advent.of.code.days

import com.github.advent.of.code.Executable
import kotlin.math.abs

class Day12HillClimbingAlgorithm : Executable {
    override fun executePartOne(input: String): Any {
        val heightMap = input.lines()

        val start = findInMap('S', heightMap)
        val goal = findInMap('E', heightMap)

        val arrangedMap = arrangeMap(heightMap)

        val shortestPath = aStar(arrangedMap, start, goal)

        return shortestPath.size - 1
    }

    override fun executePartTwo(input: String): Any {
        val heightMap = input.lines()

        val goal = findInMap('E', heightMap)

        val arrangedMap = arrangeMap(heightMap)

        val starts = findAllInMap('a', arrangedMap)

        val allPaths = starts.associateWith { start ->
            try {
                aStar(arrangedMap, start, goal).size - 1
            } catch (e: Error) {
                Int.MAX_VALUE
            }
        }

        val bestPath = allPaths.minBy { it.value }

        return bestPath.value
    }

    private fun findInMap(target: Char, heightMap: List<String>): Point {
        for (i in heightMap.indices) {
            for (j in heightMap[i].indices) {
                if (heightMap[i][j] == target) {
                    return Point(j, i)
                }
            }
        }

        throw Error("Unable to find target in map.")
    }

    private fun findAllInMap(target: Char, heightMap: List<String>): List<Point> {
        val found = mutableListOf<Point>()

        for (i in heightMap.indices) {
            for (j in heightMap[i].indices) {
                if (heightMap[i][j] == target) {
                    found.add(Point(j, i))
                }
            }
        }

        return found
    }

    private fun arrangeMap(heightMap: List<String>): List<String> {
        return heightMap.map { line ->
            val replacedStart = line.replace('S', 'a')
            val replacedGoal = replacedStart.replace('E', 'z')
            replacedGoal
        }
    }

    private fun reconstructPath(cameFrom: Map<Point, Point>, current: Point): List<Point> {
        val totalPath = mutableListOf(current)
        var localCurrent = current

        while (localCurrent in cameFrom) {
            localCurrent = cameFrom.getValue(localCurrent)
            totalPath.add(0, localCurrent)
        }

        return totalPath
    }

    private fun aStar(heightMap: List<String>, start: Point, goal: Point): List<Point> {
        val openSet = mutableListOf(start)

        val cameFrom = mutableMapOf<Point, Point>()

        val gScore = mutableMapOf(start to 0)

        val fScore = mutableMapOf(start to start.distanceTo(goal))

        while (openSet.isNotEmpty()) {
            val current = openSet.minBy { fScore.getValue(it) }

            if (current == goal) {
                return reconstructPath(cameFrom, current)
            }

            openSet.remove(current)

            val neighors = getNeighbors(heightMap, current)

            neighors.forEach { neighbor ->
                val tentativeGScore = gScore.getValue(current) + current.distanceTo(neighbor)

                if (tentativeGScore < gScore.getOrDefault(neighbor, Int.MAX_VALUE)) {
                    cameFrom[neighbor] = current
                    gScore[neighbor] = tentativeGScore
                    fScore[neighbor] = tentativeGScore + neighbor.distanceTo(goal)

                    if (neighbor !in openSet) {
                        openSet.add(neighbor)
                    }
                }
            }
        }

        throw Error("Unable to reach goal.")
    }

    private fun getNeighbors(heightMap: List<String>, current: Point): List<Point> {
        val possibleNeighbors = listOf(current.up(), current.down(), current.left(), current.right())

        val narrowedDownNeighbors = possibleNeighbors.filterNot { possibleNeighbor ->
            possibleNeighbor.x < 0 ||
                possibleNeighbor.y < 0 ||
                possibleNeighbor.x >= heightMap[0].length ||
                possibleNeighbor.y >= heightMap.size
        }

        return narrowedDownNeighbors.filter { narrowedDownNeighbor ->
            val currentValue = heightMap[current.y][current.x]
            val narrowedDownNeighborValue = heightMap[narrowedDownNeighbor.y][narrowedDownNeighbor.x]

            currentValue >= narrowedDownNeighborValue || currentValue + 1 == narrowedDownNeighborValue
        }
    }

    private data class Point(
        val x: Int,
        val y: Int
    ) {
        fun distanceTo(other: Point): Int {
            return abs(this.x - other.x) + abs(this.y - other.y)
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
}
