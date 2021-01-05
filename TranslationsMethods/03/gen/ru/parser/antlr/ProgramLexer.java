// Generated from /home/vladkuznetsov/Vl/Projects/MT/03/src/main/java/antlr/Program.g4 by ANTLR 4.8
package ru.parser.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProgramLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Int=3, LongLong=4, Auto=5, Bool=6, Double=7, LongDouble=8, 
		Void=9, String=10, Vector=11, PlusPlus=12, MinusMinus=13, Not=14, PlusAssign=15, 
		MinusAssign=16, MultiplyAssign=17, DivAssign=18, ModAssign=19, XorAssign=20, 
		AndAssign=21, OrAssign=22, LeftShiftAssign=23, RightShiftAssign=24, Plus=25, 
		Minus=26, Multiply=27, Div=28, Mod=29, Xor=30, And=31, Or=32, Less=33, 
		Greater=34, Equal=35, NotEqual=36, LessEqual=37, GreaterEqual=38, AndAnd=39, 
		OrOr=40, LeftShift=41, RightShift=42, Assign=43, Tilde=44, Comma=45, Semicolon=46, 
		OpenBracket=47, CloseBracket=48, OpenFigureBracket=49, CloseFigureBracket=50, 
		Point=51, Pointer=52, OpenSqBracket=53, CloseSqBracket=54, Child=55, Colon=56, 
		Break=57, Return=58, Continue=59, Delete=60, Include=61, For=62, While=63, 
		Class=64, Struct=65, If=66, Switch=67, Case=68, Number=69, FloatNumber=70, 
		Digits=71, Digit=72, Boolean=73, True=74, False=75, Word=76, AnyInQuoutes=77, 
		WS=78;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "Int", "LongLong", "Auto", "Bool", "Double", "LongDouble", 
			"Void", "String", "Vector", "PlusPlus", "MinusMinus", "Not", "PlusAssign", 
			"MinusAssign", "MultiplyAssign", "DivAssign", "ModAssign", "XorAssign", 
			"AndAssign", "OrAssign", "LeftShiftAssign", "RightShiftAssign", "Plus", 
			"Minus", "Multiply", "Div", "Mod", "Xor", "And", "Or", "Less", "Greater", 
			"Equal", "NotEqual", "LessEqual", "GreaterEqual", "AndAnd", "OrOr", "LeftShift", 
			"RightShift", "Assign", "Tilde", "Comma", "Semicolon", "OpenBracket", 
			"CloseBracket", "OpenFigureBracket", "CloseFigureBracket", "Point", "Pointer", 
			"OpenSqBracket", "CloseSqBracket", "Child", "Colon", "Break", "Return", 
			"Continue", "Delete", "Include", "For", "While", "Class", "Struct", "If", 
			"Switch", "Case", "Number", "FloatNumber", "Digits", "Digit", "Boolean", 
			"True", "False", "Word", "AnyInQuoutes", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'\"'", "'using'", "'int'", "'long long'", "'auto'", "'bool'", 
			"'double'", "'long double'", "'void'", "'string'", "'vector'", "'++'", 
			"'--'", null, "'+='", "'-='", "'*='", "'/='", "'%='", "'^='", "'&='", 
			"'|='", "'<<='", "'>>='", "'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'&'", 
			"'|'", "'<'", "'>'", "'=='", "'!='", "'<='", "'>='", null, null, "'<<'", 
			"'>>'", "'='", "'~'", "','", "';'", "'('", "')'", "'{'", "'}'", "'.'", 
			"'->'", "'['", "']'", "'::'", "':'", "'break'", "'return'", "'continue'", 
			"'delete'", "'#include'", "'for'", "'while'", "'class'", "'struct'", 
			"'if'", "'switch'", "'case'", null, null, null, null, null, "'true'", 
			"'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "Int", "LongLong", "Auto", "Bool", "Double", "LongDouble", 
			"Void", "String", "Vector", "PlusPlus", "MinusMinus", "Not", "PlusAssign", 
			"MinusAssign", "MultiplyAssign", "DivAssign", "ModAssign", "XorAssign", 
			"AndAssign", "OrAssign", "LeftShiftAssign", "RightShiftAssign", "Plus", 
			"Minus", "Multiply", "Div", "Mod", "Xor", "And", "Or", "Less", "Greater", 
			"Equal", "NotEqual", "LessEqual", "GreaterEqual", "AndAnd", "OrOr", "LeftShift", 
			"RightShift", "Assign", "Tilde", "Comma", "Semicolon", "OpenBracket", 
			"CloseBracket", "OpenFigureBracket", "CloseFigureBracket", "Point", "Pointer", 
			"OpenSqBracket", "CloseSqBracket", "Child", "Colon", "Break", "Return", 
			"Continue", "Delete", "Include", "For", "While", "Class", "Struct", "If", 
			"Switch", "Case", "Number", "FloatNumber", "Digits", "Digit", "Boolean", 
			"True", "False", "Word", "AnyInQuoutes", "WS"
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


	public ProgramLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Program.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2P\u01e1\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\5\17\u00f0\n\17\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\""+
		"\3#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3(\3(\5(\u0137"+
		"\n(\3)\3)\3)\3)\5)\u013d\n)\3*\3*\3*\3+\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/"+
		"\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\65\3\66"+
		"\3\66\3\67\3\67\38\38\38\39\39\3:\3:\3:\3:\3:\3:\3;\3;\3;\3;\3;\3;\3;"+
		"\3<\3<\3<\3<\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3=\3>\3>\3>\3>\3>\3>\3>"+
		"\3>\3>\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3A\3A\3A\3A\3A\3A\3B\3B\3B\3B\3B"+
		"\3B\3B\3C\3C\3C\3D\3D\3D\3D\3D\3D\3D\3E\3E\3E\3E\3E\3F\3F\3F\5F\u01b2"+
		"\nF\3G\3G\3G\3G\3H\6H\u01b9\nH\rH\16H\u01ba\3I\3I\3J\3J\5J\u01c1\nJ\3"+
		"K\3K\3K\3K\3K\3L\3L\3L\3L\3L\3L\3M\6M\u01cf\nM\rM\16M\u01d0\3N\3N\6N\u01d5"+
		"\nN\rN\16N\u01d6\3N\3N\3O\6O\u01dc\nO\rO\16O\u01dd\3O\3O\2\2P\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w"+
		"=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F\u008bG\u008dH\u008fI\u0091"+
		"J\u0093K\u0095L\u0097M\u0099N\u009bO\u009dP\3\2\6\3\2\62;\5\2C\\aac|\7"+
		"\2\"#..AAC\\c|\5\2\13\f\17\17\"\"\2\u01ea\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2"+
		"\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2"+
		"\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M"+
		"\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2"+
		"\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2"+
		"\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s"+
		"\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177"+
		"\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2"+
		"\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091"+
		"\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2"+
		"\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\3\u009f\3\2\2\2\5\u00a1\3\2\2\2\7\u00a7"+
		"\3\2\2\2\t\u00ab\3\2\2\2\13\u00b5\3\2\2\2\r\u00ba\3\2\2\2\17\u00bf\3\2"+
		"\2\2\21\u00c6\3\2\2\2\23\u00d2\3\2\2\2\25\u00d7\3\2\2\2\27\u00de\3\2\2"+
		"\2\31\u00e5\3\2\2\2\33\u00e8\3\2\2\2\35\u00ef\3\2\2\2\37\u00f1\3\2\2\2"+
		"!\u00f4\3\2\2\2#\u00f7\3\2\2\2%\u00fa\3\2\2\2\'\u00fd\3\2\2\2)\u0100\3"+
		"\2\2\2+\u0103\3\2\2\2-\u0106\3\2\2\2/\u0109\3\2\2\2\61\u010d\3\2\2\2\63"+
		"\u0111\3\2\2\2\65\u0113\3\2\2\2\67\u0115\3\2\2\29\u0117\3\2\2\2;\u0119"+
		"\3\2\2\2=\u011b\3\2\2\2?\u011d\3\2\2\2A\u011f\3\2\2\2C\u0121\3\2\2\2E"+
		"\u0123\3\2\2\2G\u0125\3\2\2\2I\u0128\3\2\2\2K\u012b\3\2\2\2M\u012e\3\2"+
		"\2\2O\u0136\3\2\2\2Q\u013c\3\2\2\2S\u013e\3\2\2\2U\u0141\3\2\2\2W\u0144"+
		"\3\2\2\2Y\u0146\3\2\2\2[\u0148\3\2\2\2]\u014a\3\2\2\2_\u014c\3\2\2\2a"+
		"\u014e\3\2\2\2c\u0150\3\2\2\2e\u0152\3\2\2\2g\u0154\3\2\2\2i\u0156\3\2"+
		"\2\2k\u0159\3\2\2\2m\u015b\3\2\2\2o\u015d\3\2\2\2q\u0160\3\2\2\2s\u0162"+
		"\3\2\2\2u\u0168\3\2\2\2w\u016f\3\2\2\2y\u0178\3\2\2\2{\u017f\3\2\2\2}"+
		"\u0188\3\2\2\2\177\u018c\3\2\2\2\u0081\u0192\3\2\2\2\u0083\u0198\3\2\2"+
		"\2\u0085\u019f\3\2\2\2\u0087\u01a2\3\2\2\2\u0089\u01a9\3\2\2\2\u008b\u01b1"+
		"\3\2\2\2\u008d\u01b3\3\2\2\2\u008f\u01b8\3\2\2\2\u0091\u01bc\3\2\2\2\u0093"+
		"\u01c0\3\2\2\2\u0095\u01c2\3\2\2\2\u0097\u01c7\3\2\2\2\u0099\u01ce\3\2"+
		"\2\2\u009b\u01d2\3\2\2\2\u009d\u01db\3\2\2\2\u009f\u00a0\7$\2\2\u00a0"+
		"\4\3\2\2\2\u00a1\u00a2\7w\2\2\u00a2\u00a3\7u\2\2\u00a3\u00a4\7k\2\2\u00a4"+
		"\u00a5\7p\2\2\u00a5\u00a6\7i\2\2\u00a6\6\3\2\2\2\u00a7\u00a8\7k\2\2\u00a8"+
		"\u00a9\7p\2\2\u00a9\u00aa\7v\2\2\u00aa\b\3\2\2\2\u00ab\u00ac\7n\2\2\u00ac"+
		"\u00ad\7q\2\2\u00ad\u00ae\7p\2\2\u00ae\u00af\7i\2\2\u00af\u00b0\7\"\2"+
		"\2\u00b0\u00b1\7n\2\2\u00b1\u00b2\7q\2\2\u00b2\u00b3\7p\2\2\u00b3\u00b4"+
		"\7i\2\2\u00b4\n\3\2\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7w\2\2\u00b7\u00b8"+
		"\7v\2\2\u00b8\u00b9\7q\2\2\u00b9\f\3\2\2\2\u00ba\u00bb\7d\2\2\u00bb\u00bc"+
		"\7q\2\2\u00bc\u00bd\7q\2\2\u00bd\u00be\7n\2\2\u00be\16\3\2\2\2\u00bf\u00c0"+
		"\7f\2\2\u00c0\u00c1\7q\2\2\u00c1\u00c2\7w\2\2\u00c2\u00c3\7d\2\2\u00c3"+
		"\u00c4\7n\2\2\u00c4\u00c5\7g\2\2\u00c5\20\3\2\2\2\u00c6\u00c7\7n\2\2\u00c7"+
		"\u00c8\7q\2\2\u00c8\u00c9\7p\2\2\u00c9\u00ca\7i\2\2\u00ca\u00cb\7\"\2"+
		"\2\u00cb\u00cc\7f\2\2\u00cc\u00cd\7q\2\2\u00cd\u00ce\7w\2\2\u00ce\u00cf"+
		"\7d\2\2\u00cf\u00d0\7n\2\2\u00d0\u00d1\7g\2\2\u00d1\22\3\2\2\2\u00d2\u00d3"+
		"\7x\2\2\u00d3\u00d4\7q\2\2\u00d4\u00d5\7k\2\2\u00d5\u00d6\7f\2\2\u00d6"+
		"\24\3\2\2\2\u00d7\u00d8\7u\2\2\u00d8\u00d9\7v\2\2\u00d9\u00da\7t\2\2\u00da"+
		"\u00db\7k\2\2\u00db\u00dc\7p\2\2\u00dc\u00dd\7i\2\2\u00dd\26\3\2\2\2\u00de"+
		"\u00df\7x\2\2\u00df\u00e0\7g\2\2\u00e0\u00e1\7e\2\2\u00e1\u00e2\7v\2\2"+
		"\u00e2\u00e3\7q\2\2\u00e3\u00e4\7t\2\2\u00e4\30\3\2\2\2\u00e5\u00e6\7"+
		"-\2\2\u00e6\u00e7\7-\2\2\u00e7\32\3\2\2\2\u00e8\u00e9\7/\2\2\u00e9\u00ea"+
		"\7/\2\2\u00ea\34\3\2\2\2\u00eb\u00f0\7#\2\2\u00ec\u00ed\7p\2\2\u00ed\u00ee"+
		"\7q\2\2\u00ee\u00f0\7v\2\2\u00ef\u00eb\3\2\2\2\u00ef\u00ec\3\2\2\2\u00f0"+
		"\36\3\2\2\2\u00f1\u00f2\7-\2\2\u00f2\u00f3\7?\2\2\u00f3 \3\2\2\2\u00f4"+
		"\u00f5\7/\2\2\u00f5\u00f6\7?\2\2\u00f6\"\3\2\2\2\u00f7\u00f8\7,\2\2\u00f8"+
		"\u00f9\7?\2\2\u00f9$\3\2\2\2\u00fa\u00fb\7\61\2\2\u00fb\u00fc\7?\2\2\u00fc"+
		"&\3\2\2\2\u00fd\u00fe\7\'\2\2\u00fe\u00ff\7?\2\2\u00ff(\3\2\2\2\u0100"+
		"\u0101\7`\2\2\u0101\u0102\7?\2\2\u0102*\3\2\2\2\u0103\u0104\7(\2\2\u0104"+
		"\u0105\7?\2\2\u0105,\3\2\2\2\u0106\u0107\7~\2\2\u0107\u0108\7?\2\2\u0108"+
		".\3\2\2\2\u0109\u010a\7>\2\2\u010a\u010b\7>\2\2\u010b\u010c\7?\2\2\u010c"+
		"\60\3\2\2\2\u010d\u010e\7@\2\2\u010e\u010f\7@\2\2\u010f\u0110\7?\2\2\u0110"+
		"\62\3\2\2\2\u0111\u0112\7-\2\2\u0112\64\3\2\2\2\u0113\u0114\7/\2\2\u0114"+
		"\66\3\2\2\2\u0115\u0116\7,\2\2\u01168\3\2\2\2\u0117\u0118\7\61\2\2\u0118"+
		":\3\2\2\2\u0119\u011a\7\'\2\2\u011a<\3\2\2\2\u011b\u011c\7`\2\2\u011c"+
		">\3\2\2\2\u011d\u011e\7(\2\2\u011e@\3\2\2\2\u011f\u0120\7~\2\2\u0120B"+
		"\3\2\2\2\u0121\u0122\7>\2\2\u0122D\3\2\2\2\u0123\u0124\7@\2\2\u0124F\3"+
		"\2\2\2\u0125\u0126\7?\2\2\u0126\u0127\7?\2\2\u0127H\3\2\2\2\u0128\u0129"+
		"\7#\2\2\u0129\u012a\7?\2\2\u012aJ\3\2\2\2\u012b\u012c\7>\2\2\u012c\u012d"+
		"\7?\2\2\u012dL\3\2\2\2\u012e\u012f\7@\2\2\u012f\u0130\7?\2\2\u0130N\3"+
		"\2\2\2\u0131\u0132\7(\2\2\u0132\u0137\7(\2\2\u0133\u0134\7c\2\2\u0134"+
		"\u0135\7p\2\2\u0135\u0137\7f\2\2\u0136\u0131\3\2\2\2\u0136\u0133\3\2\2"+
		"\2\u0137P\3\2\2\2\u0138\u0139\7~\2\2\u0139\u013d\7~\2\2\u013a\u013b\7"+
		"q\2\2\u013b\u013d\7t\2\2\u013c\u0138\3\2\2\2\u013c\u013a\3\2\2\2\u013d"+
		"R\3\2\2\2\u013e\u013f\7>\2\2\u013f\u0140\7>\2\2\u0140T\3\2\2\2\u0141\u0142"+
		"\7@\2\2\u0142\u0143\7@\2\2\u0143V\3\2\2\2\u0144\u0145\7?\2\2\u0145X\3"+
		"\2\2\2\u0146\u0147\7\u0080\2\2\u0147Z\3\2\2\2\u0148\u0149\7.\2\2\u0149"+
		"\\\3\2\2\2\u014a\u014b\7=\2\2\u014b^\3\2\2\2\u014c\u014d\7*\2\2\u014d"+
		"`\3\2\2\2\u014e\u014f\7+\2\2\u014fb\3\2\2\2\u0150\u0151\7}\2\2\u0151d"+
		"\3\2\2\2\u0152\u0153\7\177\2\2\u0153f\3\2\2\2\u0154\u0155\7\60\2\2\u0155"+
		"h\3\2\2\2\u0156\u0157\7/\2\2\u0157\u0158\7@\2\2\u0158j\3\2\2\2\u0159\u015a"+
		"\7]\2\2\u015al\3\2\2\2\u015b\u015c\7_\2\2\u015cn\3\2\2\2\u015d\u015e\7"+
		"<\2\2\u015e\u015f\7<\2\2\u015fp\3\2\2\2\u0160\u0161\7<\2\2\u0161r\3\2"+
		"\2\2\u0162\u0163\7d\2\2\u0163\u0164\7t\2\2\u0164\u0165\7g\2\2\u0165\u0166"+
		"\7c\2\2\u0166\u0167\7m\2\2\u0167t\3\2\2\2\u0168\u0169\7t\2\2\u0169\u016a"+
		"\7g\2\2\u016a\u016b\7v\2\2\u016b\u016c\7w\2\2\u016c\u016d\7t\2\2\u016d"+
		"\u016e\7p\2\2\u016ev\3\2\2\2\u016f\u0170\7e\2\2\u0170\u0171\7q\2\2\u0171"+
		"\u0172\7p\2\2\u0172\u0173\7v\2\2\u0173\u0174\7k\2\2\u0174\u0175\7p\2\2"+
		"\u0175\u0176\7w\2\2\u0176\u0177\7g\2\2\u0177x\3\2\2\2\u0178\u0179\7f\2"+
		"\2\u0179\u017a\7g\2\2\u017a\u017b\7n\2\2\u017b\u017c\7g\2\2\u017c\u017d"+
		"\7v\2\2\u017d\u017e\7g\2\2\u017ez\3\2\2\2\u017f\u0180\7%\2\2\u0180\u0181"+
		"\7k\2\2\u0181\u0182\7p\2\2\u0182\u0183\7e\2\2\u0183\u0184\7n\2\2\u0184"+
		"\u0185\7w\2\2\u0185\u0186\7f\2\2\u0186\u0187\7g\2\2\u0187|\3\2\2\2\u0188"+
		"\u0189\7h\2\2\u0189\u018a\7q\2\2\u018a\u018b\7t\2\2\u018b~\3\2\2\2\u018c"+
		"\u018d\7y\2\2\u018d\u018e\7j\2\2\u018e\u018f\7k\2\2\u018f\u0190\7n\2\2"+
		"\u0190\u0191\7g\2\2\u0191\u0080\3\2\2\2\u0192\u0193\7e\2\2\u0193\u0194"+
		"\7n\2\2\u0194\u0195\7c\2\2\u0195\u0196\7u\2\2\u0196\u0197\7u\2\2\u0197"+
		"\u0082\3\2\2\2\u0198\u0199\7u\2\2\u0199\u019a\7v\2\2\u019a\u019b\7t\2"+
		"\2\u019b\u019c\7w\2\2\u019c\u019d\7e\2\2\u019d\u019e\7v\2\2\u019e\u0084"+
		"\3\2\2\2\u019f\u01a0\7k\2\2\u01a0\u01a1\7h\2\2\u01a1\u0086\3\2\2\2\u01a2"+
		"\u01a3\7u\2\2\u01a3\u01a4\7y\2\2\u01a4\u01a5\7k\2\2\u01a5\u01a6\7v\2\2"+
		"\u01a6\u01a7\7e\2\2\u01a7\u01a8\7j\2\2\u01a8\u0088\3\2\2\2\u01a9\u01aa"+
		"\7e\2\2\u01aa\u01ab\7c\2\2\u01ab\u01ac\7u\2\2\u01ac\u01ad\7g\2\2\u01ad"+
		"\u008a\3\2\2\2\u01ae\u01b2\5\u008dG\2\u01af\u01b2\5\u008fH\2\u01b0\u01b2"+
		"\5\u0093J\2\u01b1\u01ae\3\2\2\2\u01b1\u01af\3\2\2\2\u01b1\u01b0\3\2\2"+
		"\2\u01b2\u008c\3\2\2\2\u01b3\u01b4\5\u008fH\2\u01b4\u01b5\7\60\2\2\u01b5"+
		"\u01b6\5\u008fH\2\u01b6\u008e\3\2\2\2\u01b7\u01b9\5\u0091I\2\u01b8\u01b7"+
		"\3\2\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb"+
		"\u0090\3\2\2\2\u01bc\u01bd\t\2\2\2\u01bd\u0092\3\2\2\2\u01be\u01c1\5\u0095"+
		"K\2\u01bf\u01c1\5\u0097L\2\u01c0\u01be\3\2\2\2\u01c0\u01bf\3\2\2\2\u01c1"+
		"\u0094\3\2\2\2\u01c2\u01c3\7v\2\2\u01c3\u01c4\7t\2\2\u01c4\u01c5\7w\2"+
		"\2\u01c5\u01c6\7g\2\2\u01c6\u0096\3\2\2\2\u01c7\u01c8\7h\2\2\u01c8\u01c9"+
		"\7c\2\2\u01c9\u01ca\7n\2\2\u01ca\u01cb\7u\2\2\u01cb\u01cc\7g\2\2\u01cc"+
		"\u0098\3\2\2\2\u01cd\u01cf\t\3\2\2\u01ce\u01cd\3\2\2\2\u01cf\u01d0\3\2"+
		"\2\2\u01d0\u01ce\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u009a\3\2\2\2\u01d2"+
		"\u01d4\7$\2\2\u01d3\u01d5\t\4\2\2\u01d4\u01d3\3\2\2\2\u01d5\u01d6\3\2"+
		"\2\2\u01d6\u01d4\3\2\2\2\u01d6\u01d7\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8"+
		"\u01d9\7$\2\2\u01d9\u009c\3\2\2\2\u01da\u01dc\t\5\2\2\u01db\u01da\3\2"+
		"\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01de"+
		"\u01df\3\2\2\2\u01df\u01e0\bO\2\2\u01e0\u009e\3\2\2\2\f\2\u00ef\u0136"+
		"\u013c\u01b1\u01ba\u01c0\u01d0\u01d6\u01dd\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}