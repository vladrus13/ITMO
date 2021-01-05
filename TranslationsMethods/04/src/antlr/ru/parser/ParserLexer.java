// Generated from /home/vladkuznetsov/Vl/Projects/MT/04/src/main/java/generator/Parser.g4 by ANTLR 4.8
package ru.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ParserLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		Data=10, Varible=11, Regular=12, StrongRegular=13, TokenName=14, LexemName=15, 
		WS=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"Data", "Varible", "Regular", "StrongRegular", "TokenName", "LexemName", 
			"WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'!'", "';'", "':'", "'*'", "'['", "']'", "'|'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "Data", "Varible", 
			"Regular", "StrongRegular", "TokenName", "LexemName", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ParserLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22j\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3"+
		"\13\6\138\n\13\r\13\16\139\3\13\3\13\3\f\3\f\7\f@\n\f\f\f\16\fC\13\f\3"+
		"\f\3\f\3\r\3\r\6\rI\n\r\r\r\16\rJ\3\r\3\r\3\16\3\16\3\16\3\16\6\16S\n"+
		"\16\r\16\16\16T\3\16\3\16\3\17\3\17\7\17[\n\17\f\17\16\17^\13\17\3\20"+
		"\3\20\7\20b\n\20\f\20\16\20e\13\20\3\21\3\21\3\21\3\21\2\2\22\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"\3\2\t\3\2@@\3\2==\3\2))\3\2C\\\6\2\62;C\\aac|\3\2c|\5\2\13\f\17\17\""+
		"\"\2o\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r"+
		"\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2"+
		"\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3"+
		"#\3\2\2\2\5%\3\2\2\2\7\'\3\2\2\2\t)\3\2\2\2\13+\3\2\2\2\r-\3\2\2\2\17"+
		"/\3\2\2\2\21\61\3\2\2\2\23\63\3\2\2\2\25\65\3\2\2\2\27=\3\2\2\2\31F\3"+
		"\2\2\2\33N\3\2\2\2\35X\3\2\2\2\37_\3\2\2\2!f\3\2\2\2#$\7#\2\2$\4\3\2\2"+
		"\2%&\7=\2\2&\6\3\2\2\2\'(\7<\2\2(\b\3\2\2\2)*\7,\2\2*\n\3\2\2\2+,\7]\2"+
		"\2,\f\3\2\2\2-.\7_\2\2.\16\3\2\2\2/\60\7~\2\2\60\20\3\2\2\2\61\62\7}\2"+
		"\2\62\22\3\2\2\2\63\64\7\177\2\2\64\24\3\2\2\2\65\67\7>\2\2\668\n\2\2"+
		"\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:;\3\2\2\2;<\7@\2\2<\26"+
		"\3\2\2\2=A\7%\2\2>@\n\3\2\2?>\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BD"+
		"\3\2\2\2CA\3\2\2\2DE\7=\2\2E\30\3\2\2\2FH\7)\2\2GI\n\4\2\2HG\3\2\2\2I"+
		"J\3\2\2\2JH\3\2\2\2JK\3\2\2\2KL\3\2\2\2LM\7)\2\2M\32\3\2\2\2NO\7&\2\2"+
		"OP\7)\2\2PR\3\2\2\2QS\n\4\2\2RQ\3\2\2\2ST\3\2\2\2TR\3\2\2\2TU\3\2\2\2"+
		"UV\3\2\2\2VW\7)\2\2W\34\3\2\2\2X\\\t\5\2\2Y[\t\6\2\2ZY\3\2\2\2[^\3\2\2"+
		"\2\\Z\3\2\2\2\\]\3\2\2\2]\36\3\2\2\2^\\\3\2\2\2_c\t\7\2\2`b\t\6\2\2a`"+
		"\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2d \3\2\2\2ec\3\2\2\2fg\t\b\2\2g"+
		"h\3\2\2\2hi\b\21\2\2i\"\3\2\2\2\t\29AJT\\c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}