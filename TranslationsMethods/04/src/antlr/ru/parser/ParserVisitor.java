// Generated from /home/vladkuznetsov/Vl/Projects/MT/04/src/main/java/generator/Parser.g4 by ANTLR 4.8
package ru.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ParserParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ParserParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(ParserParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(ParserParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#global}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal(ParserParser.GlobalContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken(ParserParser.TokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#lexem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexem(ParserParser.LexemContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#functions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctions(ParserParser.FunctionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#matches}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatches(ParserParser.MatchesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#match}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatch(ParserParser.MatchContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#regularas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegularas(ParserParser.RegularasContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParserParser#regular}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegular(ParserParser.RegularContext ctx);
}