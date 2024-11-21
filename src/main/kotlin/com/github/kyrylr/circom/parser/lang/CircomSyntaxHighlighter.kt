package com.github.kyrylr.circom.parser.lang

import com.github.kyrylr.circom.parser.CircomLexer
import com.github.kyrylr.circom.parser.adaptors.CircomLexerAdaptor
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
        val SIGNAL_TYPE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CIRCOM_SIGNAL_TYPE", DefaultLanguageHighlighterColors.KEYWORD
        )
        private val BLOCK_COMMENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CIRCOM_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT
        )
        private val CONSTRAINT_GEN: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CIRCOM_CONSTRAINT_GEN", DefaultLanguageHighlighterColors.CONSTANT
        )

        private val BAD_CHAR_KEYS = arrayOf(HighlighterColors.BAD_CHARACTER)
        private val COMMENT_KEYS = arrayOf(LINE_COMMENT, BLOCK_COMMENT)
        private val CONSTRAINT_GEN_KEYS = arrayOf(CONSTRAINT_GEN)
        private val SIGNAL_TYPE_KEYS = arrayOf(SIGNAL_TYPE)
        private val STRING_KEYS = arrayOf(DefaultLanguageHighlighterColors.STRING)
        private val NUMBER_KEYS = arrayOf(DefaultLanguageHighlighterColors.NUMBER)
        private val COMMA_KEYS = arrayOf(DefaultLanguageHighlighterColors.COMMA)
        private val SEMICOLON_KEYS = arrayOf(DefaultLanguageHighlighterColors.SEMICOLON)
        private val PARENTHESIS_KEYS = arrayOf(DefaultLanguageHighlighterColors.PARENTHESES)
        private val BRACES_KEYS = arrayOf(DefaultLanguageHighlighterColors.BRACES)
        private val BRACKETS_KEYS = arrayOf(DefaultLanguageHighlighterColors.BRACKETS)

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
            CircomTokenTypes.SIGNAL_TYPES.contains(tokenType) -> SIGNAL_TYPE_KEYS
            CircomTokenTypes.CONSTRAINTS_GENERATORS.contains(tokenType) -> CONSTRAINT_GEN_KEYS
            CircomTokenTypes.SQUARE_BRACKET.contains(tokenType) -> BRACKETS_KEYS
            CircomTokenTypes.CURLY_BRACKET.contains(tokenType) -> BRACES_KEYS
            CircomTokenTypes.PARENTHESIS.contains(tokenType) -> PARENTHESIS_KEYS
            CircomTokenTypes.COMMA.contains(tokenType) -> COMMA_KEYS
            CircomTokenTypes.SEMICOLON.contains(tokenType) -> SEMICOLON_KEYS
            tokenType == CircomTokenTypes.BAD_TOKEN_TYPE -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }
}
