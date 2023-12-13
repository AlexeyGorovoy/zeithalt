package generators

import java.io.File

fun main () {
    println("Started!")
    val startTime = System.currentTimeMillis()

    val files = File("rules")
        .listFiles()

    println("Found files: ${files?.size ?: "none"}")

    val links = files
        ?.filter { it.isFile && it.name.startsWith("rules") }
        ?.sortedBy { file -> file.name }
        ?.flatMap { file ->
            listOf(
                file.readText()
            )
        }
        ?.joinToString("\n")

    val output = File("rules/index.md")
    output.writeText(links ?: "none")

    val endTime = System.currentTimeMillis()
    println("Finished in ${endTime - startTime} ms")
}