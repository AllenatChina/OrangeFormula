package orange;

import java.util.*;

/**
 * Created by yin on 18/04/16.
 */
public class OrangeClause {

    private Set<String> literals;

    public OrangeClause() {
        literals = new HashSet<String>();
    }

    public OrangeClause(Set<String> literals) {
        this.literals = literals;
    }

    public OrangeClause(String... literals) {
        this();
        addLiterals(new LinkedList<String>(Arrays.asList(literals)));
    }

    public boolean addLiteral(String added) {
        if (added.contains("False")) {
            added = "False";
        }
        return literals.add(added);
    }

    public boolean addLiterals(List<String> added) {
        ListIterator<String> iterator = added.listIterator();
        while (iterator.hasNext()) {
            String cur = iterator.next();
            if (cur.contains("False")) {
                iterator.remove();
            }
        }
        return literals.addAll(added);
    }

    public boolean removeLiteral(String removed) {
        return literals.remove(removed);
    }

    public boolean containsLiteral(String literal) {
        return literals.contains(literal);
    }

    public boolean containsClause(OrangeClause clause) {
        return literals.containsAll(clause.getLiterals());
    }

    public Set<String> getLiterals() {
        return literals;
    }

    public List<String> getLiteralList() {
        return new LinkedList<String>(literals);
    }

    public boolean isUnitClause() {
        return literals.size() == 1;
    }

    public boolean isEmptyClause() {
        return literals.isEmpty();
    }

    public void groundVar(String var, String val) {
        for (String literal : literals) {
            literal = literal.replace(var, val);
        }
    }

    public OrangeFormula toFormula() {
        OrangeFormula formula = new OrangeFormula();
        formula.addClause(new OrangeClause(new HashSet<String>(literals)));
        return formula;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrangeClause)) {
            return false;
        }

        return literals.equals(((OrangeClause) obj).getLiterals());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        List<String> literals = getLiteralList();
        buffer.append("[");
        for (int i = 0; i < literals.size(); i++) {
            buffer.append(literals.get(i) + (i == literals.size() - 1 ? "" : ", "));
        }
        buffer.append("]\n");

        return getLiterals().toString();
    }
}
