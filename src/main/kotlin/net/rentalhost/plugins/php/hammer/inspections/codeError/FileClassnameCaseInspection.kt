package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl
import net.rentalhost.plugins.extensions.psi.getBasename
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class FileClassnameCaseInspection: PhpInspection() {
    @OptionTag
    var includeNonRootedClasses: Boolean = false

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is PhpClassImpl &&
                element.name != "") {
                if (!includeNonRootedClasses && element.parent.parent !is PsiFile)
                    return

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

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Include non-rooted classes", includeNonRootedClasses,
                "This option will allow this inspection to check all classes present in the file. By default, only classes defined at the root of the file are analyzed."
            ) { includeNonRootedClasses = it }
        }
    }
}
