package com.github.kyrylr.circom.parser.adaptors

import org.antlr.intellij.adaptor.lexer.ANTLRLexerState
import org.antlr.v4.runtime.misc.IntegerStack
import org.antlr.v4.runtime.misc.MurmurHash

class CircomLexerState(mode: Int, modeStack: IntegerStack?) : ANTLRLexerState(mode, modeStack) {
    override fun hashCodeImpl(): Int {
        var hash = MurmurHash.initialize()
        hash = MurmurHash.update(hash, mode)
        hash = MurmurHash.update(hash, modeStack)
        return MurmurHash.finish(hash, 3)
    }
}