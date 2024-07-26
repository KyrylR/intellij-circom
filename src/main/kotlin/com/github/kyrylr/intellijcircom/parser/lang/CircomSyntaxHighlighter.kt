package com.github.kyrylr.intellijcircom.parser.lang

import com.github.kyrylr.intellijcircom.parser.CircomLexer
import com.github.kyrylr.intellijcircom.parser.adaptors.CircomLexerAdaptor
import com.github.kyrylr.intellijcircom.plugin.CircomTokenTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class CircomSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CIRCOM_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD
        )
        val LINE_COMMENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CIRCOM_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT
        )
        private val BLOCK_COMMENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CIRCOM_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT
        )

        private val BAD_CHAR_KEYS = arrayOf(HighlighterColors.BAD_CHARACTER)
        private val COMMENT_KEYS = arrayOf(LINE_COMMENT, BLOCK_COMMENT)
        private val STRING_KEYS = arrayOf(DefaultLanguageHighlighterColors.STRING)
        private val NUMBER_KEYS = arrayOf(DefaultLanguageHighlighterColors.NUMBER)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer {
        val lexer = CircomLexer(null)
        return CircomLexerAdaptor(lexer)
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when {
            CircomTokenTypes.KEYWORDS.contains(tokenType) -> arrayOf(KEYWORD)
            CircomTokenTypes.COMMENTS.contains(tokenType) -> COMMENT_KEYS
            CircomTokenTypes.INTEGERS.contains(tokenType) -> NUMBER_KEYS
            CircomTokenTypes.STRINGS.contains(tokenType) -> STRING_KEYS
            tokenType == CircomTokenTypes.BAD_TOKEN_TYPE -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }
}
