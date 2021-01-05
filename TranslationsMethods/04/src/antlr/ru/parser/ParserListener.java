// Generated from /home/vladkuznetsov/Vl/Projects/MT/04/src/main/java/generator/Parser.g4 by ANTLR 4.8
package ru.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ParserParser}.
 */
public interface ParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ParserParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(ParserParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(ParserParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(ParserParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(ParserParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#global}.
	 * @param ctx the parse tree
	 */
	void enterGlobal(ParserParser.GlobalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#global}.
	 * @param ctx the parse tree
	 */
	void exitGlobal(ParserParser.GlobalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#token}.
	 * @param ctx the parse tree
	 */
	void enterToken(ParserParser.TokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#token}.
	 * @param ctx the parse tree
	 */
	void exitToken(ParserParser.TokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#lexem}.
	 * @param ctx the parse tree
	 */
	void enterLexem(ParserParser.LexemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#lexem}.
	 * @param ctx the parse tree
	 */
	void exitLexem(ParserParser.LexemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#functions}.
	 * @param ctx the parse tree
	 */
	void enterFunctions(ParserParser.FunctionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#functions}.
	 * @param ctx the parse tree
	 */
	void exitFunctions(ParserParser.FunctionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#matches}.
	 * @param ctx the parse tree
	 */
	void enterMatches(ParserParser.MatchesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#matches}.
	 * @param ctx the parse tree
	 */
	void exitMatches(ParserParser.MatchesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#match}.
	 * @param ctx the parse tree
	 */
	void enterMatch(ParserParser.MatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#match}.
	 * @param ctx the parse tree
	 */
	void exitMatch(ParserParser.MatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#regularas}.
	 * @param ctx the parse tree
	 */
	void enterRegularas(ParserParser.RegularasContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#regularas}.
	 * @param ctx the parse tree
	 */
	void exitRegularas(ParserParser.RegularasContext ctx);
	/**
	 * Enter a parse tree produced by {@link ParserParser#regular}.
	 * @param ctx the parse tree
	 */
	void enterRegular(ParserParser.RegularContext ctx);
	/**
	 * Exit a parse tree produced by {@link ParserParser#regular}.
	 * @param ctx the parse tree
	 */
	void exitRegular(ParserParser.RegularContext ctx);
}