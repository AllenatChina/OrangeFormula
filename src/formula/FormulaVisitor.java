package formula;// Generated from Formula.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FormulaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FormulaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FormulaParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(FormulaParser.FormulaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doThen}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoThen(FormulaParser.DoThenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doOr}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoOr(FormulaParser.DoOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doXor}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoXor(FormulaParser.DoXorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newIdentifier}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewIdentifier(FormulaParser.NewIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doIF}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoIF(FormulaParser.DoIFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doPredicate}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoPredicate(FormulaParser.DoPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doExpandOr}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoExpandOr(FormulaParser.DoExpandOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newFalseBoolean}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewFalseBoolean(FormulaParser.NewFalseBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doIFF}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoIFF(FormulaParser.DoIFFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newVariable}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewVariable(FormulaParser.NewVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doParenthesis}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoParenthesis(FormulaParser.DoParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newTrueBoolean}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewTrueBoolean(FormulaParser.NewTrueBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doAnd}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoAnd(FormulaParser.DoAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doExpandAnd}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoExpandAnd(FormulaParser.DoExpandAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doITE}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoITE(FormulaParser.DoITEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doNot}
	 * labeled alternative in {@link FormulaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoNot(FormulaParser.DoNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newTermID}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewTermID(FormulaParser.NewTermIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newTermVar}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewTermVar(FormulaParser.NewTermVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doIntExpr}
	 * labeled alternative in {@link FormulaParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoIntExpr(FormulaParser.DoIntExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinusExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpression(FormulaParser.UnaryMinusExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newIntVariable}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewIntVariable(FormulaParser.NewIntVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesizedIntExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedIntExpression(FormulaParser.ParenthesizedIntExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code absValueExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbsValueExpression(FormulaParser.AbsValueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(FormulaParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newInteger}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewInteger(FormulaParser.NewIntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link FormulaParser#int_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(FormulaParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FormulaParser#range_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange_expr(FormulaParser.Range_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FormulaParser#set_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet_expr(FormulaParser.Set_exprContext ctx);
}