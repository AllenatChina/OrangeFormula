package orange;

import java.util.*;

/**
 * Created by yin on 12/04/16.
 */
public class OrangeFormula {

    /**
     * If this formula is a multiple clause, this is where the clauses will be stored
     * Otherwise this is an empty list
     */
    private Set<OrangeFormula> clauses;

    /**
     * If this formula is a single clause, this is where the literals will be stored
     * Otherwise this is an empty list
     */
    private Set<String> literals;

    public OrangeFormula() {
        clauses = new HashSet<OrangeFormula>();
        literals = new HashSet<String>();
    }

    /**
     * Constructor made only for creating single clause formula, the parameters are the list of literals
     * @param literals
     */
    public OrangeFormula(String... literals) {
        this();
        OrangeFormula inside = new OrangeFormula();
        inside.addLiterals(new LinkedList<String>(Arrays.asList(literals)));
        clauses.add(inside);
    }

    /**
     * check if this formula is a single clause
     * @return
     */
    public boolean isSingleClause() {
        return clauses.size() == 0;
    }

    public boolean containsEmptyClause() {
        if (isSingleClause()) {
            return false;
        } else {
            for (OrangeFormula clause : clauses) {
                if (clause.isEmptyClause()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEmptyFormula() {
        return clauses.size() == 0 && literals.size() == 0;
    }

    public boolean isEmptyClause() {
        if (isSingleClause())
            return literals.isEmpty();
        else
            return false;
    }

    public boolean isUnitClause() {
        if (isSingleClause()) {
            return literals.size() == 1;
        } else {
            return false;
        }
    }

    public void addLiteral(String added) {
        if (added.contains("False")) {
            added = "False";
        }
        literals.add(added);
    }

    public void addLiterals(List<String> added) {
        ListIterator<String> iterator = added.listIterator();
        while (iterator.hasNext()) {
            String cur = iterator.next();
            if (cur.contains("False")) {
                iterator.remove();
            }
        }
        literals.addAll(added);
    }

    public void addClauses(List<OrangeFormula> added) {
        clauses.addAll(added);
    }

    public void addClause(OrangeFormula added) {
        clauses.add(added);
    }

    public List<String> getLiterals() {
        return new ArrayList<String>(literals);
    }

    public Set<String> getLiteralsSet() {
        return literals;
    }

    public List<OrangeFormula> getClauses() {
        if (isSingleClause()) {
            return Collections.singletonList(this);
        } else {
            return new ArrayList<OrangeFormula>(clauses);
        }
    }

    public List<List<String>> getStringClauses() {
        List<OrangeFormula> clauses = getClauses();
        List<List<String>> stringClauses = new ArrayList<List<String>>();

        for (int i = 0; i < clauses.size(); i++) {
            stringClauses.add(clauses.get(i).getLiterals());
        }

        return stringClauses;
    }

    public OrangeFormula groundVar(String var, String val) {
        if (isSingleClause()) {
            for (String literal : literals) {
                literal = literal.replace(var, val);
            }
        } else {
            for (OrangeFormula clause : clauses) {
                clause.groundVar(var, val);
            }
        }

        return this;
    }

    public Map<String, Integer> getMapToInt() {
        Map<String, Integer> mapping = new HashMap<String, Integer>();

        int n = 1;
        List<OrangeFormula> clauses = getClauses();
        for (OrangeFormula clause : clauses) {
            List<String> literals = clause.getLiterals();
            for (int i = 0; i < literals.size(); i++) {
                String literal = literals.get(i);
                if (literal.startsWith("~")) {
                    if (!mapping.containsKey(literal.replace("~", ""))) {
                        mapping.put(literal.replace("~", ""), n);
                        n++;
                    }
                } else {
                    if (!mapping.containsKey(literal)) {
                        mapping.put(literal, n++);
                    }
                }
            }
        }

        return mapping;
    }

    public List<int[]> getIntClauses() {

        List<int[]> intClauses = new ArrayList<int[]>();

        Map<String, Integer> mapping = getMapToInt();

        for (OrangeFormula clause : getClauses()) {
            List<String> literals = clause.getLiterals();
            int[] temp = new int[literals.size()];
            for (int i = 0; i < literals.size(); i++) {
                String literal = literals.get(i);
                if (literal.startsWith("~")) {
                    temp[i] = mapping.get(literal.replace("~", "")) * -1;
                } else {
                    temp[i] = mapping.get(literal);
                }
            }
            intClauses.add(temp);
        }

        return intClauses;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        if (isSingleClause()) {
            List<String> literals = getLiterals();
            buffer.append("[");
            for (int i = 0; i < literals.size(); i++) {
                buffer.append(literals.get(i) + (i == literals.size() - 1 ? "" : ", "));
            }
            buffer.append("]\n");
        } else {
            List<OrangeFormula> clauses = getClauses();
            buffer.append("< ");
            for (int i = 0; i < clauses.size(); i++) {
                buffer.append(clauses.get(i).toString() + (i == clauses.size() - 1 ? "" : ", "));
            }
            buffer.append(" >");
        }

        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrangeFormula)) {
            return false;
        }

        OrangeFormula formula = (OrangeFormula) obj;

        if (formula.isSingleClause()) {
            if (isSingleClause()) {
                return getLiteralsSet().equals(formula.getLiteralsSet());
            } else {
                return false;
            }
        } else {
            if (isSingleClause()) {
                return false;
            } else {
                Set<OrangeFormula> a = new HashSet<OrangeFormula>(formula.getClauses());
                return a.equals(clauses);
            }
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
