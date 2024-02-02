package generators

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    rebuildLinkMap()
}

fun rebuildLinkMap(): Map<String, String> {
    println("Rebuilding link map!")

    val files = File("refs")
        .listFiles()
        ?.filter { it.name.contains(".md") }

    val linkMapFile = File("meta/.link_map").apply {
        if (!exists()) {
            createNewFile()
        }
    }

    println("Found files: ${files?.size ?: "none"}")

    val linksSet = mutableSetOf<String>()
    val linkMap = linkMapFile
        .readLines()
        .associate {
            val (link, shortened) = it.split(" -> ")
            linksSet.add(shortened)
            link to shortened
        }
        .toMutableMap()

    files?.forEach {
        val ref = "../refs/${it.name}"

        if (linkMap.containsKey(ref)) {
            return@forEach
        } else {
            println("Processing ${it.name}")
        }

        var shortened = md5Hash(it.name)
        var counter = 0
        do {
            shortened = shortened.take(3) + counter++
        } while(shortened in linksSet)

        linksSet.add(shortened)
        linkMap[ref] = shortened
    }

    val linkMapFileText = linkMap.entries.joinToString(separator = "\n") {
        "${it.key} -> ${it.value}"
    }

    linkMapFile.writeText(linkMapFileText)

    return linkMap
}

private val md = MessageDigest.getInstance("MD5")

private fun md5Hash(str: String): String {
    val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
    return String.format("%032x", bigInt)
}