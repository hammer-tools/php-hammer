package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isOperatorEqual
import net.rentalhost.plugins.php.hammer.extensions.psi.isOperatorNotEqual
import net.rentalhost.plugins.php.hammer.extensions.psi.unparenthesize
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.TypeService

class StrictComparisonInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpBinaryExpression(element: BinaryExpression) {
      if (!element.isOperatorEqual() && !element.isOperatorNotEqual())
        return

      val elementLeft = element.leftOperand.unparenthesize() ?: return
      val elementRight = element.rightOperand.unparenthesize() ?: return

      val elementLeftType = TypeService.getType(elementLeft) ?: return
      val elementRightType = TypeService.getType(elementRight) ?: return

      if (elementLeftType != elementRightType)
        return

      if (elementLeftType.isAmbiguous || elementRightType.isAmbiguous)
        return

      if (elementLeftType.size() != 1 || elementRightType.size() != 1)
        return

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        element.operation!!,
        "strict comparison can be used safely here",
      )
    }
  }
}
