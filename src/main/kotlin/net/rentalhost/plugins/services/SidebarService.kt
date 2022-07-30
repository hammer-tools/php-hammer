package net.rentalhost.plugins.services

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import net.rentalhost.plugins.forms.SidebarForm

class SidebarService: ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = with(toolWindow.contentManager) {
        addContent(factory.createContent(SidebarForm(project).panel, null, false))
    }
}
