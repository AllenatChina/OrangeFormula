package orange;

import java.util.*;

/**
 * Created by yin on 13/04/16.
 */
public class OrangeFormulaOptimiser {

    public static OrangeFormula optimise(OrangeFormula formula) {


        while (subsumption(formula)
                || unitPropagation(formula)
                || pureLiterals(formula)) {
        }

        return formula;
    }

    public static boolean subsumption(OrangeFormula formula) {

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
                return o1.getLiterals().size() - o2.getLiterals().size();
            }
        });

        StringBuffer buffer = new StringBuffer("Subsumption removed: ");
        boolean ifRemovedSth = false;

        long n = 0;
        for (int i = 0; i < clauses.size() - 1; i++) {
            for (int j = i + 1; j < clauses.size(); j++) {
                OrangeClause smaller = clauses.get(i);
                OrangeClause bigger = clauses.get(j);
                if (bigger.containsClause(smaller)) {
                    formula.removeClause(bigger);
                    n++;
                    ifRemovedSth = true;
                    buffer.append(bigger.toString() + " ");
                }
            }
        }

        if (ifRemovedSth) {
            System.out.println(buffer.toString() + " #total: " + n);
            return true;
        } else {
            return false;
        }

    }

    public static boolean unitPropagation(OrangeFormula formula) {

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
                return false;
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
        if (!units.isEmpty()) {
            System.out.println("Unit propagation: " + units.toString().replace("[", "").replace("]", "")
                    + " #total: " + units.size());
            return true;
        } else {
            return false;
        }
    }

    public static boolean pureLiterals(OrangeFormula formula) {

        boolean ifRemovedSth = false;
        Set<String> allLiterals = new HashSet<String>();
        for (OrangeClause clause : formula.getClauses()) {
            allLiterals.addAll(clause.getLiterals());
        }
        Set<String> pureLiterals = new HashSet<String>();
        for (String literal : allLiterals) {
            String negated = getNegatedLiteral(literal);
            if (pureLiterals.contains(negated)) {

            }else if (!allLiterals.contains(negated)) {
                pureLiterals.add(literal);
            }
        }

        Iterator<OrangeClause> iterator = formula.getClauses().iterator();
        while (iterator.hasNext()) {
            OrangeClause clause = iterator.next();
            for (String pureLiteral : pureLiterals) {
                if (clause.containsLiteral(pureLiteral)) {
                    iterator.remove();
                    ifRemovedSth = true;
                    break;
                }
            }
        }

        if (ifRemovedSth) {
            System.out.println("Pure literals: " + pureLiterals.toString().replace("[", "").replace("]", "")
                    + " #total: " + pureLiterals.size());
            return true;
        } else {
            return false;
        }

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
