package com.github.kyrylr.intellijcircom.plugin

import com.github.kyrylr.intellijcircom.parser.CircomLexer
import com.github.kyrylr.intellijcircom.parser.CircomParser
import com.github.kyrylr.intellijcircom.parser.lang.CircomLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory

object CircomTokenTypes {
    val BAD_TOKEN_TYPE: IElementType = IElementType("BAD_TOKEN", CircomLanguage)

    init {
        initializeElementTypeFactory()
    }

    @JvmStatic
    fun initializeElementTypeFactory() {
        PSIElementTypeFactory.defineLanguageIElementTypes(
            CircomLanguage,
            CircomLexer.tokenNames,
            CircomParser.ruleNames
        )
    }

    val COMMENTS: TokenSet = PSIElementTypeFactory.createTokenSet(
        CircomLanguage,
        CircomLexer.COMMENT,
        CircomLexer.LINE_COMMENT
    )

    val WHITESPACES: TokenSet = PSIElementTypeFactory.createTokenSet(
        CircomLanguage,
        CircomLexer.WS
    )

    val KEYWORDS: TokenSet = PSIElementTypeFactory.createTokenSet(
        CircomLanguage,
        CircomLexer.SIGNAL,
        CircomLexer.INPUT,
        CircomLexer.OUTPUT,
        CircomLexer.PUBLIC,
        CircomLexer.TEMPLATE,
        CircomLexer.COMPONENT,
        CircomLexer.VAR,
        CircomLexer.FUNCTION,
        CircomLexer.RETURN,
        CircomLexer.IF,
        CircomLexer.ELSE,
        CircomLexer.FOR,
        CircomLexer.WHILE,
        CircomLexer.DO,
        CircomLexer.LOG,
        CircomLexer.ASSERT,
        CircomLexer.INCLUDE,
        CircomLexer.PRAGMA,
        CircomLexer.CIRCOM,
        CircomLexer.CUSTOM,
        CircomLexer.CUSTOM_TEMPLATES
    )

    val INTEGERS: TokenSet = PSIElementTypeFactory.createTokenSet(
        CircomLanguage,
        CircomLexer.INT
    )

    val STRINGS: TokenSet = PSIElementTypeFactory.createTokenSet(
        CircomLanguage,
        CircomLexer.PACKAGE_NAME
    )
}
