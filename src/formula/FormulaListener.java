package formula;// Generated from Formula.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FormulaParser}.
 */
public interface FormulaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FormulaParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(FormulaParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(FormulaParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doThen}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoThen(FormulaParser.DoThenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doThen}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoThen(FormulaParser.DoThenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doOr}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoOr(FormulaParser.DoOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doOr}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoOr(FormulaParser.DoOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doXor}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoXor(FormulaParser.DoXorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doXor}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoXor(FormulaParser.DoXorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newIdentifier}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewIdentifier(FormulaParser.NewIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newIdentifier}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewIdentifier(FormulaParser.NewIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doIF}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoIF(FormulaParser.DoIFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doIF}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoIF(FormulaParser.DoIFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doPredicate}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoPredicate(FormulaParser.DoPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doPredicate}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoPredicate(FormulaParser.DoPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doExpandOr}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoExpandOr(FormulaParser.DoExpandOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doExpandOr}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoExpandOr(FormulaParser.DoExpandOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newFalseBoolean}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewFalseBoolean(FormulaParser.NewFalseBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newFalseBoolean}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewFalseBoolean(FormulaParser.NewFalseBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doIFF}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoIFF(FormulaParser.DoIFFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doIFF}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoIFF(FormulaParser.DoIFFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newVariable}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewVariable(FormulaParser.NewVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newVariable}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewVariable(FormulaParser.NewVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doParenthesis}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoParenthesis(FormulaParser.DoParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doParenthesis}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoParenthesis(FormulaParser.DoParenthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newTrueBoolean}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewTrueBoolean(FormulaParser.NewTrueBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newTrueBoolean}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewTrueBoolean(FormulaParser.NewTrueBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doAnd}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoAnd(FormulaParser.DoAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doAnd}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoAnd(FormulaParser.DoAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doExpandAnd}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoExpandAnd(FormulaParser.DoExpandAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doExpandAnd}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoExpandAnd(FormulaParser.DoExpandAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doITE}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoITE(FormulaParser.DoITEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doITE}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoITE(FormulaParser.DoITEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doNot}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoNot(FormulaParser.DoNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doNot}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoNot(FormulaParser.DoNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newTermID}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 */
	void enterNewTermID(FormulaParser.NewTermIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newTermID}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 */
	void exitNewTermID(FormulaParser.NewTermIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newTermVar}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 */
	void enterNewTermVar(FormulaParser.NewTermVarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newTermVar}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 */
	void exitNewTermVar(FormulaParser.NewTermVarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doIntExpr}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 */
	void enterDoIntExpr(FormulaParser.DoIntExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doIntExpr}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 */
	void exitDoIntExpr(FormulaParser.DoIntExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpression(FormulaParser.UnaryMinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpression(FormulaParser.UnaryMinusExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newIntVariable}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void enterNewIntVariable(FormulaParser.NewIntVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newIntVariable}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void exitNewIntVariable(FormulaParser.NewIntVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesizedIntExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedIntExpression(FormulaParser.ParenthesizedIntExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesizedIntExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedIntExpression(FormulaParser.ParenthesizedIntExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code absValueExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void enterAbsValueExpression(FormulaParser.AbsValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code absValueExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void exitAbsValueExpression(FormulaParser.AbsValueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(FormulaParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(FormulaParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newInteger}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void enterNewInteger(FormulaParser.NewIntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newInteger}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void exitNewInteger(FormulaParser.NewIntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(FormulaParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(FormulaParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaParser#range_expr}.
	 * @param ctx the parse tree
	 */
	void enterRange_expr(FormulaParser.Range_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaParser#range_expr}.
	 * @param ctx the parse tree
	 */
	void exitRange_expr(FormulaParser.Range_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaParser#set_expr}.
	 * @param ctx the parse tree
	 */
	void enterSet_expr(FormulaParser.Set_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaParser#set_expr}.
	 * @param ctx the parse tree
	 */
	void exitSet_expr(FormulaParser.Set_exprContext ctx);
}