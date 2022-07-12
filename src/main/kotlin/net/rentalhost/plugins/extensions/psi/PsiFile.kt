package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiFile

fun PsiFile.getBasename(): String =
    name.substringBefore(".")
