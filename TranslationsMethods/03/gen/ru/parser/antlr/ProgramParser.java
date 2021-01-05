// Generated from /home/vladkuznetsov/Vl/Projects/MT/03/src/main/java/antlr/Program.g4 by ANTLR 4.8
package ru.parser.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProgramParser extends Parser {
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
	public static final int
		RULE_program = 0, RULE_headers = 1, RULE_header = 2, RULE_block = 3, RULE_function = 4, 
		RULE_arguments = 5, RULE_argument = 6, RULE_references = 7, RULE_r_class = 8, 
		RULE_struct = 9, RULE_classOrStructBody = 10, RULE_field = 11, RULE_using = 12, 
		RULE_body = 13, RULE_r_for = 14, RULE_r_while = 15, RULE_r_if = 16, RULE_r_switch = 17, 
		RULE_r_case = 18, RULE_instruction = 19, RULE_maybeExpression = 20, RULE_expression = 21, 
		RULE_value = 22, RULE_binaryAssignOperator = 23, RULE_type = 24, RULE_binaryOperator = 25, 
		RULE_unaryOperation = 26, RULE_singleAssignOperator = 27;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "headers", "header", "block", "function", "arguments", "argument", 
			"references", "r_class", "struct", "classOrStructBody", "field", "using", 
			"body", "r_for", "r_while", "r_if", "r_switch", "r_case", "instruction", 
			"maybeExpression", "expression", "value", "binaryAssignOperator", "type", 
			"binaryOperator", "unaryOperation", "singleAssignOperator"
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

	@Override
	public String getGrammarFileName() { return "Program.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ProgramParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public HeadersContext headers() {
			return getRuleContext(HeadersContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			headers();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (T__1 - 2)) | (1L << (Int - 2)) | (1L << (LongLong - 2)) | (1L << (Auto - 2)) | (1L << (Bool - 2)) | (1L << (Double - 2)) | (1L << (LongDouble - 2)) | (1L << (Void - 2)) | (1L << (String - 2)) | (1L << (Vector - 2)) | (1L << (Class - 2)) | (1L << (Struct - 2)))) != 0)) {
				{
				{
				setState(57);
				block();
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class HeadersContext extends ParserRuleContext {
		public List<HeaderContext> header() {
			return getRuleContexts(HeaderContext.class);
		}
		public HeaderContext header(int i) {
			return getRuleContext(HeaderContext.class,i);
		}
		public HeadersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_headers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterHeaders(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitHeaders(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitHeaders(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeadersContext headers() throws RecognitionException {
		HeadersContext _localctx = new HeadersContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_headers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Include) {
				{
				{
				setState(63);
				header();
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class HeaderContext extends ParserRuleContext {
		public TerminalNode Include() { return getToken(ProgramParser.Include, 0); }
		public TerminalNode Less() { return getToken(ProgramParser.Less, 0); }
		public List<TerminalNode> Word() { return getTokens(ProgramParser.Word); }
		public TerminalNode Word(int i) {
			return getToken(ProgramParser.Word, i);
		}
		public TerminalNode Greater() { return getToken(ProgramParser.Greater, 0); }
		public TerminalNode Point() { return getToken(ProgramParser.Point, 0); }
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_header);
		int _la;
		try {
			setState(85);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(69);
				match(Include);
				setState(70);
				match(Less);
				setState(71);
				match(Word);
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Point) {
					{
					setState(72);
					match(Point);
					setState(73);
					match(Word);
					}
				}

				setState(76);
				match(Greater);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				match(Include);
				setState(78);
				match(T__0);
				setState(79);
				match(Word);
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Point) {
					{
					setState(80);
					match(Point);
					setState(81);
					match(Word);
					}
				}

				setState(84);
				match(T__0);
				}
				break;
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

	public static class BlockContext extends ParserRuleContext {
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public List<R_classContext> r_class() {
			return getRuleContexts(R_classContext.class);
		}
		public R_classContext r_class(int i) {
			return getRuleContext(R_classContext.class,i);
		}
		public List<StructContext> struct() {
			return getRuleContexts(StructContext.class);
		}
		public StructContext struct(int i) {
			return getRuleContext(StructContext.class,i);
		}
		public List<UsingContext> using() {
			return getRuleContexts(UsingContext.class);
		}
		public UsingContext using(int i) {
			return getRuleContext(UsingContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(91); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(91);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case Int:
					case LongLong:
					case Auto:
					case Bool:
					case Double:
					case LongDouble:
					case Void:
					case String:
					case Vector:
						{
						setState(87);
						function();
						}
						break;
					case Class:
						{
						setState(88);
						r_class();
						}
						break;
					case Struct:
						{
						setState(89);
						struct();
						}
						break;
					case T__1:
						{
						setState(90);
						using();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(93); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class FunctionContext extends ParserRuleContext {
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
	 
		public FunctionContext() { }
		public void copyFrom(FunctionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NotFullFunctionContext extends FunctionContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public TerminalNode Semicolon() { return getToken(ProgramParser.Semicolon, 0); }
		public NotFullFunctionContext(FunctionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterNotFullFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitNotFullFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitNotFullFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FullFunctionContext extends FunctionContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public TerminalNode OpenFigureBracket() { return getToken(ProgramParser.OpenFigureBracket, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode CloseFigureBracket() { return getToken(ProgramParser.CloseFigureBracket, 0); }
		public FullFunctionContext(FunctionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterFullFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitFullFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitFullFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_function);
		try {
			setState(111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new FullFunctionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(95);
				type();
				setState(96);
				match(Word);
				setState(97);
				match(OpenBracket);
				setState(98);
				arguments();
				setState(99);
				match(CloseBracket);
				setState(100);
				match(OpenFigureBracket);
				setState(101);
				body();
				setState(102);
				match(CloseFigureBracket);
				}
				break;
			case 2:
				_localctx = new NotFullFunctionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(104);
				type();
				setState(105);
				match(Word);
				setState(106);
				match(OpenBracket);
				setState(107);
				arguments();
				setState(108);
				match(CloseBracket);
				setState(109);
				match(Semicolon);
				}
				break;
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

	public static class ArgumentsContext extends ParserRuleContext {
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(ProgramParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(ProgramParser.Comma, i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_arguments);
		try {
			int _alt;
			setState(123);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Int:
			case LongLong:
			case Auto:
			case Bool:
			case Double:
			case LongDouble:
			case Void:
			case String:
			case Vector:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(113);
						argument();
						setState(114);
						match(Comma);
						}
						} 
					}
					setState(120);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				}
				setState(121);
				argument();
				}
				break;
			case CloseBracket:
				enterOuterAlt(_localctx, 2);
				{
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

	public static class ArgumentContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ReferencesContext references() {
			return getRuleContext(ReferencesContext.class,0);
		}
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			type();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(126);
				references();
				}
			}

			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Word) {
				{
				setState(129);
				match(Word);
				}
			}

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

	public static class ReferencesContext extends ParserRuleContext {
		public List<TerminalNode> Multiply() { return getTokens(ProgramParser.Multiply); }
		public TerminalNode Multiply(int i) {
			return getToken(ProgramParser.Multiply, i);
		}
		public ReferencesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_references; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterReferences(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitReferences(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitReferences(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferencesContext references() throws RecognitionException {
		ReferencesContext _localctx = new ReferencesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_references);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(132);
				match(Multiply);
				}
				}
				setState(135); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Multiply );
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

	public static class R_classContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(ProgramParser.Class, 0); }
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public TerminalNode OpenFigureBracket() { return getToken(ProgramParser.OpenFigureBracket, 0); }
		public ClassOrStructBodyContext classOrStructBody() {
			return getRuleContext(ClassOrStructBodyContext.class,0);
		}
		public TerminalNode CloseFigureBracket() { return getToken(ProgramParser.CloseFigureBracket, 0); }
		public R_classContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r_class; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterR_class(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitR_class(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitR_class(this);
			else return visitor.visitChildren(this);
		}
	}

	public final R_classContext r_class() throws RecognitionException {
		R_classContext _localctx = new R_classContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_r_class);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(Class);
			setState(138);
			match(Word);
			setState(139);
			match(OpenFigureBracket);
			setState(140);
			classOrStructBody();
			setState(141);
			match(CloseFigureBracket);
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

	public static class StructContext extends ParserRuleContext {
		public TerminalNode Struct() { return getToken(ProgramParser.Struct, 0); }
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public TerminalNode OpenFigureBracket() { return getToken(ProgramParser.OpenFigureBracket, 0); }
		public ClassOrStructBodyContext classOrStructBody() {
			return getRuleContext(ClassOrStructBodyContext.class,0);
		}
		public TerminalNode CloseFigureBracket() { return getToken(ProgramParser.CloseFigureBracket, 0); }
		public StructContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterStruct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitStruct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitStruct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructContext struct() throws RecognitionException {
		StructContext _localctx = new StructContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_struct);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(Struct);
			setState(144);
			match(Word);
			setState(145);
			match(OpenFigureBracket);
			setState(146);
			classOrStructBody();
			setState(147);
			match(CloseFigureBracket);
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

	public static class ClassOrStructBodyContext extends ParserRuleContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public ClassOrStructBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrStructBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterClassOrStructBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitClassOrStructBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitClassOrStructBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassOrStructBodyContext classOrStructBody() throws RecognitionException {
		ClassOrStructBodyContext _localctx = new ClassOrStructBodyContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_classOrStructBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Int) | (1L << LongLong) | (1L << Auto) | (1L << Bool) | (1L << Double) | (1L << LongDouble) | (1L << Void) | (1L << String) | (1L << Vector))) != 0)) {
				{
				setState(151);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(149);
					field();
					}
					break;
				case 2:
					{
					setState(150);
					function();
					}
					break;
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class FieldContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public TerminalNode Semicolon() { return getToken(ProgramParser.Semicolon, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_field);
		try {
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(156);
				type();
				setState(157);
				match(Word);
				setState(158);
				match(Semicolon);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				type();
				setState(161);
				expression(0);
				setState(162);
				match(Semicolon);
				}
				break;
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

	public static class UsingContext extends ParserRuleContext {
		public List<TerminalNode> Word() { return getTokens(ProgramParser.Word); }
		public TerminalNode Word(int i) {
			return getToken(ProgramParser.Word, i);
		}
		public TerminalNode Semicolon() { return getToken(ProgramParser.Semicolon, 0); }
		public UsingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_using; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterUsing(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitUsing(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitUsing(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UsingContext using() throws RecognitionException {
		UsingContext _localctx = new UsingContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_using);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(T__1);
			setState(167);
			match(Word);
			setState(168);
			match(Word);
			setState(169);
			match(Semicolon);
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

	public static class BodyContext extends ParserRuleContext {
		public List<MaybeExpressionContext> maybeExpression() {
			return getRuleContexts(MaybeExpressionContext.class);
		}
		public MaybeExpressionContext maybeExpression(int i) {
			return getRuleContext(MaybeExpressionContext.class,i);
		}
		public List<R_forContext> r_for() {
			return getRuleContexts(R_forContext.class);
		}
		public R_forContext r_for(int i) {
			return getRuleContext(R_forContext.class,i);
		}
		public List<R_whileContext> r_while() {
			return getRuleContexts(R_whileContext.class);
		}
		public R_whileContext r_while(int i) {
			return getRuleContext(R_whileContext.class,i);
		}
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public List<R_ifContext> r_if() {
			return getRuleContexts(R_ifContext.class);
		}
		public R_ifContext r_if(int i) {
			return getRuleContext(R_ifContext.class,i);
		}
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public List<R_switchContext> r_switch() {
			return getRuleContexts(R_switchContext.class);
		}
		public R_switchContext r_switch(int i) {
			return getRuleContext(R_switchContext.class,i);
		}
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_body);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(178);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						setState(171);
						maybeExpression();
						}
						break;
					case 2:
						{
						setState(172);
						r_for();
						}
						break;
					case 3:
						{
						setState(173);
						r_while();
						}
						break;
					case 4:
						{
						setState(174);
						instruction();
						}
						break;
					case 5:
						{
						setState(175);
						r_if();
						}
						break;
					case 6:
						{
						setState(176);
						field();
						}
						break;
					case 7:
						{
						setState(177);
						r_switch();
						}
						break;
					}
					} 
				}
				setState(182);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
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

	public static class R_forContext extends ParserRuleContext {
		public TerminalNode For() { return getToken(ProgramParser.For, 0); }
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public MaybeExpressionContext maybeExpression() {
			return getRuleContext(MaybeExpressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public TerminalNode OpenFigureBracket() { return getToken(ProgramParser.OpenFigureBracket, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode CloseFigureBracket() { return getToken(ProgramParser.CloseFigureBracket, 0); }
		public R_forContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r_for; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterR_for(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitR_for(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitR_for(this);
			else return visitor.visitChildren(this);
		}
	}

	public final R_forContext r_for() throws RecognitionException {
		R_forContext _localctx = new R_forContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_r_for);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(For);
			setState(184);
			match(OpenBracket);
			setState(185);
			field();
			setState(186);
			maybeExpression();
			setState(187);
			expression(0);
			setState(188);
			match(CloseBracket);
			setState(189);
			match(OpenFigureBracket);
			setState(190);
			body();
			setState(191);
			match(CloseFigureBracket);
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

	public static class R_whileContext extends ParserRuleContext {
		public TerminalNode While() { return getToken(ProgramParser.While, 0); }
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public TerminalNode OpenFigureBracket() { return getToken(ProgramParser.OpenFigureBracket, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode CloseFigureBracket() { return getToken(ProgramParser.CloseFigureBracket, 0); }
		public R_whileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r_while; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterR_while(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitR_while(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitR_while(this);
			else return visitor.visitChildren(this);
		}
	}

	public final R_whileContext r_while() throws RecognitionException {
		R_whileContext _localctx = new R_whileContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_r_while);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(While);
			setState(194);
			match(OpenBracket);
			setState(195);
			expression(0);
			setState(196);
			match(CloseBracket);
			setState(197);
			match(OpenFigureBracket);
			setState(198);
			body();
			setState(199);
			match(CloseFigureBracket);
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

	public static class R_ifContext extends ParserRuleContext {
		public TerminalNode If() { return getToken(ProgramParser.If, 0); }
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public TerminalNode OpenFigureBracket() { return getToken(ProgramParser.OpenFigureBracket, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode CloseFigureBracket() { return getToken(ProgramParser.CloseFigureBracket, 0); }
		public R_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r_if; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterR_if(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitR_if(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitR_if(this);
			else return visitor.visitChildren(this);
		}
	}

	public final R_ifContext r_if() throws RecognitionException {
		R_ifContext _localctx = new R_ifContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_r_if);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(If);
			setState(202);
			match(OpenBracket);
			setState(203);
			expression(0);
			setState(204);
			match(CloseBracket);
			setState(205);
			match(OpenFigureBracket);
			setState(206);
			body();
			setState(207);
			match(CloseFigureBracket);
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

	public static class R_switchContext extends ParserRuleContext {
		public TerminalNode Switch() { return getToken(ProgramParser.Switch, 0); }
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public TerminalNode OpenFigureBracket() { return getToken(ProgramParser.OpenFigureBracket, 0); }
		public TerminalNode CloseFigureBracket() { return getToken(ProgramParser.CloseFigureBracket, 0); }
		public List<R_caseContext> r_case() {
			return getRuleContexts(R_caseContext.class);
		}
		public R_caseContext r_case(int i) {
			return getRuleContext(R_caseContext.class,i);
		}
		public R_switchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r_switch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterR_switch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitR_switch(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitR_switch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final R_switchContext r_switch() throws RecognitionException {
		R_switchContext _localctx = new R_switchContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_r_switch);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(Switch);
			setState(210);
			match(OpenBracket);
			setState(211);
			expression(0);
			setState(212);
			match(CloseBracket);
			setState(213);
			match(OpenFigureBracket);
			setState(215); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(214);
				r_case();
				}
				}
				setState(217); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Case );
			setState(219);
			match(CloseFigureBracket);
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

	public static class R_caseContext extends ParserRuleContext {
		public TerminalNode Case() { return getToken(ProgramParser.Case, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Colon() { return getToken(ProgramParser.Colon, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public R_caseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r_case; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterR_case(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitR_case(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitR_case(this);
			else return visitor.visitChildren(this);
		}
	}

	public final R_caseContext r_case() throws RecognitionException {
		R_caseContext _localctx = new R_caseContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_r_case);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(Case);
			setState(222);
			expression(0);
			setState(223);
			match(Colon);
			setState(224);
			body();
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

	public static class InstructionContext extends ParserRuleContext {
		public TerminalNode Semicolon() { return getToken(ProgramParser.Semicolon, 0); }
		public TerminalNode Delete() { return getToken(ProgramParser.Delete, 0); }
		public TerminalNode Continue() { return getToken(ProgramParser.Continue, 0); }
		public TerminalNode Break() { return getToken(ProgramParser.Break, 0); }
		public TerminalNode Return() { return getToken(ProgramParser.Return, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_instruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Delete:
				{
				setState(226);
				match(Delete);
				}
				break;
			case Continue:
				{
				setState(227);
				match(Continue);
				}
				break;
			case Break:
				{
				setState(228);
				match(Break);
				}
				break;
			case Return:
				{
				{
				setState(229);
				match(Return);
				setState(230);
				expression(0);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(233);
			match(Semicolon);
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

	public static class MaybeExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Semicolon() { return getToken(ProgramParser.Semicolon, 0); }
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public MaybeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_maybeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterMaybeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitMaybeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitMaybeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MaybeExpressionContext maybeExpression() throws RecognitionException {
		MaybeExpressionContext _localctx = new MaybeExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_maybeExpression);
		try {
			setState(240);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(235);
				expression(0);
				setState(236);
				match(Semicolon);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(238);
				match(Semicolon);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(239);
				field();
				}
				break;
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

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallingFunctionExpressionContext extends ExpressionContext {
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public CallingFunctionExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterCallingFunctionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitCallingFunctionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitCallingFunctionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PointerExpressionContext extends ExpressionContext {
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Point() { return getToken(ProgramParser.Point, 0); }
		public TerminalNode Pointer() { return getToken(ProgramParser.Pointer, 0); }
		public TerminalNode Child() { return getToken(ProgramParser.Child, 0); }
		public PointerExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterPointerExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitPointerExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitPointerExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryAssignOperatorContext binaryAssignOperator() {
			return getRuleContext(BinaryAssignOperatorContext.class,0);
		}
		public BinaryOperatorContext binaryOperator() {
			return getRuleContext(BinaryOperatorContext.class,0);
		}
		public BinaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterBinaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitBinaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBinaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValueExpressionContext extends ExpressionContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ValueExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterValueExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitValueExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitValueExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EmptyExpressionContext extends ExpressionContext {
		public EmptyExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterEmptyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitEmptyExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitEmptyExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SingleAssignExpressionContext extends ExpressionContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public SingleAssignOperatorContext singleAssignOperator() {
			return getRuleContext(SingleAssignOperatorContext.class,0);
		}
		public SingleAssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterSingleAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitSingleAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitSingleAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VectorExpressionContext extends ExpressionContext {
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public TerminalNode OpenSqBracket() { return getToken(ProgramParser.OpenSqBracket, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CloseSqBracket() { return getToken(ProgramParser.CloseSqBracket, 0); }
		public VectorExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterVectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitVectorExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitVectorExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InBracketExpressionContext extends ExpressionContext {
		public TerminalNode OpenBracket() { return getToken(ProgramParser.OpenBracket, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(ProgramParser.CloseBracket, 0); }
		public InBracketExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterInBracketExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitInBracketExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitInBracketExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryExpressionContext extends ExpressionContext {
		public UnaryOperationContext unaryOperation() {
			return getRuleContext(UnaryOperationContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				_localctx = new SingleAssignExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(243);
				value();
				setState(244);
				singleAssignOperator();
				}
				break;
			case 2:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(246);
				unaryOperation();
				setState(247);
				expression(7);
				}
				break;
			case 3:
				{
				_localctx = new InBracketExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(249);
				match(OpenBracket);
				setState(250);
				expression(0);
				setState(251);
				match(CloseBracket);
				}
				break;
			case 4:
				{
				_localctx = new CallingFunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(253);
				match(Word);
				setState(254);
				match(OpenBracket);
				setState(255);
				expression(0);
				setState(256);
				match(CloseBracket);
				}
				break;
			case 5:
				{
				_localctx = new VectorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(258);
				match(Word);
				setState(259);
				match(OpenSqBracket);
				setState(260);
				expression(0);
				setState(261);
				match(CloseSqBracket);
				}
				break;
			case 6:
				{
				_localctx = new PointerExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(263);
				match(Word);
				setState(264);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Point) | (1L << Pointer) | (1L << Child))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(265);
				expression(3);
				}
				break;
			case 7:
				{
				_localctx = new ValueExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(266);
				value();
				}
				break;
			case 8:
				{
				_localctx = new EmptyExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(279);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_expression);
					setState(270);
					if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
					setState(273);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case PlusAssign:
					case MinusAssign:
					case MultiplyAssign:
					case DivAssign:
					case ModAssign:
					case XorAssign:
					case AndAssign:
					case OrAssign:
					case LeftShiftAssign:
					case RightShiftAssign:
					case Assign:
						{
						setState(271);
						binaryAssignOperator();
						}
						break;
					case Plus:
					case Minus:
					case Multiply:
					case Div:
					case Mod:
					case Xor:
					case And:
					case Or:
					case Less:
					case Greater:
					case Equal:
					case NotEqual:
					case LessEqual:
					case GreaterEqual:
					case AndAnd:
					case OrOr:
					case LeftShift:
					case RightShift:
						{
						setState(272);
						binaryOperator();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(275);
					expression(9);
					}
					} 
				}
				setState(281);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode AnyInQuoutes() { return getToken(ProgramParser.AnyInQuoutes, 0); }
		public TerminalNode Number() { return getToken(ProgramParser.Number, 0); }
		public TerminalNode Word() { return getToken(ProgramParser.Word, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			_la = _input.LA(1);
			if ( !(((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (Number - 69)) | (1L << (Word - 69)) | (1L << (AnyInQuoutes - 69)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public static class BinaryAssignOperatorContext extends ParserRuleContext {
		public TerminalNode Assign() { return getToken(ProgramParser.Assign, 0); }
		public TerminalNode PlusAssign() { return getToken(ProgramParser.PlusAssign, 0); }
		public TerminalNode MinusAssign() { return getToken(ProgramParser.MinusAssign, 0); }
		public TerminalNode MultiplyAssign() { return getToken(ProgramParser.MultiplyAssign, 0); }
		public TerminalNode DivAssign() { return getToken(ProgramParser.DivAssign, 0); }
		public TerminalNode ModAssign() { return getToken(ProgramParser.ModAssign, 0); }
		public TerminalNode XorAssign() { return getToken(ProgramParser.XorAssign, 0); }
		public TerminalNode AndAssign() { return getToken(ProgramParser.AndAssign, 0); }
		public TerminalNode OrAssign() { return getToken(ProgramParser.OrAssign, 0); }
		public TerminalNode LeftShiftAssign() { return getToken(ProgramParser.LeftShiftAssign, 0); }
		public TerminalNode RightShiftAssign() { return getToken(ProgramParser.RightShiftAssign, 0); }
		public BinaryAssignOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryAssignOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterBinaryAssignOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitBinaryAssignOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBinaryAssignOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryAssignOperatorContext binaryAssignOperator() throws RecognitionException {
		BinaryAssignOperatorContext _localctx = new BinaryAssignOperatorContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_binaryAssignOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PlusAssign) | (1L << MinusAssign) | (1L << MultiplyAssign) | (1L << DivAssign) | (1L << ModAssign) | (1L << XorAssign) | (1L << AndAssign) | (1L << OrAssign) | (1L << LeftShiftAssign) | (1L << RightShiftAssign) | (1L << Assign))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Int() { return getToken(ProgramParser.Int, 0); }
		public TerminalNode LongLong() { return getToken(ProgramParser.LongLong, 0); }
		public TerminalNode Auto() { return getToken(ProgramParser.Auto, 0); }
		public TerminalNode Bool() { return getToken(ProgramParser.Bool, 0); }
		public TerminalNode Double() { return getToken(ProgramParser.Double, 0); }
		public TerminalNode LongDouble() { return getToken(ProgramParser.LongDouble, 0); }
		public TerminalNode Void() { return getToken(ProgramParser.Void, 0); }
		public TerminalNode String() { return getToken(ProgramParser.String, 0); }
		public TerminalNode Vector() { return getToken(ProgramParser.Vector, 0); }
		public TerminalNode Less() { return getToken(ProgramParser.Less, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Greater() { return getToken(ProgramParser.Greater, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_type);
		try {
			setState(299);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Int:
				enterOuterAlt(_localctx, 1);
				{
				setState(286);
				match(Int);
				}
				break;
			case LongLong:
				enterOuterAlt(_localctx, 2);
				{
				setState(287);
				match(LongLong);
				}
				break;
			case Auto:
				enterOuterAlt(_localctx, 3);
				{
				setState(288);
				match(Auto);
				}
				break;
			case Bool:
				enterOuterAlt(_localctx, 4);
				{
				setState(289);
				match(Bool);
				}
				break;
			case Double:
				enterOuterAlt(_localctx, 5);
				{
				setState(290);
				match(Double);
				}
				break;
			case LongDouble:
				enterOuterAlt(_localctx, 6);
				{
				setState(291);
				match(LongDouble);
				}
				break;
			case Void:
				enterOuterAlt(_localctx, 7);
				{
				setState(292);
				match(Void);
				}
				break;
			case String:
				enterOuterAlt(_localctx, 8);
				{
				setState(293);
				match(String);
				}
				break;
			case Vector:
				enterOuterAlt(_localctx, 9);
				{
				setState(294);
				match(Vector);
				setState(295);
				match(Less);
				setState(296);
				type();
				setState(297);
				match(Greater);
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

	public static class BinaryOperatorContext extends ParserRuleContext {
		public TerminalNode Plus() { return getToken(ProgramParser.Plus, 0); }
		public TerminalNode Minus() { return getToken(ProgramParser.Minus, 0); }
		public TerminalNode Multiply() { return getToken(ProgramParser.Multiply, 0); }
		public TerminalNode Div() { return getToken(ProgramParser.Div, 0); }
		public TerminalNode Mod() { return getToken(ProgramParser.Mod, 0); }
		public TerminalNode Xor() { return getToken(ProgramParser.Xor, 0); }
		public TerminalNode And() { return getToken(ProgramParser.And, 0); }
		public TerminalNode Or() { return getToken(ProgramParser.Or, 0); }
		public TerminalNode Less() { return getToken(ProgramParser.Less, 0); }
		public TerminalNode Greater() { return getToken(ProgramParser.Greater, 0); }
		public TerminalNode Equal() { return getToken(ProgramParser.Equal, 0); }
		public TerminalNode NotEqual() { return getToken(ProgramParser.NotEqual, 0); }
		public TerminalNode LessEqual() { return getToken(ProgramParser.LessEqual, 0); }
		public TerminalNode GreaterEqual() { return getToken(ProgramParser.GreaterEqual, 0); }
		public TerminalNode AndAnd() { return getToken(ProgramParser.AndAnd, 0); }
		public TerminalNode OrOr() { return getToken(ProgramParser.OrOr, 0); }
		public TerminalNode LeftShift() { return getToken(ProgramParser.LeftShift, 0); }
		public TerminalNode RightShift() { return getToken(ProgramParser.RightShift, 0); }
		public BinaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterBinaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitBinaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitBinaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryOperatorContext binaryOperator() throws RecognitionException {
		BinaryOperatorContext _localctx = new BinaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_binaryOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Plus) | (1L << Minus) | (1L << Multiply) | (1L << Div) | (1L << Mod) | (1L << Xor) | (1L << And) | (1L << Or) | (1L << Less) | (1L << Greater) | (1L << Equal) | (1L << NotEqual) | (1L << LessEqual) | (1L << GreaterEqual) | (1L << AndAnd) | (1L << OrOr) | (1L << LeftShift) | (1L << RightShift))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public static class UnaryOperationContext extends ParserRuleContext {
		public TerminalNode Not() { return getToken(ProgramParser.Not, 0); }
		public UnaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperationContext unaryOperation() throws RecognitionException {
		UnaryOperationContext _localctx = new UnaryOperationContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_unaryOperation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			match(Not);
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

	public static class SingleAssignOperatorContext extends ParserRuleContext {
		public TerminalNode PlusPlus() { return getToken(ProgramParser.PlusPlus, 0); }
		public TerminalNode MinusMinus() { return getToken(ProgramParser.MinusMinus, 0); }
		public SingleAssignOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleAssignOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).enterSingleAssignOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProgramListener ) ((ProgramListener)listener).exitSingleAssignOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ProgramVisitor ) return ((ProgramVisitor<? extends T>)visitor).visitSingleAssignOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleAssignOperatorContext singleAssignOperator() throws RecognitionException {
		SingleAssignOperatorContext _localctx = new SingleAssignOperatorContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_singleAssignOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			_la = _input.LA(1);
			if ( !(_la==PlusPlus || _la==MinusMinus) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 21:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3P\u0136\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\7\2=\n\2\f\2\16\2@\13"+
		"\2\3\3\7\3C\n\3\f\3\16\3F\13\3\3\4\3\4\3\4\3\4\3\4\5\4M\n\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\5\4U\n\4\3\4\5\4X\n\4\3\5\3\5\3\5\3\5\6\5^\n\5\r\5\16\5"+
		"_\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6"+
		"r\n\6\3\7\3\7\3\7\7\7w\n\7\f\7\16\7z\13\7\3\7\3\7\5\7~\n\7\3\b\3\b\5\b"+
		"\u0082\n\b\3\b\5\b\u0085\n\b\3\t\6\t\u0088\n\t\r\t\16\t\u0089\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\7\f\u009a\n\f\f"+
		"\f\16\f\u009d\13\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00a7\n\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u00b5\n\17"+
		"\f\17\16\17\u00b8\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\6\23\u00da\n\23\r\23\16\23"+
		"\u00db\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\5\25"+
		"\u00ea\n\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\5\26\u00f3\n\26\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u010f\n\27"+
		"\3\27\3\27\3\27\5\27\u0114\n\27\3\27\3\27\7\27\u0118\n\27\f\27\16\27\u011b"+
		"\13\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\5\32\u012e\n\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35"+
		"\2\3,\36\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668"+
		"\2\7\4\2\65\6699\4\2GGNO\4\2\21\32--\3\2\33,\3\2\16\17\2\u0149\2:\3\2"+
		"\2\2\4D\3\2\2\2\6W\3\2\2\2\b]\3\2\2\2\nq\3\2\2\2\f}\3\2\2\2\16\177\3\2"+
		"\2\2\20\u0087\3\2\2\2\22\u008b\3\2\2\2\24\u0091\3\2\2\2\26\u009b\3\2\2"+
		"\2\30\u00a6\3\2\2\2\32\u00a8\3\2\2\2\34\u00b6\3\2\2\2\36\u00b9\3\2\2\2"+
		" \u00c3\3\2\2\2\"\u00cb\3\2\2\2$\u00d3\3\2\2\2&\u00df\3\2\2\2(\u00e9\3"+
		"\2\2\2*\u00f2\3\2\2\2,\u010e\3\2\2\2.\u011c\3\2\2\2\60\u011e\3\2\2\2\62"+
		"\u012d\3\2\2\2\64\u012f\3\2\2\2\66\u0131\3\2\2\28\u0133\3\2\2\2:>\5\4"+
		"\3\2;=\5\b\5\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?\3\3\2\2\2@>\3"+
		"\2\2\2AC\5\6\4\2BA\3\2\2\2CF\3\2\2\2DB\3\2\2\2DE\3\2\2\2E\5\3\2\2\2FD"+
		"\3\2\2\2GH\7?\2\2HI\7#\2\2IL\7N\2\2JK\7\65\2\2KM\7N\2\2LJ\3\2\2\2LM\3"+
		"\2\2\2MN\3\2\2\2NX\7$\2\2OP\7?\2\2PQ\7\3\2\2QT\7N\2\2RS\7\65\2\2SU\7N"+
		"\2\2TR\3\2\2\2TU\3\2\2\2UV\3\2\2\2VX\7\3\2\2WG\3\2\2\2WO\3\2\2\2X\7\3"+
		"\2\2\2Y^\5\n\6\2Z^\5\22\n\2[^\5\24\13\2\\^\5\32\16\2]Y\3\2\2\2]Z\3\2\2"+
		"\2][\3\2\2\2]\\\3\2\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\t\3\2\2\2ab\5\62"+
		"\32\2bc\7N\2\2cd\7\61\2\2de\5\f\7\2ef\7\62\2\2fg\7\63\2\2gh\5\34\17\2"+
		"hi\7\64\2\2ir\3\2\2\2jk\5\62\32\2kl\7N\2\2lm\7\61\2\2mn\5\f\7\2no\7\62"+
		"\2\2op\7\60\2\2pr\3\2\2\2qa\3\2\2\2qj\3\2\2\2r\13\3\2\2\2st\5\16\b\2t"+
		"u\7/\2\2uw\3\2\2\2vs\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2\2z"+
		"x\3\2\2\2{~\5\16\b\2|~\3\2\2\2}x\3\2\2\2}|\3\2\2\2~\r\3\2\2\2\177\u0081"+
		"\5\62\32\2\u0080\u0082\5\20\t\2\u0081\u0080\3\2\2\2\u0081\u0082\3\2\2"+
		"\2\u0082\u0084\3\2\2\2\u0083\u0085\7N\2\2\u0084\u0083\3\2\2\2\u0084\u0085"+
		"\3\2\2\2\u0085\17\3\2\2\2\u0086\u0088\7\35\2\2\u0087\u0086\3\2\2\2\u0088"+
		"\u0089\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\21\3\2\2"+
		"\2\u008b\u008c\7B\2\2\u008c\u008d\7N\2\2\u008d\u008e\7\63\2\2\u008e\u008f"+
		"\5\26\f\2\u008f\u0090\7\64\2\2\u0090\23\3\2\2\2\u0091\u0092\7C\2\2\u0092"+
		"\u0093\7N\2\2\u0093\u0094\7\63\2\2\u0094\u0095\5\26\f\2\u0095\u0096\7"+
		"\64\2\2\u0096\25\3\2\2\2\u0097\u009a\5\30\r\2\u0098\u009a\5\n\6\2\u0099"+
		"\u0097\3\2\2\2\u0099\u0098\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2"+
		"\2\2\u009b\u009c\3\2\2\2\u009c\27\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u009f"+
		"\5\62\32\2\u009f\u00a0\7N\2\2\u00a0\u00a1\7\60\2\2\u00a1\u00a7\3\2\2\2"+
		"\u00a2\u00a3\5\62\32\2\u00a3\u00a4\5,\27\2\u00a4\u00a5\7\60\2\2\u00a5"+
		"\u00a7\3\2\2\2\u00a6\u009e\3\2\2\2\u00a6\u00a2\3\2\2\2\u00a7\31\3\2\2"+
		"\2\u00a8\u00a9\7\4\2\2\u00a9\u00aa\7N\2\2\u00aa\u00ab\7N\2\2\u00ab\u00ac"+
		"\7\60\2\2\u00ac\33\3\2\2\2\u00ad\u00b5\5*\26\2\u00ae\u00b5\5\36\20\2\u00af"+
		"\u00b5\5 \21\2\u00b0\u00b5\5(\25\2\u00b1\u00b5\5\"\22\2\u00b2\u00b5\5"+
		"\30\r\2\u00b3\u00b5\5$\23\2\u00b4\u00ad\3\2\2\2\u00b4\u00ae\3\2\2\2\u00b4"+
		"\u00af\3\2\2\2\u00b4\u00b0\3\2\2\2\u00b4\u00b1\3\2\2\2\u00b4\u00b2\3\2"+
		"\2\2\u00b4\u00b3\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6"+
		"\u00b7\3\2\2\2\u00b7\35\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba\7@\2\2"+
		"\u00ba\u00bb\7\61\2\2\u00bb\u00bc\5\30\r\2\u00bc\u00bd\5*\26\2\u00bd\u00be"+
		"\5,\27\2\u00be\u00bf\7\62\2\2\u00bf\u00c0\7\63\2\2\u00c0\u00c1\5\34\17"+
		"\2\u00c1\u00c2\7\64\2\2\u00c2\37\3\2\2\2\u00c3\u00c4\7A\2\2\u00c4\u00c5"+
		"\7\61\2\2\u00c5\u00c6\5,\27\2\u00c6\u00c7\7\62\2\2\u00c7\u00c8\7\63\2"+
		"\2\u00c8\u00c9\5\34\17\2\u00c9\u00ca\7\64\2\2\u00ca!\3\2\2\2\u00cb\u00cc"+
		"\7D\2\2\u00cc\u00cd\7\61\2\2\u00cd\u00ce\5,\27\2\u00ce\u00cf\7\62\2\2"+
		"\u00cf\u00d0\7\63\2\2\u00d0\u00d1\5\34\17\2\u00d1\u00d2\7\64\2\2\u00d2"+
		"#\3\2\2\2\u00d3\u00d4\7E\2\2\u00d4\u00d5\7\61\2\2\u00d5\u00d6\5,\27\2"+
		"\u00d6\u00d7\7\62\2\2\u00d7\u00d9\7\63\2\2\u00d8\u00da\5&\24\2\u00d9\u00d8"+
		"\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"\u00dd\3\2\2\2\u00dd\u00de\7\64\2\2\u00de%\3\2\2\2\u00df\u00e0\7F\2\2"+
		"\u00e0\u00e1\5,\27\2\u00e1\u00e2\7:\2\2\u00e2\u00e3\5\34\17\2\u00e3\'"+
		"\3\2\2\2\u00e4\u00ea\7>\2\2\u00e5\u00ea\7=\2\2\u00e6\u00ea\7;\2\2\u00e7"+
		"\u00e8\7<\2\2\u00e8\u00ea\5,\27\2\u00e9\u00e4\3\2\2\2\u00e9\u00e5\3\2"+
		"\2\2\u00e9\u00e6\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb"+
		"\u00ec\7\60\2\2\u00ec)\3\2\2\2\u00ed\u00ee\5,\27\2\u00ee\u00ef\7\60\2"+
		"\2\u00ef\u00f3\3\2\2\2\u00f0\u00f3\7\60\2\2\u00f1\u00f3\5\30\r\2\u00f2"+
		"\u00ed\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f1\3\2\2\2\u00f3+\3\2\2\2"+
		"\u00f4\u00f5\b\27\1\2\u00f5\u00f6\5.\30\2\u00f6\u00f7\58\35\2\u00f7\u010f"+
		"\3\2\2\2\u00f8\u00f9\5\66\34\2\u00f9\u00fa\5,\27\t\u00fa\u010f\3\2\2\2"+
		"\u00fb\u00fc\7\61\2\2\u00fc\u00fd\5,\27\2\u00fd\u00fe\7\62\2\2\u00fe\u010f"+
		"\3\2\2\2\u00ff\u0100\7N\2\2\u0100\u0101\7\61\2\2\u0101\u0102\5,\27\2\u0102"+
		"\u0103\7\62\2\2\u0103\u010f\3\2\2\2\u0104\u0105\7N\2\2\u0105\u0106\7\67"+
		"\2\2\u0106\u0107\5,\27\2\u0107\u0108\78\2\2\u0108\u010f\3\2\2\2\u0109"+
		"\u010a\7N\2\2\u010a\u010b\t\2\2\2\u010b\u010f\5,\27\5\u010c\u010f\5.\30"+
		"\2\u010d\u010f\3\2\2\2\u010e\u00f4\3\2\2\2\u010e\u00f8\3\2\2\2\u010e\u00fb"+
		"\3\2\2\2\u010e\u00ff\3\2\2\2\u010e\u0104\3\2\2\2\u010e\u0109\3\2\2\2\u010e"+
		"\u010c\3\2\2\2\u010e\u010d\3\2\2\2\u010f\u0119\3\2\2\2\u0110\u0113\f\n"+
		"\2\2\u0111\u0114\5\60\31\2\u0112\u0114\5\64\33\2\u0113\u0111\3\2\2\2\u0113"+
		"\u0112\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\5,\27\13\u0116\u0118\3"+
		"\2\2\2\u0117\u0110\3\2\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011a-\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011d\t\3\2\2"+
		"\u011d/\3\2\2\2\u011e\u011f\t\4\2\2\u011f\61\3\2\2\2\u0120\u012e\7\5\2"+
		"\2\u0121\u012e\7\6\2\2\u0122\u012e\7\7\2\2\u0123\u012e\7\b\2\2\u0124\u012e"+
		"\7\t\2\2\u0125\u012e\7\n\2\2\u0126\u012e\7\13\2\2\u0127\u012e\7\f\2\2"+
		"\u0128\u0129\7\r\2\2\u0129\u012a\7#\2\2\u012a\u012b\5\62\32\2\u012b\u012c"+
		"\7$\2\2\u012c\u012e\3\2\2\2\u012d\u0120\3\2\2\2\u012d\u0121\3\2\2\2\u012d"+
		"\u0122\3\2\2\2\u012d\u0123\3\2\2\2\u012d\u0124\3\2\2\2\u012d\u0125\3\2"+
		"\2\2\u012d\u0126\3\2\2\2\u012d\u0127\3\2\2\2\u012d\u0128\3\2\2\2\u012e"+
		"\63\3\2\2\2\u012f\u0130\t\5\2\2\u0130\65\3\2\2\2\u0131\u0132\7\20\2\2"+
		"\u0132\67\3\2\2\2\u0133\u0134\t\6\2\2\u01349\3\2\2\2\33>DLTW]_qx}\u0081"+
		"\u0084\u0089\u0099\u009b\u00a6\u00b4\u00b6\u00db\u00e9\u00f2\u010e\u0113"+
		"\u0119\u012d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}