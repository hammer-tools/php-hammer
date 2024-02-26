package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.ParameterListImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.insertAfter
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class DangerousExtractInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(functionReference: FunctionReference) {
            if (!functionReference.isName("\\extract"))
                return

            val parameters = functionReference.parameters

            parameters.forEach {
                if (ParameterListImpl.getNameIdentifier(it)?.text.equals("flags", true))
                    return
            }

            if (parameters.size >= 2 && ParameterListImpl.getNameIdentifier(parameters[1]) == null)
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder, functionReference, "dangerous extract() call",
                QuickFixService.instance.simpleInline("Add EXTR_SKIP flag argument (may affect behavior)") {
                    with(parameters[0].insertAfter(PhpPsiElementFactory.createComma(functionReference.project))) {
                        insertAfter(PhpPsiElementFactory.createClassReference(functionReference.project, "\\EXTR_SKIP"))
                    }
                }
            )
        }
    }
}
