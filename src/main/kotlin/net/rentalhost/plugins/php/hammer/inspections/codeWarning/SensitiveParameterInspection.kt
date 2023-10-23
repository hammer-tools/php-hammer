package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import ai.grazie.utils.capitalize
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.OptStringList
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.util.findParentOfType
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.Parameter
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.kotlin.joinToStringEx
import net.rentalhost.plugins.php.hammer.extensions.psi.addAttribute
import net.rentalhost.plugins.php.hammer.extensions.psi.getTypes
import net.rentalhost.plugins.php.hammer.services.CacheService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

val wordExpression = Regex("(\\p{Lu}\\p{Ll}+|\\p{Lu}+|\\p{Ll}+|\\p{L}+)")

val ignorableTypes = listOf(
  PhpType._NULL,
  PhpType._INT, PhpType._INTEGER,
  PhpType._BOOL, PhpType._BOOLEAN,
  PhpType._FLOAT, PhpType._DOUBLE,
)

class SensitiveParameterInspection : PhpInspection() {
  @OptionTag
  private val sensitiveWords = mutableListOf(
    "access",
    "account",
    "address",
    "api",
    "auth",
    "authentication",
    "bank",
    "bearer",
    "card",
    "ccn",
    "cert",
    "certificate",
    "code",
    "credential",
    "credit",
    "email",
    "first name",
    "firstname",
    "full name",
    "fullname",
    "hash",
    "header",
    "jwt",
    "last name",
    "lastname",
    "license",
    "mid name",
    "middle name",
    "middlename",
    "midname",
    "oauth",
    "pass",
    "passphrase",
    "passcode",
    "passwd",
    "password",
    "phone",
    "pin",
    "private key",
    "public key",
    "secret",
    "security",
    "session",
    "ssn",
    "sur name",
    "surname",
    "token",
  )

  @OptionTag
  var ignoreTypes = true

  // The tilde (~) is intentionally added at the end of each word to ensure that the entire word is matched, not just part of it.
  // For example, "auth" becomes "Auth~", so in "Authenticated~," it won't be found.
  private fun wordsCapitalize(words: String): String =
    wordExpression.findAll(words)
      .toList()
      .joinToString("") { it.value.lowercase().capitalize() + "~" }

  private fun normalizedSensitiveWords(): Map<String, List<String>> =
    CacheService.remember(
      this::class.qualifiedName!!,
      sensitiveWords.joinToString(",")
    ) {
      sensitiveWords
        .sortedByDescending { it.length }
        .associateWith { sensitiveWord ->
          // We've added some possibilities for simple plurals.
          listOf(
            wordsCapitalize(sensitiveWord),
            wordsCapitalize("${sensitiveWord}s"),
            wordsCapitalize("${sensitiveWord}es"),
          ).distinct()
        }
    }

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpParameter(parameter: Parameter) {
      // If the #[\SensitiveParameter] attribute is found, then everything is fine here.
      for (attribute in parameter.attributes) {
        if (attribute.fqn == "\\SensitiveParameter")
          return
      }

      // We should ignore phpdoc.
      if (parameter.findParentOfType<PhpDocComment>() != null)
        return

      // We should ignore if it comes from a abstract method.
      with(parameter.findParentOfType<Method>()) {
        if (this?.isAbstract == true)
          return
      }

      if (ignoreTypes) {
        val parameterTypes = parameter.getTypes()

        if (parameterTypes.isNotEmpty()) {
          val parameterTypesFiltered = parameterTypes.filterNot {
            ignorableTypes.contains(it)
          }

          // We should skip bool, int and float types.
          if (parameterTypesFiltered.isEmpty())
            return
        }
      }

      val parameterSensitiveWords = mutableListOf<Pair<String, Int>>()
      val parameterName = wordsCapitalize(parameter.name)
      var parameterNameMutable = parameterName

      // Here things get a bit more complicated.
      // But, essentially, we're looking for compatible sensitive words in each existing word in the parameter.
      // When we find them, we remove them from a copy to avoid duplicates or smaller words intersecting with larger words (e.g., 'key' in 'private key').
      // We also store the position where the word was found so that we can organize what we find in the order it was originally defined in the parameter.
      run sensitiveWordsSearch@{
        normalizedSensitiveWords().forEach { sensitiveMap ->
          sensitiveMap.value.forEach {
            if (parameterNameMutable.contains(it)) {
              parameterSensitiveWords.add(Pair(sensitiveMap.key, parameterName.indexOf(it)))
              parameterNameMutable = parameterNameMutable.replace(it, "")

              if (parameterNameMutable.isEmpty())
                return@sensitiveWordsSearch
            }
          }
        }
      }

      if (parameterSensitiveWords.isEmpty())
        return

      val parameterSensitiveWordsNormalized = parameterSensitiveWords
        .sortedBy { it.second }
        .map { it.first }

      val parameterPointer = parameter.createSmartPointer()

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        parameter.nameIdentifier ?: parameter,
        if (parameterSensitiveWords.size == 1) "the sensitive word \"${parameterSensitiveWordsNormalized.first()}\" was used"
        else "the sensitive words ${parameterSensitiveWordsNormalized.joinToStringEx(", ", limit = 3) { "\"$it\"" }} was used",
        QuickFixService.instance.simpleInline("Declare #[SensitiveParameter] attribute") {
          parameterPointer.element?.addAttribute("SensitiveParameter")
        }
      )
    }
  }

  override fun getOptionsPane(): OptPane {
    return OptPane.pane(
      OptStringList(
        "sensitiveWords",
        PlainMessage("Sensitive words:"),
        null,
        HtmlChunk.raw("Manage words that should be treated as sensitive.")
      ),

      OptCheckbox(
        "ignoreTypes",
        PlainMessage("Ignore bool, int and float types"),
        emptyList(),
        HtmlChunk.raw(
          "When you activate this option, sensitive words will be allowed for <code>bool</code>, <code>int</code>, <code>float</code>, and/or <code>null</code> parameters. " +
            "This significantly reduces the noise that this inspection causes on its own."
        )
      )
    )
  }

  override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP820
}
