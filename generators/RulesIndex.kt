package generators

import java.io.File

fun main () {
    println("Started!")
    val startTime = System.currentTimeMillis()

    val files = File("rules")
        .listFiles()

    println("Found files: ${files?.size ?: "none"}")

    val fileToAnchor = mutableMapOf<String, String>()

    val indexText = files
        ?.filter { it.isFile && it.name.startsWith("rules") }
        ?.sortedBy { file -> file.name }
        ?.flatMap { file ->
            val text = file.readText()
            fileToAnchor[file.name] = text.lines().first()
                .replace("##", "")
                .trim()
                .replace(" ", "-")
                .replace(".", "")
                .lowercase()
            listOf(
                file.readText()
            )
        }
        ?.joinToString("\n")
        ?: return

    val withSelfAnchors = fileToAnchor.entries.fold(indexText) { acc, entry ->
        acc.replace("../rules/${entry.key}", "#${entry.value}")
    }

    val output = File("rules/index.md")
    output.writeText(withSelfAnchors)

    val endTime = System.currentTimeMillis()
    println("Finished in ${endTime - startTime} ms")
}