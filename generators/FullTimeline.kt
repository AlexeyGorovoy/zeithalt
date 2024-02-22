package generators

import java.io.File

fun main() {
    rebuildTimelineIndex(rebuildLinkMap())
}

fun rebuildTimelineIndex (linkMap: Map<String, String>) {
    println("Started!")
    val startTime = System.currentTimeMillis()

    val files = File("timeline")
        .listFiles()

    println("Found files: ${files?.size ?: "none"}")

    val links = files
        ?.filter { it.isFile && it.name.startsWith("eon") }
        ?.sortedByDescending { file -> file.name }
        ?.flatMap { file ->
            val ownAnchor = file.name.replace(".md", "")
            listOf(
                file.readText()
                    .split("<!--")
                    .first()
                    .replace("## ", "## <a id=\"${ownAnchor}\"></a>")
                    .apply {
                           var startIndex = 0
                        while (startIndex < length) {
                            val start = indexOf("](", startIndex)
                            if (start == -1) {
                                break
                            }
                            val end = indexOf(")", start)
                            val link = substring(start + 3, end)

                            when {
                                link.contains("img") -> {
                                    /* skip */
                                }
                                link.contains("refs") -> {
                                    val anchor = linkMap[link] ?: ""
                                    replaceRange(start + 3, end, "../refs/#${anchor})")
                                }
                            }

                            startIndex = end
                        }
                    },
            )
        }
        ?.joinToString("\n")

    val output = File("docs/timeline/index.md")
    output.writeText(links ?: "none")

    val endTime = System.currentTimeMillis()
    println("Finished in ${endTime - startTime} ms")
}