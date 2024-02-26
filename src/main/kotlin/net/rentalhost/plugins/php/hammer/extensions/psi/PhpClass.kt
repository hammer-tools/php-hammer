package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.psi.PhpCodeEditUtil
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.jetbrains.php.lang.psi.elements.PhpClassFieldsList
import com.jetbrains.php.lang.psi.elements.impl.PhpPromotedFieldParameterImpl

fun PhpClass.addField(parameter: PhpPromotedFieldParameterImpl, comment: PhpDocComment? = null): PsiElement =
    PhpPsiElementFactory.createPhpPsiFromText(
        project,
        PhpClassFieldsList::class.java,
        "class A{${parameter.text.substringBefore("=")};}"
    ).apply {
        PhpCodeEditUtil.insertClassMemberWithPhpDoc(this@addField, this, comment)
    }
