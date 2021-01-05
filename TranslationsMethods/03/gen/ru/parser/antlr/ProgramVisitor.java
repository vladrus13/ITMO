// Generated from /home/vladkuznetsov/Vl/Projects/MT/03/src/main/java/antlr/Program.g4 by ANTLR 4.8
package ru.parser.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ProgramParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ProgramVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ProgramParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ProgramParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#headers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaders(ProgramParser.HeadersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(ProgramParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ProgramParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fullFunction}
	 * labeled alternative in {@link ProgramParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullFunction(ProgramParser.FullFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notFullFunction}
	 * labeled alternative in {@link ProgramParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotFullFunction(ProgramParser.NotFullFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(ProgramParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(ProgramParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#references}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferences(ProgramParser.ReferencesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#r_class}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR_class(ProgramParser.R_classContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct(ProgramParser.StructContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#classOrStructBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrStructBody(ProgramParser.ClassOrStructBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(ProgramParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#using}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsing(ProgramParser.UsingContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(ProgramParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#r_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR_for(ProgramParser.R_forContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#r_while}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR_while(ProgramParser.R_whileContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#r_if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR_if(ProgramParser.R_ifContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#r_switch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR_switch(ProgramParser.R_switchContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#r_case}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR_case(ProgramParser.R_caseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(ProgramParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#maybeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaybeExpression(ProgramParser.MaybeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callingFunctionExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallingFunctionExpression(ProgramParser.CallingFunctionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pointerExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointerExpression(ProgramParser.PointerExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpression(ProgramParser.BinaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueExpression(ProgramParser.ValueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyExpression(ProgramParser.EmptyExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleAssignExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleAssignExpression(ProgramParser.SingleAssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code vectorExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVectorExpression(ProgramParser.VectorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inBracketExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInBracketExpression(ProgramParser.InBracketExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ProgramParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ProgramParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#binaryAssignOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryAssignOperator(ProgramParser.BinaryAssignOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ProgramParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#binaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOperator(ProgramParser.BinaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#unaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperation(ProgramParser.UnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProgramParser#singleAssignOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleAssignOperator(ProgramParser.SingleAssignOperatorContext ctx);
}