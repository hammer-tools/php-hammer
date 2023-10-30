package net.rentalhost.plugins.php.hammer.services

import com.intellij.find.findUsages.PsiElement2UsageTargetAdapter
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.usageView.UsageInfo
import com.intellij.usages.UsageInfoToUsageConverter
import com.intellij.usages.UsageViewManager
import com.intellij.usages.UsageViewPresentation

class FindUsageService {
  companion object {
    fun showUsages(
      project: Project,
      elements: Collection<PsiElement>,
      tabTitle: String,
      listTitle: String = tabTitle,
      elementReference: PsiElement? = null
    ) {
      val presentation = UsageViewPresentation().apply {
        targetsNodeText = listTitle
        tabText = tabTitle
      }

      val thisArray = elements.toTypedArray()

      UsageViewManager.getInstance(project)
        .showUsages(
          PsiElement2UsageTargetAdapter.convert(thisArray, true),
          if (elementReference == null) emptyArray()
          else arrayOf(UsageInfoToUsageConverter.convert(thisArray, UsageInfo(elementReference))),
          presentation,
        )
    }
  }
}
