package com.github.kyrylr.intellijcircom.parser.lang

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.jetbrains.annotations.NotNull

class CircomFileRoot(@NotNull viewProvider: FileViewProvider) : PsiFileBase(viewProvider, CircomLanguage) {

    @NotNull
    override fun getFileType(): FileType {
        return CircomFileType
    }

    override fun toString(): String {
        return "ANTLR v4 grammar file"
    }
}