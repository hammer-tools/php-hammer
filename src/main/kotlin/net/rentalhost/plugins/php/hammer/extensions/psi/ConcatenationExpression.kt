package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.ConcatenationExpression

private fun addElementsFromOperand(elements: MutableList<PsiElement>, operand: PsiElement?) {
    if (operand is ConcatenationExpression) elements.addAll(operand.getConcatenatedElements())
    else if (operand != null) elements.add(operand.unparenthesize() ?: return)
}

fun ConcatenationExpression.getConcatenatedElements(): List<PsiElement> {
    val elements = mutableListOf<PsiElement>()

    addElementsFromOperand(elements, this.leftOperand.unparenthesize())
    addElementsFromOperand(elements, this.rightOperand.unparenthesize())

    return elements
}
