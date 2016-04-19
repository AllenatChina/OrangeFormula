package orange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 11/04/16.
 */
public class OrangeFormulaConverter {

    public static OrangeFormula and(OrangeFormula a, OrangeFormula b) {
        OrangeFormula result = new OrangeFormula();

        result.addClauses(a.getClauseList());
        result.addClauses(b.getClauseList());

        return result;
    }

    public static OrangeFormula and(List<OrangeFormula> formulas) {

        OrangeFormula result = new OrangeFormula();
        for (OrangeFormula formula : formulas) {
            result.addClauses(formula.getClauseList());
        }
        return result;

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
        List<OrangeClause> clauses1 = a.getClauseList();
        List<OrangeClause> clauses2 = b.getClauseList();
        for (int i = 0; i < clauses1.size(); i++) {
            for (int j = 0; j < clauses2.size(); j++) {
                OrangeClause clause = new OrangeClause();
                clause.addLiterals(clauses1.get(i).getLiteralList());
                clause.addLiterals(clauses2.get(j).getLiteralList());
                result.addClause(clause);
            }
        }
//        if (a.isSingleClause() && b.isSingleClause()) {
//            result.addLiterals(a.getLiterals());
//            result.addLiterals(b.getLiterals());
//        } else {
//        }

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

        List<OrangeFormula> formulas = new ArrayList<OrangeFormula>();
        List<OrangeClause> clauses = formula.getClauseList();
        for (int i = 0; i < clauses.size(); i++) {
            formulas.add(not(clauses.get(i)).toFormula());
        }

        return or(formulas);

    }

    private static OrangeClause not(OrangeClause clause) {
        List<String> literals = clause.getLiteralList();
        OrangeClause result = new OrangeClause();
        for (String literal : literals) {
            if (literal.equals("True")) {
                result.addLiteral("False");
            } else if (literal.equals("False")) {
                result.addLiteral("True");
            }else if (literal.startsWith("~")) {
                result.addLiteral(literal.replace("~", ""));
            } else {
                result.addLiteral("~" + literal);
            }
        }
        return result;
    }

}
