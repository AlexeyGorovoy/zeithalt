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
        ?.filter { it.isFile && !it.name.startsWith("#") && !it.name.startsWith("index") }
        ?.flatMap { file ->

            val text = file.readText()

            val title = file.readLines()
            .firstOrNull()
            ?.replace("##", "")
            ?.trim()

            val aliases = extractAliases(text)

            val titles = if (title != null) {
                aliases + title
            } else {
                aliases
            }
            buildEntries(titles, file.name)
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

    val output = File("refs/index.md")
    output.writeText("""
# Index 
$reference

$content
    """.trimIndent())

    val endTime = System.currentTimeMillis()
    println("Finished in ${endTime - startTime} ms")
}

@Suppress("UnnecessaryVariable")
private fun extractAliases(refText: String): List<String> {

    val parts = refText.split("<!---")

    val metadata = parts
        .getOrNull(1)
        ?.split("\n")

//    val keywords = metadata
//        ?.find { it.startsWith("keywords:", ignoreCase = true) }
//        ?.replace("keywords:", "")
//        ?.trim()
//        ?.split(", ")
//        .orEmpty()

    val aliases = metadata
        ?.find { it.startsWith("aliases:", ignoreCase = true) }
        ?.replace("aliases:", "")
        ?.trim()
        ?.split(", ")
        ?.filter { it.isNotBlank() }
        .orEmpty()

    return aliases
}

private fun buildEntries(titles: List<String>, filename: String): List<Entry> {
    return titles.mapNotNull { title ->
        val letter = title.firstOrNull {
            it in 'a'..'z' || it in 'A'..'Z' || it in '0' .. '9'
        }?.uppercase()

        if (letter != null) {
            Entry(
                letter = letter,
                title = title,
                link = "[$title](../refs/${filename})"
            )
        } else {
            null
        }
    }
}

data class Entry(
    val letter: String,
    val title: String,
    val link: String
)