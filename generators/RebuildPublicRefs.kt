package generators

import java.io.File

fun rebuildRefsIndex(
    destination: Destination,
    linkMap: Map<String, String>
) {
    println("Started!")
    val startTime = System.currentTimeMillis()

    val files = File("refs")
        .listFiles()

    println("Found files: ${files?.size ?: "none"}")

    val topAnchor = "top"

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
                listOf(title) + aliases
            } else {
                aliases
            }
            val anchor = linkMap["../refs/${file.name}"] ?: ""
            buildEntries("${destination.refsSelfDir}/${file.name}", titles, textNoTitle, anchor)
        }
        ?.sortedBy { it.letter }

    val links = entries
        ?.flatMap { entry ->
            val newLetter = entry.letter != lastLetter
            lastLetter = entry.letter
            letters.add(entry.letter)

            listOfNotNull(
                if (newLetter) "### <a id=\"#$topAnchor\"></a>${entry.letter}" else null,
                "▪️ <a id=\"${entry.upAnchor}\"></a>[${entry.title}](${entry.filename})",
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

    val rIndexText = "# <a id=\"$topAnchor\"></a>Zeithalt Lore Book\n$reference\n$content\n"

    val output = File("${destination.targetRefsRoot}/index.md")
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
    filename: String,
    titles: List<String>,
    text: String,
    anchor: String
): List<Entry2> {
    val firstTitle = titles.firstOrNull()
    return titles.mapIndexedNotNull { index, title ->
        val letter = title.firstOrNull {
            it in 'a'..'z' || it in 'A'..'Z' || it in '0' .. '9'
        }?.uppercase()

        val actualText = if (index == 0) {
            text
        } else {
            "Refers to <a href=\"#$anchor\">$firstTitle</a>"
        }

        if (letter != null) {
            Entry2(
                filename = filename,
                letter = letter,
                title = title,
                anchor = anchor,
                upAnchor = "${anchor}_s",
                text = actualText
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
    val filename: String,
    val letter: String,
    val title: String,
    val anchor: String,
    val upAnchor: String,
    val text: String
)