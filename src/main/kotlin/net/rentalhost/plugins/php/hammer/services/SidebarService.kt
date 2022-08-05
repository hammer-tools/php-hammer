package net.rentalhost.plugins.php.hammer.services

import com.intellij.openapi.project.Project
import net.rentalhost.plugins.forms.SidebarForm
import net.rentalhost.plugins.hammer.services.SidebarService
import javax.swing.JComponent

class SidebarService: SidebarService() {
    override fun getPanel(project: Project): JComponent = SidebarForm(project).panel
}
