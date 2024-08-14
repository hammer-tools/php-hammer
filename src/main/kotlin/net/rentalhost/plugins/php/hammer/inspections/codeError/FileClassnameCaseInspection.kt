package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiFile
import com.intellij.psi.createSmartPointer
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.PhpLangUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.jetbrains.php.lang.psi.elements.PhpNamespace
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getBasename
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class FileClassnameCaseInspection : PhpInspection() {
    @OptionTag
    var includeNonRootedClasses: Boolean = false

    @OptionTag
    var includeFilesWithMultipleClasses: Boolean = false

    @OptionTag
    var includeFilesWithInvalidIdentifier: Boolean = false

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpClass(element: PhpClass) {
            if (element.name == "")
                return

            if (!includeNonRootedClasses &&
                element.parent.parent !is PsiFile &&
                element.parent.parent !is PhpNamespace
            )
                return

            val file = element.containingFile

            if (!includeFilesWithMultipleClasses && PsiTreeUtil.collectElementsOfType(file, PhpClassImpl::class.java).size > 1)
                return

            val fileBasename = file.getBasename()
            val fileIdentifierValid = PhpLangUtil.isPhpIdentifier(fileBasename)

            if (!includeFilesWithInvalidIdentifier && !fileIdentifierValid)
                return

            if (fileBasename == element.name)
                return

            try {
                ProblemsHolderService.instance.registerProblem(
                    problemsHolder,
                    element.nameIdentifier ?: return,
                    "class name (\"${element.name}\") does not match the file that stores it (\"${file.name}\")",
                    if (fileIdentifierValid) QuickFixService.instance.simpleReplace(
                        "Rename class to match filename",
                        FactoryService.createClassReference(problemsHolder.project, fileBasename).createSmartPointer()
                    )
                    else null
                )
            } catch (_: AssertionError) {
            }
        }
    }

    override fun getOptionsPane(): OptPane {
        return OptPane.pane(
            OptCheckbox(
                "includeNonRootedClasses",
                PlainMessage("Include non-rooted classes"),
                emptyList(),
                HtmlChunk.raw(
                    "This option will allow this inspection to check all classes present in the file. " +
                            "By default, only classes defined at the root of the file are analyzed."
                )
            ),

            OptCheckbox(
                "includeFilesWithMultipleClasses",
                PlainMessage("Include files with multiple classes"),
                emptyList(),
                HtmlChunk.raw(
                    "This option will allow the inspection to analyze files that define multiple classes. " +
                            "By default, only classes with a single class definition are inspected."
                )
            ),

            OptCheckbox(
                "includeFilesWithInvalidIdentifier",
                PlainMessage("Include files with incompatible identifiers"),
                emptyList(),
                HtmlChunk.raw(
                    "This option will allow this inspection to analyze files even if their names cannot be used as class identifiers. " +
                            "Regardless, quick-fix will not be available in these cases naturally."
                )
            )
        )
    }
}
