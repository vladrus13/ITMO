// Generated from /home/vladkuznetsov/Vl/Projects/MT/03/src/main/java/antlr/Program.g4 by ANTLR 4.8
package ru.parser.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ProgramParser}.
 */
public interface ProgramListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ProgramParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(ProgramParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(ProgramParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#headers}.
	 * @param ctx the parse tree
	 */
	void enterHeaders(ProgramParser.HeadersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#headers}.
	 * @param ctx the parse tree
	 */
	void exitHeaders(ProgramParser.HeadersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(ProgramParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(ProgramParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ProgramParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ProgramParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fullFunction}
	 * labeled alternative in {@link ProgramParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFullFunction(ProgramParser.FullFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fullFunction}
	 * labeled alternative in {@link ProgramParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFullFunction(ProgramParser.FullFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notFullFunction}
	 * labeled alternative in {@link ProgramParser#function}.
	 * @param ctx the parse tree
	 */
	void enterNotFullFunction(ProgramParser.NotFullFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notFullFunction}
	 * labeled alternative in {@link ProgramParser#function}.
	 * @param ctx the parse tree
	 */
	void exitNotFullFunction(ProgramParser.NotFullFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(ProgramParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(ProgramParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(ProgramParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(ProgramParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#references}.
	 * @param ctx the parse tree
	 */
	void enterReferences(ProgramParser.ReferencesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#references}.
	 * @param ctx the parse tree
	 */
	void exitReferences(ProgramParser.ReferencesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#r_class}.
	 * @param ctx the parse tree
	 */
	void enterR_class(ProgramParser.R_classContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#r_class}.
	 * @param ctx the parse tree
	 */
	void exitR_class(ProgramParser.R_classContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#struct}.
	 * @param ctx the parse tree
	 */
	void enterStruct(ProgramParser.StructContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#struct}.
	 * @param ctx the parse tree
	 */
	void exitStruct(ProgramParser.StructContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#classOrStructBody}.
	 * @param ctx the parse tree
	 */
	void enterClassOrStructBody(ProgramParser.ClassOrStructBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#classOrStructBody}.
	 * @param ctx the parse tree
	 */
	void exitClassOrStructBody(ProgramParser.ClassOrStructBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(ProgramParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(ProgramParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#using}.
	 * @param ctx the parse tree
	 */
	void enterUsing(ProgramParser.UsingContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#using}.
	 * @param ctx the parse tree
	 */
	void exitUsing(ProgramParser.UsingContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(ProgramParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(ProgramParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#r_for}.
	 * @param ctx the parse tree
	 */
	void enterR_for(ProgramParser.R_forContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#r_for}.
	 * @param ctx the parse tree
	 */
	void exitR_for(ProgramParser.R_forContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#r_while}.
	 * @param ctx the parse tree
	 */
	void enterR_while(ProgramParser.R_whileContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#r_while}.
	 * @param ctx the parse tree
	 */
	void exitR_while(ProgramParser.R_whileContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#r_if}.
	 * @param ctx the parse tree
	 */
	void enterR_if(ProgramParser.R_ifContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#r_if}.
	 * @param ctx the parse tree
	 */
	void exitR_if(ProgramParser.R_ifContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#r_switch}.
	 * @param ctx the parse tree
	 */
	void enterR_switch(ProgramParser.R_switchContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#r_switch}.
	 * @param ctx the parse tree
	 */
	void exitR_switch(ProgramParser.R_switchContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#r_case}.
	 * @param ctx the parse tree
	 */
	void enterR_case(ProgramParser.R_caseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#r_case}.
	 * @param ctx the parse tree
	 */
	void exitR_case(ProgramParser.R_caseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(ProgramParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(ProgramParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#maybeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMaybeExpression(ProgramParser.MaybeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#maybeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMaybeExpression(ProgramParser.MaybeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callingFunctionExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCallingFunctionExpression(ProgramParser.CallingFunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callingFunctionExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCallingFunctionExpression(ProgramParser.CallingFunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pointerExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPointerExpression(ProgramParser.PointerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pointerExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPointerExpression(ProgramParser.PointerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(ProgramParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(ProgramParser.BinaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterValueExpression(ProgramParser.ValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitValueExpression(ProgramParser.ValueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEmptyExpression(ProgramParser.EmptyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEmptyExpression(ProgramParser.EmptyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleAssignExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSingleAssignExpression(ProgramParser.SingleAssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleAssignExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSingleAssignExpression(ProgramParser.SingleAssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code vectorExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVectorExpression(ProgramParser.VectorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code vectorExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVectorExpression(ProgramParser.VectorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inBracketExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInBracketExpression(ProgramParser.InBracketExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inBracketExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInBracketExpression(ProgramParser.InBracketExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(ProgramParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link ProgramParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(ProgramParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ProgramParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ProgramParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#binaryAssignOperator}.
	 * @param ctx the parse tree
	 */
	void enterBinaryAssignOperator(ProgramParser.BinaryAssignOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#binaryAssignOperator}.
	 * @param ctx the parse tree
	 */
	void exitBinaryAssignOperator(ProgramParser.BinaryAssignOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ProgramParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ProgramParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOperator(ProgramParser.BinaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOperator(ProgramParser.BinaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#unaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperation(ProgramParser.UnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#unaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperation(ProgramParser.UnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProgramParser#singleAssignOperator}.
	 * @param ctx the parse tree
	 */
	void enterSingleAssignOperator(ProgramParser.SingleAssignOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProgramParser#singleAssignOperator}.
	 * @param ctx the parse tree
	 */
	void exitSingleAssignOperator(ProgramParser.SingleAssignOperatorContext ctx);
}