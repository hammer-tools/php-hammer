package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ConcatenationExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.Include
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.followContents
import net.rentalhost.plugins.extensions.psi.getConcatenatedElements
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.StringService

class BackslashFilenameUsageInspection: PhpInspection() {
    @Suppress("SpellCheckingInspection")
    private val filesystemFunctions = mapOf<String, List<Int>?>(
        Pair("\\basename", listOf(0)),
        Pair("\\chdir", listOf(0)),
        Pair("\\chgrp", listOf(0)),
        Pair("\\chmod", listOf(0)),
        Pair("\\chown", listOf(0)),
        Pair("\\chroot", listOf(0)),
        Pair("\\clearstatcache", listOf(1)),
        Pair("\\copy", listOf(0, 1)),
        Pair("\\dir", listOf(0)),
        Pair("\\dirname", listOf(0)),
        Pair("\\disk_free_space", listOf(0)),
        Pair("\\disk_free_space", listOf(0)),
        Pair("\\disk_total_space", listOf(0)),
        Pair("\\diskfreespace", listOf(0)),
        Pair("\\file", listOf(0)),
        Pair("\\file_exists", listOf(0)),
        Pair("\\file_get_contents", listOf(0)),
        Pair("\\file_put_contents", listOf(0)),
        Pair("\\fileatime", listOf(0)),
        Pair("\\filectime", listOf(0)),
        Pair("\\filegroup", listOf(0)),
        Pair("\\fileinode", listOf(0)),
        Pair("\\filemtime", listOf(0)),
        Pair("\\fileowner", listOf(0)),
        Pair("\\fileperms", listOf(0)),
        Pair("\\filesize", listOf(0)),
        Pair("\\filetype", listOf(0)),
        Pair("\\fopen", listOf(0)),
        Pair("\\glob", listOf(0)),
        Pair("\\is_dir", listOf(0)),
        Pair("\\is_executable", listOf(0)),
        Pair("\\is_file", listOf(0)),
        Pair("\\is_link", listOf(0)),
        Pair("\\is_readable", listOf(0)),
        Pair("\\is_uploaded_file", listOf(0)),
        Pair("\\is_writable", listOf(0)),
        Pair("\\is_writeable", listOf(0)),
        Pair("\\lchgrp", listOf(0)),
        Pair("\\lchown", listOf(0)),
        Pair("\\link", listOf(0, 1)),
        Pair("\\linkinfo", listOf(0)),
        Pair("\\lstat", listOf(0)),
        Pair("\\mkdir", listOf(0)),
        Pair("\\move_uploaded_file", listOf(0, 1)),
        Pair("\\opendir", listOf(0)),
        Pair("\\parse_ini_file", listOf(0)),
        Pair("\\pathinfo", listOf(0)),
        Pair("\\readfile", listOf(0)),
        Pair("\\readlink", listOf(0)),
        Pair("\\realpath", listOf(0)),
        Pair("\\rename", listOf(0, 1)),
        Pair("\\rmdir", listOf(0)),
        Pair("\\scandir", listOf(0)),
        Pair("\\stat", listOf(0)),
        Pair("\\symlink", listOf(0, 1)),
        Pair("\\tempnam", listOf(0)),
        Pair("\\touch", listOf(0)),
        Pair("\\unlink", listOf(0)),
    )

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        private fun processParameter(parameter: PsiElement) {
            val parameterStrings = when (parameter) {
                is StringLiteralExpression ->
                    listOf(parameter)

                is ConcatenationExpression ->
                    parameter.getConcatenatedElements()
                        .filterIsInstance(StringLiteralExpression::class.java)

                else -> return
            }

            for (parameterString in parameterStrings) {
                val stringContents = StringService.unescapeString(parameterString)

                if (!stringContents.contains("\\"))
                    continue

                ProblemsHolderService.registerProblem(
                    problemsHolder, parameterString,
                    "Using backslash on filesystem-related name",
                    LocalQuickFixService.SimpleInlineQuickFix("Replace backslash") {
                        parameterString.updateText(stringContents.replace("\\", "/"))
                    }
                )
            }
        }

        override fun visitPhpFunctionCall(function: FunctionReference) {
            (filesystemFunctions[(function.fqn ?: return).lowercase()] ?: return)
                .mapNotNull { function.getParameter(it)?.followContents() }
                .forEach { processParameter(it) }
        }

        override fun visitPhpInclude(include: Include) {
            processParameter((include.argument ?: return).followContents())
        }
    }
}
