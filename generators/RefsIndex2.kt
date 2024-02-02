package generators

import java.io.File

fun main() {
    println("Started!")
    val startTime = System.currentTimeMillis()

    val files = File("refs")
        .listFiles()

    println("Found files: ${files?.size ?: "none"}")

    val topAnchor = "top"

    val linkMap = rebuildLinkMap()

    val letters = mutableSetOf<String>()
    var lastLetter: String? = null

    val entries = files
        ?.filter { it.isFile && !it.name.startsWith("#") && !it.name.startsWith("index") }
        ?.flatMap { file ->

            val text = file.readText()

            val title = file.readLines()
                .firstOrNull()
                ?.replace("##", "")
                ?.trim()

            val textNoTitle = text.lines().drop(1).joinToString("\n")

            val aliases = extractAliases(text)

            val titles = if (title != null) {
                aliases + title
            } else {
                aliases
            }
            val anchor = linkMap["../refs/${file.name}"] ?: ""
            buildEntries(titles, textNoTitle, anchor)
        }
        ?.sortedBy { it.letter }

    val links = entries
        ?.flatMap { entry ->
            val newLetter = entry.letter != lastLetter
            lastLetter = entry.letter
            letters.add(entry.letter)

            listOfNotNull(
                if (newLetter) "### <a id=\"#$topAnchor\">↑</a> ${entry.letter}" else null,
                "<a id=\"${entry.upAnchor}\"></a>[↓ ${entry.title}](#${entry.anchor})",
                ""
            )
        }
        ?.joinToString("\n")


    val content = links ?: "Nothing to show here."

    val reference = letters
        .sorted()
        .joinToString(" ") { letter ->
            "[$letter](#${letter.lowercase()})"
        }

    val sep = "\n----------\n"

    val texts = entries?.joinToString("\n") { entry ->
        "$sep### <a id=\"${entry.anchor}\" href=\"#${entry.letter.lowercase()}\">↑</a> ${entry.title}\n${entry.text}\n"
    }

    val rIndexText = "# <a id=\"$topAnchor\"></a>Zeithalt Lore Book 2\n$reference\n$content\n$texts"
        .replaceLinks(linkMap)

    val output = File("r/index.md")
    output.writeText(rIndexText)

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

private fun buildEntries(
    titles: List<String>,
    text: String,
    anchor: String
): List<Entry2> {
    return titles.mapNotNull { title ->
        val letter = title.firstOrNull {
            it in 'a'..'z' || it in 'A'..'Z' || it in '0' .. '9'
        }?.uppercase()

        if (letter != null) {
            Entry2(
                letter = letter,
                title = title,
                anchor = anchor,
                upAnchor = "${anchor}_s",
                text = text
            )
        } else {
            null
        }
    }
}

private fun String.replaceLinks(linkMap: Map<String, String>): String {
    return linkMap.entries.fold(this) { acc, entry ->
        acc.replace(entry.key, "#${entry.value}")
    }
}

data class Entry2(
    val letter: String,
    val title: String,
    val anchor: String,
    val upAnchor: String,
    val text: String
)