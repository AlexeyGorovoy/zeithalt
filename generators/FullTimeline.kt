package generators

import java.io.File

fun rebuildTimelineIndex(
    destination: Destination,
    linkMap: Map<String, String>
) {
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
                    .replaceRefsWithGlobalLinks(destination, linkMap),
            )
        }
        ?.joinToString("\n")

    val output = File("${destination.targetTimelineRoot}/index.md")
    output.writeText(links ?: "none")

    val endTime = System.currentTimeMillis()
    println("Finished in ${endTime - startTime} ms")
}

fun String.replaceRefsWithGlobalLinks(
    destination: Destination,
    linkMap: Map<String, String>
): String {
    var result = this
    var startIndex = 0
    while (startIndex < length) {
        val start = result.indexOf("](", startIndex)
        if (start == -1) {
            break
        }

        val end = result.indexOf(")", start)
        val link = result.substring(start + 2, end)

        when {
            link.contains("img") -> {
                /* skip */
            }

            link.contains("#") -> {
                /* skip */
            }

            link.contains("timeline") && link.contains(".md") -> {
                val anchor = linkMap[link] ?: link.split("/").last().replace(".md", "")
                if (start > result.length) {
                    println("oops")
                }
                result = result.replaceRange(start + 2, end, "../timeline/#${anchor}")
            }
        }

        startIndex++
    }
    return result
        .replace("../refs/img/", destination.globalRefsImg)
        .replace("../refs/", destination.globalRefs)
        .replace("../timeline/map/", destination.globalTimelineMap)
        .replace("../timeline/", destination.globalTimeline)
        .replace(".md", ".html")
}
