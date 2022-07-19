package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.isEmpty
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.delete
import net.rentalhost.plugins.extensions.psi.getCommaRange
import net.rentalhost.plugins.extensions.psi.isName
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService
import kotlin.streams.toList

class CompactDuplicatedTermsInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("compact"))
                return

            val variables = mutableListOf<String>()

            element.parameters
                .map {
                    when (it) {
                        is ArrayCreationExpressionImpl -> it.values().toList()
                        else -> listOf(it)
                    }
                }
                .forEach { values ->
                    values
                        .filterIsInstance(StringLiteralExpression::class.java)
                        .forEach forEachValues@{
                            if (!variables.contains(it.contents)) {
                                variables.add(it.contents)
                                return@forEachValues
                            }

                            ProblemsHolderService.registerProblem(
                                problemsHolder, it,
                                "Duplicated term in compact().",
                                LocalQuickFixService.SimpleInlineQuickFix("Drop duplicated term") {
                                    val array = PsiTreeUtil.getParentOfType(it, ArrayCreationExpressionImpl::class.java, false, ParameterList::class.java)
                                    val range = when {
                                        array != null -> it.parent
                                        else -> it
                                    }

                                    range.getCommaRange().delete()

                                    if (array != null &&
                                        array.values().isEmpty()) {
                                        array.getCommaRange().delete()
                                    }
                                }
                            )
                        }
                }
        }
    }
}
