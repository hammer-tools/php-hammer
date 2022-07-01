package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.PhpPsiElementFactory

object LeafService {
    fun createColon(project: Project): PsiElement =
        PhpPsiElementFactory.createColon(project)

    fun createSemicolon(project: Project): PsiElement =
        PhpPsiElementFactory.createFromText(project, PhpTokenTypes.opSEMICOLON, ";")
}
