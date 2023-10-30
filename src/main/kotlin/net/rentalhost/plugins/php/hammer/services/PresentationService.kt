package net.rentalhost.plugins.php.hammer.services

import com.intellij.codeInspection.CommonProblemDescriptor
import com.intellij.codeInspection.ex.GlobalInspectionContextImpl
import com.intellij.codeInspection.ex.InspectionToolWrapper
import com.intellij.codeInspection.ui.DefaultInspectionToolPresentation
import net.rentalhost.plugins.php.hammer.interfaces.QuickIntentionAction

@Suppress("UnstableApiUsage")
class PresentationService(
  toolWrapper: InspectionToolWrapper<*, *>,
  context: GlobalInspectionContextImpl
) : DefaultInspectionToolPresentation(toolWrapper, context) {
  private fun isQuickIntentionAction(): Boolean {
    this.problemDescriptors.forEach { problemDescriptor ->
      problemDescriptor.fixes?.forEach { fix ->
        if (fix !is QuickIntentionAction)
          return false
      }
    }

    return true
  }

  override fun showProblemCount(): Boolean {
    if (isQuickIntentionAction())
      return false

    return super.showProblemCount()
  }

  override fun resolveProblem(descriptor: CommonProblemDescriptor) {
    if (isQuickIntentionAction())
      return

    super.resolveProblem(descriptor)
  }
}
