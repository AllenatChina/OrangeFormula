package orange;

import formula.FormulaBaseVisitor;
import formula.FormulaParser;

import java.util.*;

/**
 * Created by yin on 12/04/16.
 */
public class OrangeFormulaVisitor extends FormulaBaseVisitor<OrangeFormula> {

    public OrangeFormula rootFormula;

    private Map<String, String> boundMap;

    public OrangeFormulaVisitor() {
        rootFormula = new OrangeFormula();
    }

    @Override
    public OrangeFormula visitFormula(FormulaParser.FormulaContext ctx) {

        List<OrangeFormula> exprs = new ArrayList<OrangeFormula>();
        List<FormulaParser.ExpressionContext> contexts = ctx.expression();
        for (int i = 0; i < contexts.size(); i++) {
            exprs.add(visit(contexts.get(i)));
        }

        return OrangeFormulaConverter.and(exprs);
    }

    public OrangeFormula visitDoAnd(FormulaParser.DoAndContext ctx) {

        return OrangeFormulaConverter.and(visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public OrangeFormula visitDoThen(FormulaParser.DoThenContext ctx) {

        return OrangeFormulaConverter.then(visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public OrangeFormula visitDoOr(FormulaParser.DoOrContext ctx) {

        return OrangeFormulaConverter.or(visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public OrangeFormula visitDoXor(FormulaParser.DoXorContext ctx) {
        OrangeFormula p = visit(ctx.expression(0));
        OrangeFormula q = visit(ctx.expression(1));

        return OrangeFormulaConverter.xor(p, q);
    }

    @Override
    public OrangeFormula visitNewIdentifier(FormulaParser.NewIdentifierContext ctx) {
        return new OrangeFormula(ctx.ID().getText());
    }

    @Override
    public OrangeFormula visitDoIF(FormulaParser.DoIFContext ctx) {
        return OrangeFormulaConverter.then(visit(ctx.expression(1)), visit(ctx.expression(0)));
    }

    @Override
    public OrangeFormula visitDoIFF(FormulaParser.DoIFFContext ctx) {
        OrangeFormula a = visit(ctx.expression(0));
        OrangeFormula b = visit(ctx.expression(1));
        return OrangeFormulaConverter.iff(a, b);
    }

    @Override
    public OrangeFormula visitNewVariable(FormulaParser.NewVariableContext ctx) {
        String var = ctx.VAR().getText();
        if (boundMap == null || !boundMap.containsKey(var)) {
            return new OrangeFormula(ctx.VAR().getText());
        } else {
            return new OrangeFormula(boundMap.get(var));
        }
    }

    @Override
    public OrangeFormula visitDoParenthesis(FormulaParser.DoParenthesisContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public OrangeFormula visitDoITE(FormulaParser.DoITEContext ctx) {
        OrangeFormula p = visit(ctx.expression(0));
        OrangeFormula q = visit(ctx.expression(1));
        OrangeFormula r = visit(ctx.expression(2));

        return OrangeFormulaConverter.ite(p, q, r);
    }

    @Override
    public OrangeFormula visitDoNot(FormulaParser.DoNotContext ctx) {

        if (ctx.expression() instanceof FormulaParser.DoNotContext) {
            return visit(((FormulaParser.DoNotContext) ctx.expression()).expression());
        } else {
            return OrangeFormulaConverter.not(visit(ctx.expression()));
        }

    }

    @Override
    public OrangeFormula visitDoPredicate(FormulaParser.DoPredicateContext ctx) {

        return evalPredicate(ctx);
    }

    @Override
    public OrangeFormula visitNewFalseBoolean(FormulaParser.NewFalseBooleanContext ctx) {
        return new OrangeFormula("False");
    }

    @Override
    public OrangeFormula visitNewTrueBoolean(FormulaParser.NewTrueBooleanContext ctx) {

        return new OrangeFormula("True");
    }

    @Override
    public OrangeFormula visitDoExpandOr(FormulaParser.DoExpandOrContext ctx) {
        FormulaParser.Range_exprContext range_ctx = ctx.range_expr();
        FormulaParser.Set_exprContext set_ctx = ctx.set_expr();
        String var = ctx.VAR().getText();

        return evalExpandExpr(1, var, range_ctx, set_ctx, ctx.expression());
    }

    @Override
    public OrangeFormula visitDoExpandAnd(FormulaParser.DoExpandAndContext ctx) {

        FormulaParser.Range_exprContext range_ctx = ctx.range_expr();
        FormulaParser.Set_exprContext set_ctx = ctx.set_expr();
        String var = ctx.VAR().getText();

        return evalExpandExpr(0, var, range_ctx, set_ctx, ctx.expression());
    }

    private OrangeFormula evalPredicate(FormulaParser.DoPredicateContext ctx) {
        StringBuffer predicate = new StringBuffer(ctx.ID().getText() + "(" + evalTerm(ctx.term(0)));
        for (int i = 1; i < ctx.term().size(); i++) {
            predicate.append(",");
            predicate.append(evalTerm(ctx.term(i)));
        }
        predicate.append(")");
        return new OrangeFormula(predicate.toString());
    }

    private String evalTerm(FormulaParser.TermContext ctx) {
        if (boundMap == null) {
            boundMap = new HashMap<String, String>();
        }
        if (ctx instanceof FormulaParser.NewTermIDContext) {
            return ((FormulaParser.NewTermIDContext) ctx).ID().getText();
        } else if (ctx instanceof FormulaParser.NewTermVarContext) {
            String var = ((FormulaParser.NewTermVarContext) ctx).VAR().getText();
            if (boundMap.containsKey(var)) {
                return boundMap.get(var);
            } else {
                return "False";
            }
        } else if (ctx instanceof FormulaParser.DoIntExprContext) {
            return evalIntExpr(((FormulaParser.DoIntExprContext) ctx).int_expr()).toString();
        } else {
            return "ORANGE BAD!";
        }
    }

    private Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println("Oops, Did you try to calculate two symbols together? That's stupid. XD");
            //TODO
//            e.printStackTrace();
            return null;
        }
    }

    private String evalIntExpr(FormulaParser.Int_exprContext ctx) {
        if (ctx instanceof FormulaParser.ParenthesizedIntExpressionContext) {
            return parseInt(evalIntExpr(((FormulaParser.ParenthesizedIntExpressionContext) ctx).int_expr())).toString();
        } else if (ctx instanceof FormulaParser.AbsValueExpressionContext) {
            return Math.abs(parseInt(evalIntExpr(((FormulaParser.AbsValueExpressionContext) ctx).int_expr()))) + "";
        } else if (ctx instanceof FormulaParser.UnaryMinusExpressionContext) {
            return parseInt("-" + evalIntExpr(((FormulaParser.UnaryMinusExpressionContext) ctx).int_expr())).toString();
        } else if (ctx instanceof FormulaParser.MultiplicativeExpressionContext) {
            char op = ((FormulaParser.MultiplicativeExpressionContext) ctx).op.getText().charAt(0);
            int a = parseInt(evalIntExpr(((FormulaParser.MultiplicativeExpressionContext) ctx).int_expr(0)));
            int b = parseInt(evalIntExpr(((FormulaParser.MultiplicativeExpressionContext) ctx).int_expr(1)));
            switch (op) {
                case '*':
                    return String.valueOf(a * b);
                case '/':
                    return String.valueOf(a / b);
                case '%':
                    return String.valueOf(a % b);
                default:
                    break;
            }
        } else if (ctx instanceof FormulaParser.AdditiveExpressionContext) {
            char op = ((FormulaParser.AdditiveExpressionContext) ctx).op.getText().charAt(0);
            int a = parseInt(evalIntExpr(((FormulaParser.AdditiveExpressionContext) ctx).int_expr(0)));
            int b = parseInt(evalIntExpr(((FormulaParser.AdditiveExpressionContext) ctx).int_expr(1)));
            switch (op) {
                case '+':
                    return String.valueOf(a + b);
                case '-':
                    return String.valueOf(a - b);
                default:
                    break;
            }
        } else if (ctx instanceof FormulaParser.NewIntVariableContext) {

            String var = ((FormulaParser.NewIntVariableContext) ctx).VAR().getText();
            if (boundMap.containsKey(var)) {
                return boundMap.get(var);
            } else {
                return "0";
            }

        } else if (ctx instanceof FormulaParser.NewIntegerContext) {
            return parseInt(((FormulaParser.NewIntegerContext) ctx).NUMBER().getText()).toString();
        } else {

        }

        return null;
    }

    private OrangeFormula evalExpandExpr(int type, String var, FormulaParser.Range_exprContext range_ctx, FormulaParser.Set_exprContext
            set_ctx, FormulaParser.ExpressionContext expression) {

        if (boundMap == null) {
            boundMap = new HashMap<String, String>();
        }

        Set<String> currentBound;
        //Decide it's range or set bound
        if (range_ctx != null) {
            currentBound = evalRange_expr(range_ctx);
        } else {
            currentBound = evalSet_expr(set_ctx);
        }


        List<OrangeFormula> result = new ArrayList<OrangeFormula>();

        for (String s : currentBound) {
            boundMap.put(var, s);
            OrangeFormula formula = evalQuantifierExpr(expression);
            result.add(formula.groundVar(var, s));
        }
        if (result.size() == 0) {
            result.add(evalQuantifierExpr(expression));
        }

        boundMap.remove(var);
        if (type == 0) {
            return OrangeFormulaConverter.and(result);
        } else {
            return OrangeFormulaConverter.or(result);
        }

    }

    private OrangeFormula evalQuantifierExpr(FormulaParser.ExpressionContext ctx) {

        if (ctx instanceof FormulaParser.DoExpandAndContext) {
            FormulaParser.Range_exprContext range_ctx = ((FormulaParser.DoExpandAndContext) ctx).range_expr();
            FormulaParser.Set_exprContext set_ctx = ((FormulaParser.DoExpandAndContext) ctx).set_expr();
            String var = ((FormulaParser.DoExpandAndContext) ctx).VAR().getText();

            return evalExpandExpr(0, var, range_ctx, set_ctx, ((FormulaParser.DoExpandAndContext) ctx).expression());

        } else if (ctx instanceof FormulaParser.DoExpandOrContext) {
            FormulaParser.Range_exprContext range_ctx = ((FormulaParser.DoExpandOrContext) ctx).range_expr();
            FormulaParser.Set_exprContext set_ctx = ((FormulaParser.DoExpandOrContext) ctx).set_expr();
            String var = ((FormulaParser.DoExpandOrContext) ctx).VAR().getText();

            return evalExpandExpr(1, var, range_ctx, set_ctx, ((FormulaParser.DoExpandOrContext) ctx).expression());
        } else if (ctx instanceof FormulaParser.DoPredicateContext) {
            return evalPredicate(((FormulaParser.DoPredicateContext) ctx));
        } else {
            return visit(ctx);
        }

    }

    private Set<String> evalSet_expr(FormulaParser.Set_exprContext ctx) {

        Set<String> result = new HashSet<String>();

        for (FormulaParser.TermContext context : ctx.term()) {
            result.add(evalTerm(context));
        }

        return result;
    }

    private Set<String> evalRange_expr(FormulaParser.Range_exprContext ctx) {

        Set<String> result = new HashSet<String>();

        int from = parseInt(evalIntExpr(ctx.int_expr(0)));

        int to = parseInt(evalIntExpr(ctx.int_expr(1)));

        if (to >= from) {
            for (int i = from; i <= to; i++) {
                result.add(String.valueOf(i));
            }
        }

        return result;
    }

}
