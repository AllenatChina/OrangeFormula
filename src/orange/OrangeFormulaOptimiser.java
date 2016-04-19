package orange;

import java.util.*;

/**
 * Created by yin on 13/04/16.
 */
public class OrangeFormulaOptimiser {

    public static OrangeFormula optimise(OrangeFormula formula) {
        //TODO: optimise the formula, and return it

        subsumption(formula);

        unitPropagation(formula);

        return formula;
    }

    public static OrangeFormula subsumption(OrangeFormula formula) {

        List<OrangeClause> clauses = formula.getClauseList();

        Collections.sort(clauses, new Comparator<OrangeClause>() {
            @Override
            public int compare(OrangeClause o1, OrangeClause o2) {
                if (o1.containsClause(o2)) {
                    return 1;
                }
                if (o2.containsClause(o1)) {
                    return -1;
                }
                return o1.getLiterals().size() >= o2.getLiterals().size() ? 1 : -1;
            }
        });

        StringBuffer buffer = new StringBuffer("Subsumption removed: ");
        boolean ifRemovedSth = false;

        for (int i = 0; i < clauses.size() - 1; i++) {
            for (int j = i + 1; j < clauses.size(); j++) {
                OrangeClause smaller = clauses.get(i);
                OrangeClause bigger = clauses.get(j);
                if (bigger.containsClause(smaller)) {
                    formula.removeClause(bigger);
                    ifRemovedSth = true;
                    buffer.append(bigger.toString() + " ");
                }
            }
        }

        if (ifRemovedSth) {
            System.out.println(buffer.toString());
        }

        return formula;
    }

    public static OrangeFormula unitPropagation(OrangeFormula formula) {

        Set<String> units = new HashSet<String>();
        Set<OrangeClause> clauses = formula.getClauses();
        for (OrangeClause clause : clauses) {
            if (clause.isUnitClause()) {
                units.add(clause.getLiteralList().get(0));
            }
        }

        for (String unit : units) {
            String negatedUnit = getNegatedLiteral(unit);
            if (units.contains(negatedUnit)) {
                System.out.println("Found clash during unit propagation: " + unit + " & " + negatedUnit);
                return formula;
            }
        }

        for (String unit : units) {
            String negatedUnit = getNegatedLiteral(unit);
            Iterator<OrangeClause> iterator = clauses.iterator();
            while (iterator.hasNext()) {
                OrangeClause clause = iterator.next();
                if (clause.containsLiteral(unit)) {
                    iterator.remove();
                } else if (clause.containsLiteral(negatedUnit)) {
                    clause.removeLiteral(negatedUnit);
                }
            }
        }

        System.out.println("Unit propagation: " + units.toString().replace("[", "").replace("]", ""));
        return formula;
    }

    private static void pureLiterals(OrangeFormula formula) {

    }

    private static String getNegatedLiteral(String literal) {
        String negatedUnit;
        if (literal.startsWith("~")) {
            negatedUnit = literal.replace("~", "");
        } else {
            negatedUnit = "~" + literal;
        }
        return negatedUnit;
    }

}
