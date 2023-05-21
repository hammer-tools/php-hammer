package net.rentalhost.plugins.php.hammer.services

import com.intellij.codeInsight.daemon.impl.DaemonCodeAnalyzerImpl
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.openapi.project.guessProjectForFile

class FileDocumentManagerListenerService : FileDocumentManagerListener {
  override fun beforeDocumentSaving(document: Document) {
    if (!ProjectService.instance.settings.getServiceInstance().state.optionMissingSemicolon)
      return

    val virtualFile = FileDocumentManager.getInstance().getFile(document) ?: return

    if (virtualFile.extension != "php")
      return

    val project = guessProjectForFile(virtualFile) ?: return
    val highlights = DaemonCodeAnalyzerImpl.getHighlights(document, HighlightSeverity.ERROR, project)

    WriteCommandAction.runWriteCommandAction(project) {
      for (highlight in highlights.reversed()) {
        if (highlight.description == "Expected: semicolon") {
          document.insertString(highlight.startOffset, ";")
        }
      }
    }
  }
}
