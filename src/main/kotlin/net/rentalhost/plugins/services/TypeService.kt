package net.rentalhost.plugins.services

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl
import org.apache.commons.lang.StringUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.streams.toList

object TypeService {
    private val nullType = prependGlobalNamespace(mutableListOf("null"))

    fun splitTypes(types: String?): Stream<String?> =
        Arrays.stream(StringUtils.split(types, "|"))

    fun exceptNull(types: String?): Stream<String?> =
        splitTypes(types).filter { s: String? -> !nullType.contains(s) }

    fun joinTypes(types: Stream<String?>): String =
        types.collect(Collectors.joining("|"))

    fun isVariadic(element: PsiElement, elementMain: Class<out PsiElement>? = null): Boolean =
        element is PhpPsiElementImpl<*> &&
        element.firstChild.text == "..." &&
        (elementMain == null ||
         elementMain.isInstance(element.firstPsiChild))

    private fun prependGlobalNamespace(types: MutableList<String?>): List<String?> {
        types.addAll(
            types.stream()
                .map { s: String? -> "\\" + s }
                .toList()
        )

        return types
    }
}
