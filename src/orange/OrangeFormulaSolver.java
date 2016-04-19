package orange;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.LecteurDimacs;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yin on 13/04/16.
 */
public class OrangeFormulaSolver {

    private OrangeFormula formula;

    private Map<String, Integer> varMappings;
    private Map<String, Integer> solMappings;

    public OrangeFormulaSolver(OrangeFormula formula) {
        this.formula = formula;
        varMappings = formula.getMapToInt();
        solMappings = new HashMap<String, Integer>();
    }

    public boolean solve() {

        System.out.println("By mapping variables to integers,");
        System.out.println(formula.getMapToInt().toString());
        System.out.println("we get DIMACS Clauses:\n");
        for (int[] clauses : formula.getIntClauses()) {
            System.out.print("[");
            for (int i = 0; i < clauses.length; i++) {
                System.out.print(clauses[i] + (i == clauses.length - 1 ? "" : " "));
            }
            System.out.println("]");
        }
        System.out.println();

        try {

            writeDIMACS();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            return solveDIMACS();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseFormatException e) {
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("$ The cnf is unsatisfiable.");
//            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("$ The solver is unable to solve the cnf in two minutes. TIMEOUT!");
        }

        return false;
    }

    private void writeDIMACS() throws IOException {

        FileWriter fileWriter = new FileWriter("dimacs.cnf");

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        List<int[]> clauses = formula.getIntClauses();

        bufferedWriter.write("p cnf " + varMappings.keySet().size() + " " + clauses.size());
        bufferedWriter.newLine();

        for (int i = 0; i < clauses.size(); i++) {
            int[] current = clauses.get(i);
            for (int j = 0; j < current.length; j++) {
                bufferedWriter.write(current[j] + " ");
            }
            bufferedWriter.write(0 + "");
            if (i != clauses.size() - 1) {
                bufferedWriter.newLine();
            }
        }

        bufferedWriter.close();

    }

    private boolean solveDIMACS() throws IOException, ParseFormatException, ContradictionException, TimeoutException {

        LecteurDimacs reader = new LecteurDimacs(SolverFactory.newDefault());
        ISolver solver = (ISolver) reader.parseInstance("dimacs.cnf");
        ModelIterator mi = new ModelIterator(solver);
        mi.setTimeout(120);

        if (mi.isSatisfiable()) {
            System.out.println("$ The cnf is satisfiable:\n");

            int[] sol = mi.model();

            printSolutions(sol);

//            PrintWriter writer = new PrintWriter(System.out);
//            solver.printStat(writer);
//            writer.close();
            printOtherSolutions(mi);

            return true;

        } else {
            System.out.println("$ The cnf is unsatisfiable.");
            return false;
        }
    }

    private void printSolutions(int[] sol) {

        for (String s : varMappings.keySet()) {
            solMappings.put(s, sol[varMappings.get(s) - 1] > 0 ? 1 : 0);
        }

        System.out.println("$ One of model is: \n");
        System.out.println(solMappings.toString() + "\n");
    }

    private void printOtherSolutions(ModelIterator solver) throws TimeoutException {
        System.out.println("$ Other models are: \n");
        long n = solver.numberOfModelsFoundSoFar();
        while (solver.isSatisfiable()) {
            int[] sol = solver.model();
            for (String s : varMappings.keySet()) {
                solMappings.put(s, sol[varMappings.get(s) - 1] > 0 ? 1 : 0);
            }

            System.out.println(solMappings.toString() + "\n");
        }
    }


}
