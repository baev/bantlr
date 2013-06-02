// Generated from ru/ifmo/sta/lab04/baev/antlr4/Grammar2Grammar.g4 by ANTLR 4.0
package ru.ifmo.sta.lab04.baev.antlr4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Grammar2GrammarLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, NUMBER=3;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'+'", "'-'", "NUMBER"
	};
	public static final String[] ruleNames = {
		"PLUS", "MINUS", "NUMBER"
	};


	public Grammar2GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar2Grammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\2\4\5\22\b\1\4\2\t\2\4\3\t\3\4\4\t\4\3\2\3\2\3\3\3\3\3\4\6\4\17\n\4\r"+
		"\4\16\4\20\2\5\3\3\1\5\4\1\7\5\1\3\2\3\3\62;\22\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\3\t\3\2\2\2\5\13\3\2\2\2\7\16\3\2\2\2\t\n\7-\2\2\n\4\3"+
		"\2\2\2\13\f\7/\2\2\f\6\3\2\2\2\r\17\t\2\2\2\16\r\3\2\2\2\17\20\3\2\2\2"+
		"\20\16\3\2\2\2\20\21\3\2\2\2\21\b\3\2\2\2\4\2\20";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}