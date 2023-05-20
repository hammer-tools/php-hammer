package net.rentalhost.plugins.php.hammer.services

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.jetbrains.php.lang.psi.elements.ClassReference
import com.jetbrains.php.lang.psi.elements.ConstantReference
import com.jetbrains.php.lang.psi.elements.impl.NewExpressionImpl
import net.rentalhost.plugins.php.hammer.extensions.psi.isExactly

typealias Appender = (String) -> Unit
typealias PreProcessor = (PsiElement, Appender) -> Boolean

object FormatterService {
    private object ElementReassemble {
        fun visit(element: PsiElement, appender: Appender, preProcessor: PreProcessor? = null) {
            if (preProcessor != null && !preProcessor.invoke(element, appender)) {
                return
            }

            when {
                element is PsiComment ||
                element is PsiWhiteSpace -> return

                element is ConstantReference ||
                element is ClassReference ||
                element.isExactly<LeafPsiElement>() -> appender.invoke(element.text.lowercase())

                element is NewExpressionImpl &&
                element.parameters.isEmpty() -> appender.invoke("new " + (element.classReference ?: return).text.lowercase())

                else -> {
                    var child = element.firstChild

                    if (child == null) {
                        appender.invoke(element.text)
                        return
                    }

                    while (child != null) {
                        visit(child, appender, preProcessor)
                        child = child.nextSibling
                    }
                }
            }
        }
    }

    fun normalize(element: PsiElement, preProcessor: PreProcessor? = null): String {
        var elementText = ""

        ElementReassemble.visit(element, { s -> elementText += "$s\u0000" }, preProcessor)

        return elementText
    }
}
