package generators

import java.io.File

fun main () {
    println("Started!")
    val startTime = System.currentTimeMillis()

    val files = File("timeline")
        .listFiles()

    println("Found files: ${files?.size ?: "none"}")

    val links = files
        ?.filter { it.isFile && it.name.startsWith("eon") }
        ?.sortedByDescending { file -> file.name }
        ?.flatMap { file ->
            listOf(
                file.readText(),
                "\n-----"
            )
        }
        ?.joinToString("\n")

    println(links)

    val output = File("timeline/full_timeline.md")
    output.writeText(links ?: "none")

    val endTime = System.currentTimeMillis()
    println("Finished in ${endTime - startTime} ms")
}