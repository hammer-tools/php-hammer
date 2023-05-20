package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.PhpLangUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.*
import javax.swing.JComponent

class ClassnameLiteralInspection: PhpInspection() {
    private val classCheckers = mapOf(
        Pair("class_exists", listOf(0)),
        Pair("class_alias", listOf(1)),
        Pair("interface_exists", listOf(0)),
    )

    @OptionTag
    var includeNonexistentClasses: Boolean = false

    @OptionTag
    var includeClassCheckers: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpStringLiteralExpression(string: StringLiteralExpression) {
            if (!string.contents.contains("\\"))
                return

            val stringParent = string.parent

            if (stringParent is BinaryExpression &&
                !TypeService.compareOperations.contains(stringParent.operationType))
                return

            if (!includeClassCheckers) {
                val stringCaller = stringParent.parent as? FunctionReference ?: return

                for ((functionName, functionArgumentIndexes) in classCheckers) {
                    if (stringCaller.isName(functionName)) {
                        for (functionArgumentIndex in functionArgumentIndexes) {
                            val functionParameter = stringCaller.getParameter(functionArgumentIndex) ?: return

                            if (functionParameter === string) {
                                return
                            }
                        }
                    }
                }
            }

            val stringNormalized = StringService.unescapeString(string)

            if (!LanguageService.isQualifiedClassname(stringNormalized))
                return

            val classname = PhpLangUtil.toFQN(stringNormalized)
            val classReference = ClassService.findFQN(classname, string.project)

            if (!includeNonexistentClasses && classReference == null) {
                return
            }

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                string,
                "string can be replaced by ::class equivalent",
                QuickFixService.instance.simpleReplace(
                    "Replace with ::class equivalent",
                    FactoryService.createClassConstantReference(problemsHolder.project, classReference?.fqn ?: stringNormalized).createSmartPointer()
                )
            )
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Include non-existing classes", includeNonexistentClasses,
                "This option allows the analysis on nonexistent classes. PHP will work fine with <code>::class</code> even if the class doesn't exist though."
            ) { includeNonexistentClasses = it }

            component.addCheckbox(
                "Include class checkers", includeClassCheckers,
                "This option allows the analysis in <code>class_exists()</code>, <code>class_alias()</code> and <code>interface_exists()</code> functions."
            ) { includeClassCheckers = it }
        }
    }
}
