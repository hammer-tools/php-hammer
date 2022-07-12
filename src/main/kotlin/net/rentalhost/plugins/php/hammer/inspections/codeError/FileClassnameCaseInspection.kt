package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl
import net.rentalhost.plugins.extensions.psi.getBasename
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class FileClassnameCaseInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is PhpClassImpl &&
                element.name != "") {
                val file = element.containingFile
                val fileBasename = file.getBasename()

                if (fileBasename != element.name) {
                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element.nameIdentifier ?: return,
                        "Class name (\"${element.name}\") does not match the file that stores it (\"${file.name}\").",
                        LocalQuickFixService.SimpleReplaceQuickFix(
                            "Rename class to match filename",
                            FactoryService.createClassReference(problemsHolder.project, fileBasename)
                        )
                    )
                }
            }
        }
    }
}
