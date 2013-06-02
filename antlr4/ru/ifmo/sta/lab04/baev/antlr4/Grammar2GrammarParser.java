// Generated from ru/ifmo/sta/lab04/baev/antlr4/Grammar2Grammar.g4 by ANTLR 4.0
package ru.ifmo.sta.lab04.baev.antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Grammar2GrammarParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, NUMBER=3;
	public static final String[] tokenNames = {
		"<INVALID>", "'+'", "'-'", "NUMBER"
	};
	public static final int
		RULE_s = 0, RULE_a = 1;
	public static final String[] ruleNames = {
		"s", "a"
	};

	@Override
	public String getGrammarFileName() { return "Grammar2Grammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public Grammar2GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SContext extends ParserRuleContext {
		public AContext a() {
			return getRuleContext(AContext.class,0);
		}
		public SContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_s; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Grammar2GrammarListener ) ((Grammar2GrammarListener)listener).enterS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Grammar2GrammarListener ) ((Grammar2GrammarListener)listener).exitS(this);
		}
	}

	public final SContext s() throws RecognitionException {
		SContext _localctx = new SContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_s);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(4); a();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(Grammar2GrammarParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(Grammar2GrammarParser.MINUS, 0); }
		public List<AContext> a() {
			return getRuleContexts(AContext.class);
		}
		public AContext a(int i) {
			return getRuleContext(AContext.class,i);
		}
		public TerminalNode NUMBER() { return getToken(Grammar2GrammarParser.NUMBER, 0); }
		public AContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_a; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Grammar2GrammarListener ) ((Grammar2GrammarListener)listener).enterA(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Grammar2GrammarListener ) ((Grammar2GrammarListener)listener).exitA(this);
		}
	}

	public final AContext a() throws RecognitionException {
		AContext _localctx = new AContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_a);
		try {
			setState(15);
			switch (_input.LA(1)) {
			case PLUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(6); match(PLUS);
				setState(7); a();
				setState(8); a();
				}
				break;
			case MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(10); match(MINUS);
				setState(11); a();
				setState(12); a();
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 3);
				{
				setState(14); match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\5\24\4\2\t\2\4\3\t\3\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\5\3\22\n\3\3\3\2\4\2\4\2\2\23\2\6\3\2\2\2\4\21\3\2\2\2\6\7\5\4\3\2\7"+
		"\3\3\2\2\2\b\t\7\3\2\2\t\n\5\4\3\2\n\13\5\4\3\2\13\22\3\2\2\2\f\r\7\4"+
		"\2\2\r\16\5\4\3\2\16\17\5\4\3\2\17\22\3\2\2\2\20\22\7\5\2\2\21\b\3\2\2"+
		"\2\21\f\3\2\2\2\21\20\3\2\2\2\22\5\3\2\2\2\3\21";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}