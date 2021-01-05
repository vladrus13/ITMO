// Generated from /home/vladkuznetsov/Vl/Projects/MT/04/src/main/java/generator/Parser.g4 by ANTLR 4.8
package ru.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ParserParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            Data = 10, Varible = 11, Regular = 12, StrongRegular = 13, TokenName = 14, LexemName = 15,
            WS = 16;
    public static final int
            RULE_file = 0, RULE_main = 1, RULE_global = 2, RULE_token = 3, RULE_lexem = 4,
            RULE_functions = 5, RULE_matches = 6, RULE_match = 7, RULE_regularas = 8,
            RULE_regular = 9;

    private static String[] makeRuleNames() {
        return new String[]{
                "file", "main", "global", "token", "lexem", "functions", "matches", "match",
                "regularas", "regular"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'!'", "';'", "':'", "'*'", "'['", "']'", "'|'", "'{'", "'}'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
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

    @Override
    public String getGrammarFileName() {
        return "Parser.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public ParserParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class FileContext extends ParserRuleContext {
        public MainContext main() {
            return getRuleContext(MainContext.class, 0);
        }

        public GlobalContext global() {
            return getRuleContext(GlobalContext.class, 0);
        }

        public List<TokenContext> token() {
            return getRuleContexts(TokenContext.class);
        }

        public TokenContext token(int i) {
            return getRuleContext(TokenContext.class, i);
        }

        public List<LexemContext> lexem() {
            return getRuleContexts(LexemContext.class);
        }

        public LexemContext lexem(int i) {
            return getRuleContext(LexemContext.class, i);
        }

        public FileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_file;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterFile(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitFile(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitFile(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FileContext file() throws RecognitionException {
        FileContext _localctx = new FileContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_file);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(20);
                main();
                setState(21);
                global();
                setState(26);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == TokenName || _la == LexemName) {
                    {
                        setState(24);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case TokenName: {
                                setState(22);
                                token();
                            }
                            break;
                            case LexemName: {
                                setState(23);
                                lexem();
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    setState(28);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class MainContext extends ParserRuleContext {
        public TerminalNode TokenName() {
            return getToken(ParserParser.TokenName, 0);
        }

        public TerminalNode LexemName() {
            return getToken(ParserParser.LexemName, 0);
        }

        public MainContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_main;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterMain(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitMain(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitMain(this);
            else return visitor.visitChildren(this);
        }
    }

    public final MainContext main() throws RecognitionException {
        MainContext _localctx = new MainContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_main);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(29);
                match(T__0);
                setState(30);
                _la = _input.LA(1);
                if (!(_la == TokenName || _la == LexemName)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(31);
                match(T__1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class GlobalContext extends ParserRuleContext {
        public List<TerminalNode> Varible() {
            return getTokens(ParserParser.Varible);
        }

        public TerminalNode Varible(int i) {
            return getToken(ParserParser.Varible, i);
        }

        public GlobalContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_global;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterGlobal(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitGlobal(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitGlobal(this);
            else return visitor.visitChildren(this);
        }
    }

    public final GlobalContext global() throws RecognitionException {
        GlobalContext _localctx = new GlobalContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_global);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(36);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == Varible) {
                    {
                        {
                            setState(33);
                            match(Varible);
                        }
                    }
                    setState(38);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class TokenContext extends ParserRuleContext {
        public TerminalNode TokenName() {
            return getToken(ParserParser.TokenName, 0);
        }

        public RegularasContext regularas() {
            return getRuleContext(RegularasContext.class, 0);
        }

        public TokenContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_token;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterToken(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitToken(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitToken(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TokenContext token() throws RecognitionException {
        TokenContext _localctx = new TokenContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_token);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(39);
                match(TokenName);
                setState(40);
                match(T__2);
                {
                    setState(41);
                    regularas();
                }
                setState(43);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__3) {
                    {
                        setState(42);
                        match(T__3);
                    }
                }

                setState(45);
                match(T__1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class LexemContext extends ParserRuleContext {
        public TerminalNode LexemName() {
            return getToken(ParserParser.LexemName, 0);
        }

        public MatchesContext matches() {
            return getRuleContext(MatchesContext.class, 0);
        }

        public LexemContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_lexem;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterLexem(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitLexem(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitLexem(this);
            else return visitor.visitChildren(this);
        }
    }

    public final LexemContext lexem() throws RecognitionException {
        LexemContext _localctx = new LexemContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_lexem);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(47);
                match(LexemName);
                setState(48);
                match(T__2);
                {
                    setState(49);
                    matches();
                }
                setState(50);
                match(T__1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FunctionsContext extends ParserRuleContext {
        public List<TerminalNode> Data() {
            return getTokens(ParserParser.Data);
        }

        public TerminalNode Data(int i) {
            return getToken(ParserParser.Data, i);
        }

        public FunctionsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_functions;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterFunctions(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitFunctions(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitFunctions(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FunctionsContext functions() throws RecognitionException {
        FunctionsContext _localctx = new FunctionsContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_functions);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(57);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__4) {
                    {
                        {
                            setState(52);
                            match(T__4);
                            setState(53);
                            match(Data);
                            setState(54);
                            match(T__5);
                        }
                    }
                    setState(59);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class MatchesContext extends ParserRuleContext {
        public List<MatchContext> match() {
            return getRuleContexts(MatchContext.class);
        }

        public MatchContext match(int i) {
            return getRuleContext(MatchContext.class, i);
        }

        public MatchesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_matches;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterMatches(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitMatches(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitMatches(this);
            else return visitor.visitChildren(this);
        }
    }

    public final MatchesContext matches() throws RecognitionException {
        MatchesContext _localctx = new MatchesContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_matches);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(60);
                match();
                setState(65);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__6) {
                    {
                        {
                            setState(61);
                            match(T__6);
                            setState(62);
                            match();
                        }
                    }
                    setState(67);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class MatchContext extends ParserRuleContext {
        public FunctionsContext functions() {
            return getRuleContext(FunctionsContext.class, 0);
        }

        public List<TerminalNode> TokenName() {
            return getTokens(ParserParser.TokenName);
        }

        public TerminalNode TokenName(int i) {
            return getToken(ParserParser.TokenName, i);
        }

        public List<TerminalNode> LexemName() {
            return getTokens(ParserParser.LexemName);
        }

        public TerminalNode LexemName(int i) {
            return getToken(ParserParser.LexemName, i);
        }

        public MatchContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_match;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterMatch(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitMatch(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitMatch(this);
            else return visitor.visitChildren(this);
        }
    }

    public final MatchContext match() throws RecognitionException {
        MatchContext _localctx = new MatchContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_match);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(71);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == TokenName || _la == LexemName) {
                    {
                        {
                            setState(68);
                            _la = _input.LA(1);
                            if (!(_la == TokenName || _la == LexemName)) {
                                _errHandler.recoverInline(this);
                            } else {
                                if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                _errHandler.reportMatch(this);
                                consume();
                            }
                        }
                    }
                    setState(73);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(78);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__7) {
                    {
                        setState(74);
                        match(T__7);
                        setState(75);
                        functions();
                        setState(76);
                        match(T__8);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class RegularasContext extends ParserRuleContext {
        public List<RegularContext> regular() {
            return getRuleContexts(RegularContext.class);
        }

        public RegularContext regular(int i) {
            return getRuleContext(RegularContext.class, i);
        }

        public RegularasContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_regularas;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterRegularas(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitRegularas(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitRegularas(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RegularasContext regularas() throws RecognitionException {
        RegularasContext _localctx = new RegularasContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_regularas);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(80);
                regular();
                setState(85);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__6) {
                    {
                        {
                            setState(81);
                            match(T__6);
                            setState(82);
                            regular();
                        }
                    }
                    setState(87);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class RegularContext extends ParserRuleContext {
        public List<TerminalNode> Regular() {
            return getTokens(ParserParser.Regular);
        }

        public TerminalNode Regular(int i) {
            return getToken(ParserParser.Regular, i);
        }

        public List<TerminalNode> StrongRegular() {
            return getTokens(ParserParser.StrongRegular);
        }

        public TerminalNode StrongRegular(int i) {
            return getToken(ParserParser.StrongRegular, i);
        }

        public RegularContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_regular;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).enterRegular(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ParserListener) ((ParserListener) listener).exitRegular(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ParserVisitor) return ((ParserVisitor<? extends T>) visitor).visitRegular(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RegularContext regular() throws RecognitionException {
        RegularContext _localctx = new RegularContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_regular);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(91);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == Regular || _la == StrongRegular) {
                    {
                        {
                            setState(88);
                            _la = _input.LA(1);
                            if (!(_la == Regular || _la == StrongRegular)) {
                                _errHandler.recoverInline(this);
                            } else {
                                if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                _errHandler.reportMatch(this);
                                consume();
                            }
                        }
                    }
                    setState(93);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\22a\4\2\t\2\4\3\t" +
                    "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3" +
                    "\2\3\2\3\2\3\2\7\2\33\n\2\f\2\16\2\36\13\2\3\3\3\3\3\3\3\3\3\4\7\4%\n" +
                    "\4\f\4\16\4(\13\4\3\5\3\5\3\5\3\5\5\5.\n\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6" +
                    "\3\7\3\7\3\7\7\7:\n\7\f\7\16\7=\13\7\3\b\3\b\3\b\7\bB\n\b\f\b\16\bE\13" +
                    "\b\3\t\7\tH\n\t\f\t\16\tK\13\t\3\t\3\t\3\t\3\t\5\tQ\n\t\3\n\3\n\3\n\7" +
                    "\nV\n\n\f\n\16\nY\13\n\3\13\7\13\\\n\13\f\13\16\13_\13\13\3\13\2\2\f\2" +
                    "\4\6\b\n\f\16\20\22\24\2\4\3\2\20\21\3\2\16\17\2`\2\26\3\2\2\2\4\37\3" +
                    "\2\2\2\6&\3\2\2\2\b)\3\2\2\2\n\61\3\2\2\2\f;\3\2\2\2\16>\3\2\2\2\20I\3" +
                    "\2\2\2\22R\3\2\2\2\24]\3\2\2\2\26\27\5\4\3\2\27\34\5\6\4\2\30\33\5\b\5" +
                    "\2\31\33\5\n\6\2\32\30\3\2\2\2\32\31\3\2\2\2\33\36\3\2\2\2\34\32\3\2\2" +
                    "\2\34\35\3\2\2\2\35\3\3\2\2\2\36\34\3\2\2\2\37 \7\3\2\2 !\t\2\2\2!\"\7" +
                    "\4\2\2\"\5\3\2\2\2#%\7\r\2\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2" +
                    "\'\7\3\2\2\2(&\3\2\2\2)*\7\20\2\2*+\7\5\2\2+-\5\22\n\2,.\7\6\2\2-,\3\2" +
                    "\2\2-.\3\2\2\2./\3\2\2\2/\60\7\4\2\2\60\t\3\2\2\2\61\62\7\21\2\2\62\63" +
                    "\7\5\2\2\63\64\5\16\b\2\64\65\7\4\2\2\65\13\3\2\2\2\66\67\7\7\2\2\678" +
                    "\7\f\2\28:\7\b\2\29\66\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<\r\3\2\2" +
                    "\2=;\3\2\2\2>C\5\20\t\2?@\7\t\2\2@B\5\20\t\2A?\3\2\2\2BE\3\2\2\2CA\3\2" +
                    "\2\2CD\3\2\2\2D\17\3\2\2\2EC\3\2\2\2FH\t\2\2\2GF\3\2\2\2HK\3\2\2\2IG\3" +
                    "\2\2\2IJ\3\2\2\2JP\3\2\2\2KI\3\2\2\2LM\7\n\2\2MN\5\f\7\2NO\7\13\2\2OQ" +
                    "\3\2\2\2PL\3\2\2\2PQ\3\2\2\2Q\21\3\2\2\2RW\5\24\13\2ST\7\t\2\2TV\5\24" +
                    "\13\2US\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2X\23\3\2\2\2YW\3\2\2\2Z\\" +
                    "\t\3\2\2[Z\3\2\2\2\\_\3\2\2\2][\3\2\2\2]^\3\2\2\2^\25\3\2\2\2_]\3\2\2" +
                    "\2\f\32\34&-;CIPW]";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}