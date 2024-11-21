package com.github.kyrylr.circom.parser.adaptors

import com.github.kyrylr.circom.parser.CircomLexer
import com.github.kyrylr.circom.parser.CircomParser
import com.github.kyrylr.circom.parser.lang.CircomLanguage
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.v4.runtime.Lexer

class CircomLexerAdaptor(lexer: CircomLexer) : ANTLRLexerAdaptor(CircomLanguage, lexer) {

    companion object {
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

        private val INITIAL_STATE = CircomLexerState(Lexer.DEFAULT_MODE, null)
    }

    override fun getInitialState(): CircomLexerState {
        return INITIAL_STATE
    }

    override fun getLexerState(lexer: Lexer): CircomLexerState {
        return if (lexer._modeStack.isEmpty) {
            CircomLexerState(lexer._mode, null)
        } else {
            CircomLexerState(lexer._mode, lexer._modeStack)
        }
    }
}