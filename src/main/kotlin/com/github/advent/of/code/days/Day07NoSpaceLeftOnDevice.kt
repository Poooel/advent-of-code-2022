package com.github.advent.of.code.days

import com.github.advent.of.code.Executable

class Day07NoSpaceLeftOnDevice : Executable {
    override fun executePartOne(input: String): Any {
        val directoryTree = parseDirectoryTree(input)
        val directoryList = walkDirectoryTree(directoryTree, mutableListOf())

        val smallDirectories = directoryList.filter { it.size() < 100_000 }

        return smallDirectories.sumOf { it.size() }
    }

    override fun executePartTwo(input: String): Any {
        val directoryTree = parseDirectoryTree(input)
        val directoryList = walkDirectoryTree(directoryTree, mutableListOf())

        val totalAvailableDiskSpace = 70_000_000
        val requiredDiskSpaceForUpdate = 30_000_000

        val diskSpaceInUse = directoryTree.size()
        val currentAvailableDiskSpace = totalAvailableDiskSpace - diskSpaceInUse
        val requiredDiskSpaceToFree = requiredDiskSpaceForUpdate - currentAvailableDiskSpace

        return directoryList
            .map { it.size() } // Replace directories with their size as we don't need anything else
            .filter { it > requiredDiskSpaceToFree } // Remove all directories that would be too small to delete
            .minBy { it } // Take the smallest directory that's big enough to delete
    }

    private fun parseDirectoryTree(input: String): Directory {
        val rootDirectory = Directory(name = "/", parent = null)
        var currentDirectory = rootDirectory

        val terminalOutput = input.lines()

        terminalOutput.forEach { terminalLine ->
            if (terminalLine.startsWith('$')) {
                val (prefix, command) = terminalLine.split(' ')

                if (command == "cd") { // Change directory
                    val argument = terminalLine.split(' ').last()

                    if (argument == "..") { // Go back one directory
                        currentDirectory = currentDirectory.getParent()
                    } else if (argument == "/") { // Go back to root directory
                        currentDirectory = rootDirectory
                    } else { // Go to directory specified
                        currentDirectory = currentDirectory.getDirectory(argument)
                    }
                } else if (command == "ls") { // List files
                    // Ignore for now
                } else {
                    throw Error("Not yet supported.")
                }
            } else {
                val (sizeOrType, name) = terminalLine.split(' ')

                if (sizeOrType.isNumber()) { // It's a file
                    currentDirectory.addFile(name, sizeOrType.toLong())
                } else if (sizeOrType == "dir") { // It's a directory
                    currentDirectory.addDirectory(name)
                } else {
                    throw Error("Not yet supported.")
                }
            }
        }

        return rootDirectory
    }

    private fun walkDirectoryTree(rootDirectory: Directory, directories: MutableList<Directory>): MutableList<Directory> {
        rootDirectory.subDirectories.forEach { subDirectory ->
            walkDirectoryTree(subDirectory, directories)
        }

        directories.add(rootDirectory)

        return directories
    }
}

private fun String.isNumber(): Boolean {
    return this.all { it.isDigit() }
}

private data class Directory(
    val name: String,
    val subDirectories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<File> = mutableListOf(),
    private val parent: Directory?
) {
    fun addDirectory(name: String) {
        subDirectories.add(
            Directory(name = name, parent = this)
        )
    }

    fun addFile(name: String, size: Long) {
        files.add(
            File(name, size)
        )
    }

    fun getDirectory(name: String): Directory {
        return subDirectories.first { it.name == name }
    }

    fun getParent(): Directory {
        if (name == "/") {
            throw Error("Root directory has no parent directory.")
        } else {
            return parent!!
        }
    }

    fun size(): Long {
        var count = 0L

        count += subDirectories.sumOf { it.size() }
        count += files.sumOf { it.size }

        return count
    }
}

private data class File(
    val name: String,
    val size: Long
)
