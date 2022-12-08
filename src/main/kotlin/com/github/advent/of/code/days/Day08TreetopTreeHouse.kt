package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day08TreetopTreeHouse : Executable {
    override fun executePartOne(input: String): Any {
        return countVisibleTrees(input.lines())
    }

    override fun executePartTwo(input: String): Any {
        return getMaxScenicScore(input.lines())
    }

    private fun countVisibleTrees(map: List<String>): Int {
        var visibleTrees = 0

        visibleTrees += map.size * 2
        visibleTrees += (map[0].length - 2) * 2

        for (y in 1 until map.size - 1) {
            for (x in 1 until map[0].length - 1) {
                if (isTreeVisibleFromRight(map, x, y, map[y][x].digitToInt())) {
                    visibleTrees += 1
                } else if (isTreeVisibleFromLeft(map, x, y, map[y][x].digitToInt())) {
                    visibleTrees += 1
                } else if (isTreeVisibleFromTop(map, x, y, map[y][x].digitToInt())) {
                    visibleTrees += 1
                } else if (isTreeVisibleFromBottom(map, x, y, map[y][x].digitToInt())) {
                    visibleTrees += 1
                }
            }
        }

        return visibleTrees
    }

    private fun getMaxScenicScore(map: List<String>): Int {
        var maxScenicScore = 0

        for (y in 1 until map.size - 1) {
            for (x in 1 until map[0].length - 1) {
                val scenicScore = getScenicScoreForRight(map, x, y, map[y][x].digitToInt()) *
                    getScenicScoreForLeft(map, x, y, map[y][x].digitToInt()) *
                    getScenicScoreForTop(map, x, y, map[y][x].digitToInt()) *
                    getScenicScoreForBottom(map, x, y, map[y][x].digitToInt())

                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore
                }
            }
        }

        return maxScenicScore
    }

    private fun isTreeVisibleFromTop(map: List<String>, x: Int, y: Int, treeHeight: Int): Boolean {
        for (i in y - 1 downTo 0) {
            if (map[i][x].digitToInt() >= treeHeight) {
                return false
            }
        }

        return true
    }

    private fun isTreeVisibleFromBottom(map: List<String>, x: Int, y: Int, treeHeight: Int): Boolean {
        for (i in y + 1 until map.size) {
            if (map[i][x].digitToInt() >= treeHeight) {
                return false
            }
        }

        return true
    }

    private fun isTreeVisibleFromRight(map: List<String>, x: Int, y: Int, treeHeight: Int): Boolean {
        for (i in x + 1 until map[0].length) {
            if (map[y][i].digitToInt() >= treeHeight) {
                return false
            }
        }

        return true
    }

    private fun isTreeVisibleFromLeft(map: List<String>, x: Int, y: Int, treeHeight: Int): Boolean {
        for (i in x - 1 downTo 0) {
            if (map[y][i].digitToInt() >= treeHeight) {
                return false
            }
        }

        return true
    }

    private fun getScenicScoreForTop(map: List<String>, x: Int, y: Int, treeHeight: Int): Int {
        var scenicScore = 0

        for (i in y - 1 downTo 0) {
            if (map[i][x].digitToInt() < treeHeight) {
                scenicScore += 1
            } else {
                scenicScore += 1
                break
            }
        }

        return scenicScore
    }

    private fun getScenicScoreForBottom(map: List<String>, x: Int, y: Int, treeHeight: Int): Int {
        var scenicScore = 0

        for (i in y + 1 until map.size) {
            if (map[i][x].digitToInt() < treeHeight) {
                scenicScore += 1
            } else {
                scenicScore += 1
                break
            }
        }

        return scenicScore
    }

    private fun getScenicScoreForRight(map: List<String>, x: Int, y: Int, treeHeight: Int): Int {
        var scenicScore = 0

        for (i in x + 1 until map[0].length) {
            if (map[y][i].digitToInt() < treeHeight) {
                scenicScore += 1
            } else {
                scenicScore += 1
                break
            }
        }

        return scenicScore
    }

    private fun getScenicScoreForLeft(map: List<String>, x: Int, y: Int, treeHeight: Int): Int {
        var scenicScore = 0

        for (i in x - 1 downTo 0) {
            if (map[y][i].digitToInt() < treeHeight) {
                scenicScore += 1
            } else {
                scenicScore += 1
                break
            }
        }

        return scenicScore
    }
}
