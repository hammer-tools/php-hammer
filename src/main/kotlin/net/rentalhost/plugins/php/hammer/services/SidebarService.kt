package net.rentalhost.plugins.php.hammer.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ex.ToolWindowEx
import net.rentalhost.plugins.php.hammer.forms.SidebarForm

class SidebarService: ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = with(toolWindow.contentManager) {
        addContent(factory.createContent(SidebarForm().panel, null, false))

        ApplicationManager.getApplication().invokeLater {
            with(toolWindow as ToolWindowEx) {
                stretchWidth(320 - component.width)
            }
        }
    }
}
