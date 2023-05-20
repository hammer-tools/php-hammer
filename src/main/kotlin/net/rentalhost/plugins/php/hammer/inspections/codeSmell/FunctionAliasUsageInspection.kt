package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.LanguageService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class FunctionAliasUsageInspection: PhpInspection() {
    @Suppress("SpellCheckingInspection")
    private val aliasedFunctions = mapOf(
        Pair("\\diskfreespace", "disk_free_space"),
        Pair("\\ftp_quit", "ftp_close"),
        Pair("\\get_required_files", "get_included_files"),
        Pair("\\getdir", "dir"),
        Pair("\\is_writeable", "is_writable"),
        Pair("\\pcntl_errno", "pcntl_get_last_error"),
        Pair("\\posix_errno", "posix_get_last_error"),
        Pair("\\read_exif_data", "exif_read_data"),
        Pair("\\session_commit", "session_write_close"),
        Pair("\\socket_getopt", "socket_get_option"),
        Pair("\\socket_setopt", "socket_set_option"),
        Pair("\\srand", "mt_srand"),
        Pair("\\user_error", "trigger_error"),
    )

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(function: FunctionReference) {
            val functionName = (function.fqn ?: return).lowercase()
            val functionTarget = aliasedFunctions[functionName] ?: return

            @Suppress("SpellCheckingInspection")
            if (functionName == "\\srand" && !LanguageService.atLeast(problemsHolder.project, PhpLanguageLevel.PHP710))
                return

            val functionIdentifier = (function.nameNode ?: return).psi

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                function,
                function.firstChild,
                functionIdentifier,
                "using function alias",
                QuickFixService.instance.simpleReplace(
                    "Replace with target function", functionIdentifier.createSmartPointer(),
                    FactoryService.createFunctionIdentifier(problemsHolder.project, functionTarget).createSmartPointer()
                )
            )
        }
    }
}
