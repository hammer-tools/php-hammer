package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isGeneratorComplex
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.extensions.psi.isVariadicPreceded
import net.rentalhost.plugins.php.hammer.extensions.psi.unpackValues
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class FunctionSpreadingInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunctionCall(function: FunctionReference) {
      if (!function.isName("array_merge") ||
        function.parameters.size < 2) return

      function.parameters.forEach {
        if (it.isVariadicPreceded()) return
        if (it is FunctionReferenceImpl && it.isGeneratorComplex()) return
      }

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        function,
        "function ${function.name}() can be replaced with spread",
        QuickFixService.instance.simpleReplace(
          "Replace with spread",
          PhpPsiElementFactory.createPhpPsiFromText(
            function.project, ArrayCreationExpression::class.java, "[${
              PhpPsiElementFactory.createPhpPsiFromText(
                function.project,
                ArrayCreationExpression::class.java,
                "[${function.parameters.joinToString(",") { e -> "...${e.text}" }}]"
              ).unpackValues().joinToString(",") { e -> e.text }
            }]"
          ).createSmartPointer()
        )
      )
    }
  }

  override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP740
}
