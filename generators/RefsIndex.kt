package generators

import java.io.File

fun main() {
    println("Started!")
    val startTime = System.currentTimeMillis()

    val files = File("refs")
        .listFiles()

    println("Found files: ${files?.size ?: "none"}")

    val letters = mutableSetOf<String>()
    var lastLetter: String? = null

    val links = files
        ?.filter { it.isFile && !it.name.startsWith("_") }
        ?.mapNotNull { file ->

            val title = file.readLines()
            .firstOrNull()
            ?.replace("##", "")
            ?.trim()

            val letter = title?.firstOrNull {
                it in 'a'..'z' || it in 'A'..'Z' || it in '0' .. '9'
            }?.uppercase()

            if (letter != null) {
                Entry(
                    letter = letter,
                    title = title,
                    link = "[$title](${file.name})"
                )
            } else {
                null
            }
        }
        ?.sortedBy { it.letter }
        ?.flatMap { entry ->
            val newLetter = entry.letter != lastLetter
            lastLetter = entry.letter
            letters.add(entry.letter)

            listOfNotNull(
                if (newLetter) "### ${entry.letter}" else null,
                entry.link,
                ""
            )
        }
        ?.joinToString("\n")


    val content = links ?: "Nothing to show here."

    val reference = letters
        .sorted()
        .joinToString(" ") { letter ->
            "[$letter](#${letter})"
        }

    val output = File("refs/_index.md")
    output.writeText("""
# Index 
=================
$reference

$content
    """.trimIndent())

    val endTime = System.currentTimeMillis()
    println("Finished in ${endTime - startTime} ms")
}

data class Entry(
    val letter: String,
    val title: String,
    val link: String
)