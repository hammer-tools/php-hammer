package net.rentalhost.plugins.php.hammer.services

import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.PhpReference
import com.jetbrains.php.lang.psi.elements.PhpTypedElement
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import java.util.stream.Collectors
import java.util.stream.Stream

object TypeService {
    private val nullType = prependGlobalNamespace(mutableListOf("null"))

    val nativeTypes: List<String> = listOf(
        PhpType._STRING,
        PhpType._INT, PhpType._INTEGER,
        PhpType._BOOL, PhpType._BOOLEAN,
        PhpType._FLOAT, PhpType._DOUBLE,
        PhpType._ARRAY
    )

    var compareOperations: TokenSet =
        TokenSet.create(
            *arrayOf(
                PhpTokenTypes.opEQUAL,
                PhpTokenTypes.opNOT_EQUAL,
                PhpTokenTypes.opIDENTICAL,
                PhpTokenTypes.opNOT_IDENTICAL
            )
        )

    fun splitTypes(types: String?): Stream<String> =
        if (types.isNullOrEmpty()) Stream.empty()
        else Stream.of(*StringUtil.split(types, "|").toTypedArray())

    fun exceptNull(types: String?): Stream<String?> =
        splitTypes(types).filter { s: String? -> !nullType.contains(s) }

    fun joinTypes(types: Stream<String?>): String =
        types.collect(Collectors.joining("|"))

    fun isVariadic(element: PsiElement, elementMain: Class<out PsiElement>? = null): Boolean =
        element is PhpPsiElementImpl<*> &&
                element.firstChild.text == "..." &&
                (elementMain == null ||
                        elementMain.isInstance(element.firstPsiChild))

    fun getType(element: PsiElement) =
        when (element) {
            is PhpReference -> element.resolveLocalType()
            is PhpTypedElement -> element.type
            else -> null
        }

    private fun prependGlobalNamespace(types: MutableList<String?>): List<String?> {
        types.addAll(
            types.stream()
                .map { s: String? -> "\\" + s }
                .toList()
        )

        return types
    }
}
