package net.rentalhost.plugins.gradle.services

import org.jsoup.Jsoup
import org.jsoup.nodes.DataNode
import org.jsoup.nodes.Element
import org.jsoup.nodes.Entities
import org.jsoup.nodes.TextNode
import org.jsoup.parser.Parser
import java.io.File
import java.util.*

object HTMLService {
    private val removeIndentRegex = Regex("^\\h+", RegexOption.MULTILINE)

    private fun convertTagToMarkdown(element: Element, tag: String, wrapper: (String) -> String) = with(element.select(tag)) {
        forEach { it.replaceWith(DataNode(wrapper.invoke(it.text()))) }
    }

    fun toMarkdown(file: File): String =
        with(Jsoup.parse(file)) {
            with(outputSettings()) {
                prettyPrint(false)
                escapeMode(Entities.EscapeMode.xhtml)
            }

            val body = body()
            val codes = mutableMapOf<String, String>()

            body.select("br").remove()
            body.select("pre > code").unwrap()

            with(body.select("pre")) {
                forEach {
                    with("{{${UUID.randomUUID()}}}") {
                        codes[this] = Parser.unescapeEntities(it.html(), false)
                        it.replaceWith(TextNode("```php\n$this\n```"))
                    }
                }
            }

            convertTagToMarkdown(body, "strong") { "**$it**" }
            convertTagToMarkdown(body, "code") { "`` $it ``" }

            var result = body.html()
                .replace(removeIndentRegex, "")
                .trim()

            codes.forEach { result = result.replace(it.key, it.value) }

            return result
        }
}
