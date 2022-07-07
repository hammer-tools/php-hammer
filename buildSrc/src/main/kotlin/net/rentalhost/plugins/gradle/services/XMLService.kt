package net.rentalhost.plugins.gradle.services

import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

object XMLService {
    fun parse(file: File): Document =
        with(DocumentBuilderFactory.newInstance()) {
            isIgnoringComments = true
            isIgnoringElementContentWhitespace = true
            isValidating = false

            return newDocumentBuilder().parse(file)
        }
}
