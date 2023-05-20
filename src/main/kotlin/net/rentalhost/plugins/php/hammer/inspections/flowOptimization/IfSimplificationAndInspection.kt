package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ControlStatement
import com.jetbrains.php.lang.psi.elements.Else
import com.jetbrains.php.lang.psi.elements.ElseIf
import com.jetbrains.php.lang.psi.elements.If
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getSingleStatement
import net.rentalhost.plugins.php.hammer.extensions.psi.isAndSimplified
import net.rentalhost.plugins.php.hammer.extensions.psi.isBlade
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class IfSimplificationAndInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    private fun visit(element: ControlStatement) {
      if (element.containingFile.isBlade())
        return

      if (element is If) {
        if (element.elseBranch != null ||
          element.elseIfBranches.isNotEmpty())
          return
      }

      if (!element.isAndSimplified())
        return

      val elementStatement = element.statement ?: return
      val elementChild = elementStatement.getSingleStatement()

      if (elementChild !is If ||
        elementChild.elseBranch != null ||
        elementChild.elseIfBranches.isNotEmpty() ||
        !elementChild.isAndSimplified())
        return

      val elementNext = element.nextPsiSibling

      if (elementNext != null &&
        elementNext.parent === element.parent) {
        if (elementNext is ElseIf ||
          elementNext is Else)
          return
      }

      val elementConditionPointer = (element.condition ?: return).createSmartPointer()
      val elementChildConditionPointer = (elementChild.condition ?: return).createSmartPointer()
      val elementChildStatementPointer = (elementChild.statement ?: return).createSmartPointer()

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        element.firstChild,
        "nested condition can be merged with this",
        QuickFixService.instance.simpleInline("Merge nested conditional") {
          val elementCondition = elementConditionPointer.element ?: return@simpleInline
          val elementChildCondition = elementChildConditionPointer.element ?: return@simpleInline
          val elementChildStatement = elementChildStatementPointer.element ?: return@simpleInline

          elementCondition.replace(
            FactoryService.createBinaryExpression(
              problemsHolder.project,
              "${elementCondition.text}&&${elementChildCondition.text}"
            )
          )

          elementStatement.replace(elementChildStatement)
        }
      )
    }

    override fun visitPhpElseIf(element: ElseIf) = visit(element)
    override fun visitPhpIf(element: If) = visit(element)
  }
}
