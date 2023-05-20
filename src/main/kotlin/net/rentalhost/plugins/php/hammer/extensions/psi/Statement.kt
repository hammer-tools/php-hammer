package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.PhpExit
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.Statement
import com.jetbrains.php.lang.psi.elements.impl.PhpThrowImpl
import net.rentalhost.plugins.php.hammer.services.FactoryService

fun Statement.getSingleStatement(): PsiElement? =
    if (this !is GroupStatement) this
    else if (this.statements.size == 1) this.statements.first()
    else null

fun Statement.isTerminatingStatement(): Boolean = with(this.unwrapStatement()) {
    this is PhpReturn ||
    this is PhpThrowImpl ||
    this is PhpExit
}

fun Statement.rebuild(): PsiElement =
    replace(FactoryService.createStatement(this.project, text))
