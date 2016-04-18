package orange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 11/04/16.
 */
public class OrangeFormulaConverter {

    public static OrangeFormula and(OrangeFormula a, OrangeFormula b) {
        OrangeFormula result = new OrangeFormula();

        result.addClauses(a.getClauses());
        result.addClauses(b.getClauses());

        return result;
    }

    public static OrangeFormula and(List<OrangeFormula> formulas) {

        if (formulas.size() == 1) {
            return formulas.get(0);
        } else {
            OrangeFormula result = new OrangeFormula();
            for (OrangeFormula formula : formulas) {
                result.addClauses(formula.getClauses());
            }
            return result;
        }

    }

    public static OrangeFormula or(List<OrangeFormula> formulas) {

        int n = formulas.size();
        if (n < 2) {
            return formulas.get(0);
        }else{
            // remove(n-1), remove(n-2) is better than remove(0), remove(0), but it will cause reverse expressions
            OrangeFormula next = or(formulas.remove(n-1), formulas.remove(n-2));
            formulas.add(next);
            return or(formulas);
        }

    }

    public static OrangeFormula or(OrangeFormula a, OrangeFormula b) {
        OrangeFormula result = new OrangeFormula();
        if (a.isSingleClause() && b.isSingleClause()) {
            result.addLiterals(a.getLiterals());
            result.addLiterals(b.getLiterals());
        } else {
            List<OrangeFormula> clauses1 = a.getClauses();
            List<OrangeFormula> clauses2 = b.getClauses();
            for (int i = 0; i < clauses1.size(); i++) {
                for (int j = 0; j < clauses2.size(); j++) {
                    OrangeFormula formula = new OrangeFormula();
                    formula.addLiterals(clauses1.get(i).getLiterals());
                    formula.addLiterals(clauses2.get(j).getLiterals());
                    result.addClause(formula);
                }
            }
        }

        return result;
    }

    public static OrangeFormula then(OrangeFormula a, OrangeFormula b) {

        return or(not(a), b);
    }

    public static OrangeFormula iff(OrangeFormula a, OrangeFormula b) {
        return and(then(a, b), then(b, a));
    }

    public static OrangeFormula xor(OrangeFormula a, OrangeFormula b) {
        return and(or(a, b), not(and(a, b)));
    }

    public static OrangeFormula ite(OrangeFormula p, OrangeFormula q, OrangeFormula r) {
        return and(then(p, q), then(not(p), r));
    }

    public static OrangeFormula not(OrangeFormula formula) {

        OrangeFormula result = new OrangeFormula();

        if (formula.isSingleClause()) {
            List<String> literals = formula.getLiterals();
            if (literals.size() == 1) {

                String literal = literals.get(0);
                if (literal.startsWith("~")) {
                    result = new OrangeFormula(literal.replace("~", ""));
                } else {
                    result = new OrangeFormula("~" + literal);
                }

            } else {
                for (String literal : literals) {
                    if (literal.equals("True")) {
                        result.addClause(new OrangeFormula("False"));
                    } else if (literal.equals("False")) {
                        result.addClause(new OrangeFormula("True"));
                    }else if (literal.startsWith("~")) {
                        result.addClause(new OrangeFormula(literal.replace("~", "")));
                    } else {
                        result.addClause(new OrangeFormula("~" + literal));
                    }
                }
            }

        } else {

            List<OrangeFormula> formulas = new ArrayList<OrangeFormula>();
            for (int i = 0; i < formula.getClauses().size(); i++) {
                formulas.add(not(formula.getClauses().get(i)));
            }
            result = or(formulas);

        }

        return result;

    }

}
