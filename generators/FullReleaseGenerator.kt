package generators

import java.io.File

fun main() {
    println("Started full release generation!")
    val startTime = System.currentTimeMillis()

    generateFullRelease()

    val endTime = System.currentTimeMillis()
    println("Finished full release generation in ${endTime - startTime} ms")
}

private fun generateFullRelease() {
    rebuildRefsIndex()
    copyRefs()
    copyTimeline()
}

private fun copyRefs() {
    val refs = File("refs")
        .listFiles()
        ?.filter { it.isFile }
        ?.onEach { file ->
            val text = file.readText()

            val updatedImgLinks = text
                .replace("../refs/img", "../../refs/img")
                .split("<!---")
                .first()
                .plus("\n")
                .plus("----------\n")
                .plus("[⬅️ Back to index](../refs/index.md)")

            File("docs/refs/${file.name}")
                .writeText(updatedImgLinks)
        }

    println("Copied ${refs?.size ?: "0"} refs entries")
}

private fun copyTimeline() {
    val timeline = File("timeline")
        .listFiles()
        ?.filter { it.isFile }
        ?.onEach { file ->
            val text = file.readText()

            val updatedImgLinks = text
                .replace("../timeline/map", "../../timeline/map")
                .split("<!---")
                .first()
                .plus("\n")
                .plus("\n")
                .plus("----------\n")
                .plus("[⬅️ Back to index](../timeline/index.md)")

            File("docs/timeline/${file.name}")
                .writeText(updatedImgLinks)
        }

    println("Copied ${timeline?.size ?: "0"} timeline entries")
}