package net.rentalhost.plugins.services

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.jetbrains.php.lang.formatter.ui.predefinedStyle.PSR12CodeStyle
import com.jetbrains.php.lang.psi.elements.ClassReference
import com.jetbrains.php.lang.psi.elements.ConstantReference
import com.jetbrains.php.lang.psi.elements.impl.NewExpressionImpl
import net.rentalhost.plugins.extensions.psi.isExactly

object FormatterService {
    private val projectCodeStyle: CodeStyleSettings = CodeStyleSettingsManager().createSettings()

    init {
        PSR12CodeStyle().apply(projectCodeStyle)
    }

    private object ElementReassemble {
        fun visit(element: PsiElement, appender: (String) -> Unit) {
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
                        visit(child, appender)
                        child = child.nextSibling
                    }
                }
            }
        }
    }

    fun normalize(element: PsiElement): String {
        var elementText = ""

        ElementReassemble.visit(element) { s -> elementText += "$s\u0000" }

        return elementText
    }
}
