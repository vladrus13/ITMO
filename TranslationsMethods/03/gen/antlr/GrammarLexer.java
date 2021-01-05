// Generated from /home/vladkuznetsov/Vl/Projects/MT/03/src/main/java/antlr/GrammarLexer.g4 by ANTLR 4.8
package antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Int=1, LongLong=2, Auto=3, Bool=4, Double=5, LongDouble=6, Void=7, String=8, 
		Vector=9, PlusPlus=10, MinusMinus=11, Not=12, PlusAssign=13, MinusAssign=14, 
		MultiplyAssign=15, DivAssign=16, ModAssign=17, XorAssign=18, AndAssign=19, 
		OrAssign=20, LeftShiftAssign=21, RightShiftAssign=22, Plus=23, Minus=24, 
		Multiply=25, Div=26, Mod=27, Xor=28, And=29, Or=30, Less=31, Greater=32, 
		Equal=33, NotEqual=34, LessEqual=35, GreaterEqual=36, AndAnd=37, OrOr=38, 
		LeftShift=39, RightShift=40, Assign=41, Tilde=42, Comma=43, Semicolon=44, 
		OpenBracket=45, CloseBracket=46, OpenFigureBracket=47, CloseFigureBracket=48, 
		Point=49, Pointer=50, OpenSqBracket=51, CloseSqBracket=52, Child=53, Colon=54, 
		Break=55, Return=56, Continue=57, Delete=58, Include=59, For=60, While=61, 
		Class=62, Struct=63, If=64, Switch=65, Case=66, Number=67, FloatNumber=68, 
		Digits=69, Digit=70, Boolean=71, True=72, False=73, Word=74, AnyInQuoutes=75, 
		WS=76;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Int", "LongLong", "Auto", "Bool", "Double", "LongDouble", "Void", "String", 
			"Vector", "PlusPlus", "MinusMinus", "Not", "PlusAssign", "MinusAssign", 
			"MultiplyAssign", "DivAssign", "ModAssign", "XorAssign", "AndAssign", 
			"OrAssign", "LeftShiftAssign", "RightShiftAssign", "Plus", "Minus", "Multiply", 
			"Div", "Mod", "Xor", "And", "Or", "Less", "Greater", "Equal", "NotEqual", 
			"LessEqual", "GreaterEqual", "AndAnd", "OrOr", "LeftShift", "RightShift", 
			"Assign", "Tilde", "Comma", "Semicolon", "OpenBracket", "CloseBracket", 
			"OpenFigureBracket", "CloseFigureBracket", "Point", "Pointer", "OpenSqBracket", 
			"CloseSqBracket", "Child", "Colon", "Break", "Return", "Continue", "Delete", 
			"Include", "For", "While", "Class", "Struct", "If", "Switch", "Case", 
			"Number", "FloatNumber", "Digits", "Digit", "Boolean", "True", "False", 
			"Word", "AnyInQuoutes", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'long long'", "'auto'", "'bool'", "'double'", "'long double'", 
			"'void'", "'string'", "'vector'", "'++'", "'--'", null, "'+='", "'-='", 
			"'*='", "'/='", "'%='", "'^='", "'&='", "'|='", "'<<='", "'>>='", "'+'", 
			"'-'", "'*'", "'/'", "'%'", "'^'", "'&'", "'|'", "'<'", "'>'", "'=='", 
			"'!='", "'<='", "'>='", null, null, "'<<'", "'>>'", "'='", "'~'", "','", 
			"';'", "'('", "')'", "'{'", "'}'", "'.'", "'->'", "'['", "']'", "'::'", 
			"':'", "'break'", "'return'", "'continue'", "'delete'", "'#include'", 
			"'for'", "'while'", "'class'", "'struct'", "'if'", "'switch'", "'case'", 
			null, null, null, null, null, "'true'", "'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Int", "LongLong", "Auto", "Bool", "Double", "LongDouble", "Void", 
			"String", "Vector", "PlusPlus", "MinusMinus", "Not", "PlusAssign", "MinusAssign", 
			"MultiplyAssign", "DivAssign", "ModAssign", "XorAssign", "AndAssign", 
			"OrAssign", "LeftShiftAssign", "RightShiftAssign", "Plus", "Minus", "Multiply", 
			"Div", "Mod", "Xor", "And", "Or", "Less", "Greater", "Equal", "NotEqual", 
			"LessEqual", "GreaterEqual", "AndAnd", "OrOr", "LeftShift", "RightShift", 
			"Assign", "Tilde", "Comma", "Semicolon", "OpenBracket", "CloseBracket", 
			"OpenFigureBracket", "CloseFigureBracket", "Point", "Pointer", "OpenSqBracket", 
			"CloseSqBracket", "Child", "Colon", "Break", "Return", "Continue", "Delete", 
			"Include", "For", "While", "Class", "Struct", "If", "Switch", "Case", 
			"Number", "FloatNumber", "Digits", "Digit", "Boolean", "True", "False", 
			"Word", "AnyInQuoutes", "WS"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GrammarLexer.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2N\u01d5\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\5\r\u00e4\n\r\3\16\3\16\3\16\3"+
		"\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3"+
		"\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3%\3&\3"+
		"&\3&\3&\3&\5&\u012b\n&\3\'\3\'\3\'\3\'\5\'\u0131\n\'\3(\3(\3(\3)\3)\3"+
		")\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3"+
		"\63\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\66\3\67\3\67\38\38\38\3"+
		"8\38\38\39\39\39\39\39\39\39\3:\3:\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\3;\3"+
		";\3;\3;\3<\3<\3<\3<\3<\3<\3<\3<\3<\3=\3=\3=\3=\3>\3>\3>\3>\3>\3>\3?\3"+
		"?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3@\3A\3A\3A\3B\3B\3B\3B\3B\3B\3B\3C\3"+
		"C\3C\3C\3C\3D\3D\3D\5D\u01a6\nD\3E\3E\3E\3E\3F\6F\u01ad\nF\rF\16F\u01ae"+
		"\3G\3G\3H\3H\5H\u01b5\nH\3I\3I\3I\3I\3I\3J\3J\3J\3J\3J\3J\3K\6K\u01c3"+
		"\nK\rK\16K\u01c4\3L\3L\6L\u01c9\nL\rL\16L\u01ca\3L\3L\3M\6M\u01d0\nM\r"+
		"M\16M\u01d1\3M\3M\2\2N\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089"+
		"F\u008bG\u008dH\u008fI\u0091J\u0093K\u0095L\u0097M\u0099N\3\2\6\3\2\62"+
		";\5\2C\\aac|\7\2\"#..AAC\\c|\5\2\13\f\17\17\"\"\2\u01de\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3"+
		"\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2"+
		"\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2"+
		"q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3"+
		"\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2"+
		"\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f"+
		"\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2"+
		"\2\2\u0099\3\2\2\2\3\u009b\3\2\2\2\5\u009f\3\2\2\2\7\u00a9\3\2\2\2\t\u00ae"+
		"\3\2\2\2\13\u00b3\3\2\2\2\r\u00ba\3\2\2\2\17\u00c6\3\2\2\2\21\u00cb\3"+
		"\2\2\2\23\u00d2\3\2\2\2\25\u00d9\3\2\2\2\27\u00dc\3\2\2\2\31\u00e3\3\2"+
		"\2\2\33\u00e5\3\2\2\2\35\u00e8\3\2\2\2\37\u00eb\3\2\2\2!\u00ee\3\2\2\2"+
		"#\u00f1\3\2\2\2%\u00f4\3\2\2\2\'\u00f7\3\2\2\2)\u00fa\3\2\2\2+\u00fd\3"+
		"\2\2\2-\u0101\3\2\2\2/\u0105\3\2\2\2\61\u0107\3\2\2\2\63\u0109\3\2\2\2"+
		"\65\u010b\3\2\2\2\67\u010d\3\2\2\29\u010f\3\2\2\2;\u0111\3\2\2\2=\u0113"+
		"\3\2\2\2?\u0115\3\2\2\2A\u0117\3\2\2\2C\u0119\3\2\2\2E\u011c\3\2\2\2G"+
		"\u011f\3\2\2\2I\u0122\3\2\2\2K\u012a\3\2\2\2M\u0130\3\2\2\2O\u0132\3\2"+
		"\2\2Q\u0135\3\2\2\2S\u0138\3\2\2\2U\u013a\3\2\2\2W\u013c\3\2\2\2Y\u013e"+
		"\3\2\2\2[\u0140\3\2\2\2]\u0142\3\2\2\2_\u0144\3\2\2\2a\u0146\3\2\2\2c"+
		"\u0148\3\2\2\2e\u014a\3\2\2\2g\u014d\3\2\2\2i\u014f\3\2\2\2k\u0151\3\2"+
		"\2\2m\u0154\3\2\2\2o\u0156\3\2\2\2q\u015c\3\2\2\2s\u0163\3\2\2\2u\u016c"+
		"\3\2\2\2w\u0173\3\2\2\2y\u017c\3\2\2\2{\u0180\3\2\2\2}\u0186\3\2\2\2\177"+
		"\u018c\3\2\2\2\u0081\u0193\3\2\2\2\u0083\u0196\3\2\2\2\u0085\u019d\3\2"+
		"\2\2\u0087\u01a5\3\2\2\2\u0089\u01a7\3\2\2\2\u008b\u01ac\3\2\2\2\u008d"+
		"\u01b0\3\2\2\2\u008f\u01b4\3\2\2\2\u0091\u01b6\3\2\2\2\u0093\u01bb\3\2"+
		"\2\2\u0095\u01c2\3\2\2\2\u0097\u01c6\3\2\2\2\u0099\u01cf\3\2\2\2\u009b"+
		"\u009c\7k\2\2\u009c\u009d\7p\2\2\u009d\u009e\7v\2\2\u009e\4\3\2\2\2\u009f"+
		"\u00a0\7n\2\2\u00a0\u00a1\7q\2\2\u00a1\u00a2\7p\2\2\u00a2\u00a3\7i\2\2"+
		"\u00a3\u00a4\7\"\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7q\2\2\u00a6\u00a7"+
		"\7p\2\2\u00a7\u00a8\7i\2\2\u00a8\6\3\2\2\2\u00a9\u00aa\7c\2\2\u00aa\u00ab"+
		"\7w\2\2\u00ab\u00ac\7v\2\2\u00ac\u00ad\7q\2\2\u00ad\b\3\2\2\2\u00ae\u00af"+
		"\7d\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1\7q\2\2\u00b1\u00b2\7n\2\2\u00b2"+
		"\n\3\2\2\2\u00b3\u00b4\7f\2\2\u00b4\u00b5\7q\2\2\u00b5\u00b6\7w\2\2\u00b6"+
		"\u00b7\7d\2\2\u00b7\u00b8\7n\2\2\u00b8\u00b9\7g\2\2\u00b9\f\3\2\2\2\u00ba"+
		"\u00bb\7n\2\2\u00bb\u00bc\7q\2\2\u00bc\u00bd\7p\2\2\u00bd\u00be\7i\2\2"+
		"\u00be\u00bf\7\"\2\2\u00bf\u00c0\7f\2\2\u00c0\u00c1\7q\2\2\u00c1\u00c2"+
		"\7w\2\2\u00c2\u00c3\7d\2\2\u00c3\u00c4\7n\2\2\u00c4\u00c5\7g\2\2\u00c5"+
		"\16\3\2\2\2\u00c6\u00c7\7x\2\2\u00c7\u00c8\7q\2\2\u00c8\u00c9\7k\2\2\u00c9"+
		"\u00ca\7f\2\2\u00ca\20\3\2\2\2\u00cb\u00cc\7u\2\2\u00cc\u00cd\7v\2\2\u00cd"+
		"\u00ce\7t\2\2\u00ce\u00cf\7k\2\2\u00cf\u00d0\7p\2\2\u00d0\u00d1\7i\2\2"+
		"\u00d1\22\3\2\2\2\u00d2\u00d3\7x\2\2\u00d3\u00d4\7g\2\2\u00d4\u00d5\7"+
		"e\2\2\u00d5\u00d6\7v\2\2\u00d6\u00d7\7q\2\2\u00d7\u00d8\7t\2\2\u00d8\24"+
		"\3\2\2\2\u00d9\u00da\7-\2\2\u00da\u00db\7-\2\2\u00db\26\3\2\2\2\u00dc"+
		"\u00dd\7/\2\2\u00dd\u00de\7/\2\2\u00de\30\3\2\2\2\u00df\u00e4\7#\2\2\u00e0"+
		"\u00e1\7p\2\2\u00e1\u00e2\7q\2\2\u00e2\u00e4\7v\2\2\u00e3\u00df\3\2\2"+
		"\2\u00e3\u00e0\3\2\2\2\u00e4\32\3\2\2\2\u00e5\u00e6\7-\2\2\u00e6\u00e7"+
		"\7?\2\2\u00e7\34\3\2\2\2\u00e8\u00e9\7/\2\2\u00e9\u00ea\7?\2\2\u00ea\36"+
		"\3\2\2\2\u00eb\u00ec\7,\2\2\u00ec\u00ed\7?\2\2\u00ed \3\2\2\2\u00ee\u00ef"+
		"\7\61\2\2\u00ef\u00f0\7?\2\2\u00f0\"\3\2\2\2\u00f1\u00f2\7\'\2\2\u00f2"+
		"\u00f3\7?\2\2\u00f3$\3\2\2\2\u00f4\u00f5\7`\2\2\u00f5\u00f6\7?\2\2\u00f6"+
		"&\3\2\2\2\u00f7\u00f8\7(\2\2\u00f8\u00f9\7?\2\2\u00f9(\3\2\2\2\u00fa\u00fb"+
		"\7~\2\2\u00fb\u00fc\7?\2\2\u00fc*\3\2\2\2\u00fd\u00fe\7>\2\2\u00fe\u00ff"+
		"\7>\2\2\u00ff\u0100\7?\2\2\u0100,\3\2\2\2\u0101\u0102\7@\2\2\u0102\u0103"+
		"\7@\2\2\u0103\u0104\7?\2\2\u0104.\3\2\2\2\u0105\u0106\7-\2\2\u0106\60"+
		"\3\2\2\2\u0107\u0108\7/\2\2\u0108\62\3\2\2\2\u0109\u010a\7,\2\2\u010a"+
		"\64\3\2\2\2\u010b\u010c\7\61\2\2\u010c\66\3\2\2\2\u010d\u010e\7\'\2\2"+
		"\u010e8\3\2\2\2\u010f\u0110\7`\2\2\u0110:\3\2\2\2\u0111\u0112\7(\2\2\u0112"+
		"<\3\2\2\2\u0113\u0114\7~\2\2\u0114>\3\2\2\2\u0115\u0116\7>\2\2\u0116@"+
		"\3\2\2\2\u0117\u0118\7@\2\2\u0118B\3\2\2\2\u0119\u011a\7?\2\2\u011a\u011b"+
		"\7?\2\2\u011bD\3\2\2\2\u011c\u011d\7#\2\2\u011d\u011e\7?\2\2\u011eF\3"+
		"\2\2\2\u011f\u0120\7>\2\2\u0120\u0121\7?\2\2\u0121H\3\2\2\2\u0122\u0123"+
		"\7@\2\2\u0123\u0124\7?\2\2\u0124J\3\2\2\2\u0125\u0126\7(\2\2\u0126\u012b"+
		"\7(\2\2\u0127\u0128\7c\2\2\u0128\u0129\7p\2\2\u0129\u012b\7f\2\2\u012a"+
		"\u0125\3\2\2\2\u012a\u0127\3\2\2\2\u012bL\3\2\2\2\u012c\u012d\7~\2\2\u012d"+
		"\u0131\7~\2\2\u012e\u012f\7q\2\2\u012f\u0131\7t\2\2\u0130\u012c\3\2\2"+
		"\2\u0130\u012e\3\2\2\2\u0131N\3\2\2\2\u0132\u0133\7>\2\2\u0133\u0134\7"+
		">\2\2\u0134P\3\2\2\2\u0135\u0136\7@\2\2\u0136\u0137\7@\2\2\u0137R\3\2"+
		"\2\2\u0138\u0139\7?\2\2\u0139T\3\2\2\2\u013a\u013b\7\u0080\2\2\u013bV"+
		"\3\2\2\2\u013c\u013d\7.\2\2\u013dX\3\2\2\2\u013e\u013f\7=\2\2\u013fZ\3"+
		"\2\2\2\u0140\u0141\7*\2\2\u0141\\\3\2\2\2\u0142\u0143\7+\2\2\u0143^\3"+
		"\2\2\2\u0144\u0145\7}\2\2\u0145`\3\2\2\2\u0146\u0147\7\177\2\2\u0147b"+
		"\3\2\2\2\u0148\u0149\7\60\2\2\u0149d\3\2\2\2\u014a\u014b\7/\2\2\u014b"+
		"\u014c\7@\2\2\u014cf\3\2\2\2\u014d\u014e\7]\2\2\u014eh\3\2\2\2\u014f\u0150"+
		"\7_\2\2\u0150j\3\2\2\2\u0151\u0152\7<\2\2\u0152\u0153\7<\2\2\u0153l\3"+
		"\2\2\2\u0154\u0155\7<\2\2\u0155n\3\2\2\2\u0156\u0157\7d\2\2\u0157\u0158"+
		"\7t\2\2\u0158\u0159\7g\2\2\u0159\u015a\7c\2\2\u015a\u015b\7m\2\2\u015b"+
		"p\3\2\2\2\u015c\u015d\7t\2\2\u015d\u015e\7g\2\2\u015e\u015f\7v\2\2\u015f"+
		"\u0160\7w\2\2\u0160\u0161\7t\2\2\u0161\u0162\7p\2\2\u0162r\3\2\2\2\u0163"+
		"\u0164\7e\2\2\u0164\u0165\7q\2\2\u0165\u0166\7p\2\2\u0166\u0167\7v\2\2"+
		"\u0167\u0168\7k\2\2\u0168\u0169\7p\2\2\u0169\u016a\7w\2\2\u016a\u016b"+
		"\7g\2\2\u016bt\3\2\2\2\u016c\u016d\7f\2\2\u016d\u016e\7g\2\2\u016e\u016f"+
		"\7n\2\2\u016f\u0170\7g\2\2\u0170\u0171\7v\2\2\u0171\u0172\7g\2\2\u0172"+
		"v\3\2\2\2\u0173\u0174\7%\2\2\u0174\u0175\7k\2\2\u0175\u0176\7p\2\2\u0176"+
		"\u0177\7e\2\2\u0177\u0178\7n\2\2\u0178\u0179\7w\2\2\u0179\u017a\7f\2\2"+
		"\u017a\u017b\7g\2\2\u017bx\3\2\2\2\u017c\u017d\7h\2\2\u017d\u017e\7q\2"+
		"\2\u017e\u017f\7t\2\2\u017fz\3\2\2\2\u0180\u0181\7y\2\2\u0181\u0182\7"+
		"j\2\2\u0182\u0183\7k\2\2\u0183\u0184\7n\2\2\u0184\u0185\7g\2\2\u0185|"+
		"\3\2\2\2\u0186\u0187\7e\2\2\u0187\u0188\7n\2\2\u0188\u0189\7c\2\2\u0189"+
		"\u018a\7u\2\2\u018a\u018b\7u\2\2\u018b~\3\2\2\2\u018c\u018d\7u\2\2\u018d"+
		"\u018e\7v\2\2\u018e\u018f\7t\2\2\u018f\u0190\7w\2\2\u0190\u0191\7e\2\2"+
		"\u0191\u0192\7v\2\2\u0192\u0080\3\2\2\2\u0193\u0194\7k\2\2\u0194\u0195"+
		"\7h\2\2\u0195\u0082\3\2\2\2\u0196\u0197\7u\2\2\u0197\u0198\7y\2\2\u0198"+
		"\u0199\7k\2\2\u0199\u019a\7v\2\2\u019a\u019b\7e\2\2\u019b\u019c\7j\2\2"+
		"\u019c\u0084\3\2\2\2\u019d\u019e\7e\2\2\u019e\u019f\7c\2\2\u019f\u01a0"+
		"\7u\2\2\u01a0\u01a1\7g\2\2\u01a1\u0086\3\2\2\2\u01a2\u01a6\5\u0089E\2"+
		"\u01a3\u01a6\5\u008bF\2\u01a4\u01a6\5\u008fH\2\u01a5\u01a2\3\2\2\2\u01a5"+
		"\u01a3\3\2\2\2\u01a5\u01a4\3\2\2\2\u01a6\u0088\3\2\2\2\u01a7\u01a8\5\u008b"+
		"F\2\u01a8\u01a9\7\60\2\2\u01a9\u01aa\5\u008bF\2\u01aa\u008a\3\2\2\2\u01ab"+
		"\u01ad\5\u008dG\2\u01ac\u01ab\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01ac"+
		"\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u008c\3\2\2\2\u01b0\u01b1\t\2\2\2\u01b1"+
		"\u008e\3\2\2\2\u01b2\u01b5\5\u0091I\2\u01b3\u01b5\5\u0093J\2\u01b4\u01b2"+
		"\3\2\2\2\u01b4\u01b3\3\2\2\2\u01b5\u0090\3\2\2\2\u01b6\u01b7\7v\2\2\u01b7"+
		"\u01b8\7t\2\2\u01b8\u01b9\7w\2\2\u01b9\u01ba\7g\2\2\u01ba\u0092\3\2\2"+
		"\2\u01bb\u01bc\7h\2\2\u01bc\u01bd\7c\2\2\u01bd\u01be\7n\2\2\u01be\u01bf"+
		"\7u\2\2\u01bf\u01c0\7g\2\2\u01c0\u0094\3\2\2\2\u01c1\u01c3\t\3\2\2\u01c2"+
		"\u01c1\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c2\3\2\2\2\u01c4\u01c5\3\2"+
		"\2\2\u01c5\u0096\3\2\2\2\u01c6\u01c8\7$\2\2\u01c7\u01c9\t\4\2\2\u01c8"+
		"\u01c7\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca\u01c8\3\2\2\2\u01ca\u01cb\3\2"+
		"\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01cd\7$\2\2\u01cd\u0098\3\2\2\2\u01ce"+
		"\u01d0\t\5\2\2\u01cf\u01ce\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01cf\3\2"+
		"\2\2\u01d1\u01d2\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3\u01d4\bM\2\2\u01d4"+
		"\u009a\3\2\2\2\f\2\u00e3\u012a\u0130\u01a5\u01ae\u01b4\u01c4\u01ca\u01d1"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}