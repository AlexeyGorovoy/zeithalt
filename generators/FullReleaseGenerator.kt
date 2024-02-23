package generators

import java.io.File

enum class Destination(
    val targetRefsRoot: String,
    val refsImgReplacement: String,
    val refsDir: String,
    val refsImgDir: String
) {
    DOCS(
        targetRefsRoot = "docs/refs",
        refsImgReplacement = "../../refs/img",
        refsDir = "../refs",
        refsImgDir = "refs/img"
    ),
    ZEITHALT_REPO(
        targetRefsRoot = "public_refs",
        refsImgReplacement = "../i",
        refsDir = "..",
        refsImgDir = "i"
    )
}

fun main() {
    println("Started full release generation!")
    val startTime = System.currentTimeMillis()

    generateFullRelease(Destination.ZEITHALT_REPO)

    val endTime = System.currentTimeMillis()
    println("Finished full release generation in ${endTime - startTime} ms")
}

private fun generateFullRelease(destination: Destination) {
    val linkMap = rebuildLinkMap()

    copyRefs(destination, linkMap)
    copyRefsImg(destination)

    copyTimeline()
    copyTimelineMap()

    rebuildRefsIndex(destination, linkMap)
    rebuildTimelineIndex(linkMap)
}

private fun copyRefs(
    destination: Destination,
    linkMap: Map<String, String>
) {
    val refs = File("refs")
        .listFiles()
        ?.filter { it.isFile }
        ?.onEach { file ->
            val text = file.readText()

            val anchor = linkMap["../refs/${file.name}"] ?: ""

            val updatedImgLinks = text
                .replace("../refs/img", destination.refsImgReplacement)
                .split("<!---")
                .first()
                .plus("\n")
                .plus("----------\n")
                .plus("[⬅️ Back to index](${destination.refsDir}/#${anchor}_s)")

            File("${destination.targetRefsRoot}/${file.name}")
                .writeText(updatedImgLinks)
        }

    println("Copied ${refs?.size ?: "0"} refs entries")
}

private fun copyRefsImg(
    destination: Destination
) {
    val img = File("refs/img")
        .listFiles()
        ?.filter { it.isFile }
        ?.onEach { file ->
            file.copyTo(File("${destination.targetRefsRoot}/${destination.refsImgDir}/${file.name}"), overwrite = true)
        }

    println("Copied ${img?.size ?: "0"} refs images")
}

private fun copyTimeline() {
    val timeline = File("timeline")
        .listFiles()
        ?.filter { it.isFile }
        ?.onEach { file ->
            val text = file.readText()

            val anchor = file.name.replace(".md", "")

            val updatedImgLinks = text
                .split("<!---")
                .first()
                .plus("\n")
                .plus("\n")
                .plus("----------\n")
                .plus("[⬅️ Back to Timeline](../timeline/#${anchor})")

            File("docs/timeline/${file.name}")
                .writeText(updatedImgLinks)
        }

    println("Copied ${timeline?.size ?: "0"} timeline entries")
}

private fun copyTimelineMap() {
    val timelineMap = File("timeline/map")
        .listFiles()
        ?.filter { it.isFile }
        ?.onEach { file ->
            file.copyTo(File("docs/timeline/map/${file.name}"), overwrite = true)
        }

    println("Copied ${timelineMap?.size ?: "0"} timeline map images")
}