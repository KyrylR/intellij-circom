package com.github.kyrylr.circom.parser.adaptors

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

import com.github.kyrylr.circom.parser.CircomParser
import com.github.kyrylr.circom.parser.lang.CircomLanguage
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor

import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ParseTree

class CircomGrammarParser : ANTLRParserAdaptor(CircomLanguage, CircomParser(null)) {
    override fun parse(parser: Parser, root: IElementType): ParseTree {
        val startRule = if (root is IFileElementType) {
            CircomParser.RULE_circuit
        } else {
            Token.INVALID_TYPE
        }

        return when (startRule) {
            CircomParser.RULE_circuit -> (parser as CircomParser).circuit()
            else -> throw UnsupportedOperationException("cannot start parsing using root element $root")
        }
    }
}