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
    private Set<OrangeClause> clauses;

    /**
     * If this formula is a single clause, this is where the literals will be stored
     * Otherwise this is an empty list
     */
//    private Set<String> literals;

    public OrangeFormula() {
        clauses = new HashSet<OrangeClause>();
    }

    /**
     * Constructor made only for creating single clause formula, the parameters are the list of literals
     * @param literals
     */
    public OrangeFormula(String... literals) {
        this();
        addClause(new OrangeClause(literals));
    }


    public boolean containsEmptyClause() {
        for (OrangeClause clause : clauses) {
            if (clause.isEmptyClause()) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyFormula() {
        return clauses.isEmpty();
    }

    public boolean addClauses(List<OrangeClause> added) {
        ListIterator<OrangeClause> iterator = added.listIterator();
        while (iterator.hasNext()) {
            OrangeClause cur = iterator.next();
            if (cur.containsLiteral("True")) {
                iterator.remove();
            }
        }
        return clauses.addAll(added);
    }

    public boolean addClause(OrangeClause added) {
        if (added.containsLiteral("True")) {
            return false;
        }
        return clauses.add(added);
    }

    public boolean removeClause(OrangeClause removed) {
        return clauses.remove(removed);
    }

    public Set<OrangeClause> getClauses() {
        return clauses;
    }

    public List<OrangeClause> getClauseList() {
        return new LinkedList<OrangeClause>(clauses);
    }

    public OrangeFormula groundVar(String var, String val) {
        for (OrangeClause clause : clauses) {
            clause.groundVar(var, val);
        }

        return this;
    }

    public Map<String, Integer> getMapToInt() {
        Map<String, Integer> mapping = new HashMap<String, Integer>();

        int n = 1;
        List<OrangeClause> clauses = getClauseList();
        for (OrangeClause clause : clauses) {
            List<String> literals = clause.getLiteralList();
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

        for (OrangeClause clause : getClauses()) {
            List<String> literals = clause.getLiteralList();
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

        List<OrangeClause> clauses = getClauseList();
        buffer.append("< ");
        for (int i = 0; i < clauses.size(); i++) {
            buffer.append(clauses.get(i).toString() + (i == clauses.size() - 1 ? "" : ", "));
        }
        buffer.append(" >");
        buffer.append(" \n$ " + clauses.size() + " clauses in total");

        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrangeFormula)) {
            return false;
        }

        OrangeFormula formula = (OrangeFormula) obj;

        return clauses.equals(((OrangeFormula) obj).getClauses());
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
