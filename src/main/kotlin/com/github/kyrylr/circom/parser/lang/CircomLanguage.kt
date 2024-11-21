package com.github.kyrylr.circom.parser.lang

import com.github.kyrylr.circom.parser.ide.CircomIcons
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.vfs.VirtualFile
import java.nio.charset.StandardCharsets.UTF_8

object CircomLanguage : Language("Circom", "text/circom") {
    private fun readResolve(): Any = CircomLanguage

    override fun isCaseSensitive() = true
}

object CircomFileType : LanguageFileType(CircomLanguage) {
    object DEFAULTS {
        const val DESCRIPTION = "Circom file"
    }

    override fun getName() = DEFAULTS.DESCRIPTION
    override fun getDescription() = DEFAULTS.DESCRIPTION
    override fun getDefaultExtension() = "circom"
    override fun getIcon() = CircomIcons.FILE_ICON
    override fun getCharset(file: VirtualFile, content: ByteArray): String = UTF_8.name()
}