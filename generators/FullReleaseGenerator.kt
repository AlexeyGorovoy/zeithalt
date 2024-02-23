package generators

import java.io.File

enum class Destination(
    val targetRefsRoot: String,
    val targetTimelineRoot: String,
    val refsImgReplacement: String,
    val refsSelfDir: String,
    val refsFromTimelineDir: String,
    val refsImgDir: String,
    val timelineDir: String,
    val timelineMapDir: String,
    val globalRefs: String,
    val globalRefsImg: String,
    val globalTimeline: String,
    val globalTimelineMap: String
) {
    DOCS(
        targetRefsRoot = "docs/refs",
        targetTimelineRoot = "docs/timeline",
        refsImgReplacement = "../../refs/img",
        refsSelfDir = "../refs",
        refsFromTimelineDir = "../refs",
        refsImgDir = "refs/img",
        timelineDir = "../timeline",
        timelineMapDir = "../../timeline/map",
        globalRefs = "https://alexeygorovoy.github.io/zeithalt/refs/",
        globalRefsImg = "https://alexeygorovoy.github.io/zeithalt/refs/img/",
        globalTimeline = "https://alexeygorovoy.github.io/zeithalt/timeline/",
        globalTimelineMap = "https://alexeygorovoy.github.io/zeithalt/timeline/map/"
    ),
    ZEITHALT_REPO(
        targetRefsRoot = "public_refs",
        targetTimelineRoot = "public_timeline",
        refsImgReplacement = "/i",
        refsSelfDir = "",
        refsFromTimelineDir = "../r",
        refsImgDir = "i",
        timelineDir = "../t",
        timelineMapDir = "m",
        globalRefs = "https://zeithalt.github.io/r/",
        globalRefsImg = "https://zeithalt.github.io/r/i/",
        globalTimeline = "https://zeithalt.github.io/t/",
        globalTimelineMap = "https://zeithalt.github.io/t/m/"
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

    if (destination == Destination.DOCS) {
        copyTimeline()
    }
    copyTimelineMap(destination)

    rebuildRefsIndex(destination, linkMap)
    rebuildTimelineIndex(destination, linkMap)
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
//                .replace("../refs/img", destination.refsImgReplacement)
//                .replace("../refs/", "${destination.refsSelfDir}/")
//                .replace("../timeline/", "${destination.timelineDir}/")
//                .replace(".md", ".html")
                .replaceRefsWithGlobalLinks(destination, linkMap)
                .split("<!---")
                .first()
                .plus("\n")
                .plus("----------\n")
                .plus("[⬅️ Back to index](${destination.refsSelfDir}/index.md#${anchor}_s)")

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

private fun copyTimelineMap(
    destination: Destination
) {
    val timelineMap = File("timeline/map")
        .listFiles()
        ?.filter { it.isFile }
        ?.onEach { file ->
            file.copyTo(File("${destination.targetTimelineRoot}/${destination.timelineMapDir}/${file.name}"), overwrite = true)
        }

    println("Copied ${timelineMap?.size ?: "0"} timeline map images")
}