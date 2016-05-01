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

    private String id;

    public OrangeFormulaSolver(OrangeFormula formula) {
        this.formula = formula;
        varMappings = formula.getMapToInt();
        solMappings = new HashMap<String, Integer>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean solve() {

        System.out.println("By mapping variables to integers,");
        System.out.println(varMappings.toString());
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
            System.out.println("$ The solver is unable to finish solving in a short time. TIMEOUT!");
        }

        return false;
    }

    private String filename = "";

    private void writeDIMACS() throws IOException {
        filename = "dimacs" + (id == null ? "" : ("_" + id)) + ".cnf";

        FileWriter fileWriter = new FileWriter(filename);

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        List<int[]> clauses = formula.getIntClauses();

        bufferedWriter.write("c variable mappings: " + varMappings.toString());
        bufferedWriter.newLine();
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

    private ModelIterator mi;

    private boolean solveDIMACS() throws IOException, ParseFormatException, ContradictionException, TimeoutException {

        LecteurDimacs reader = new LecteurDimacs(SolverFactory.newDefault());
        ISolver solver = (ISolver) reader.parseInstance(filename);
        mi = new ModelIterator(solver);

        if (mi.isSatisfiable()) {
            System.out.println("$ The cnf is satisfiable:\n");

            int[] sol = mi.model();

            printSolutions(sol);

//            PrintWriter writer = new PrintWriter(System.out);
//            solver.printStat(writer);
//            writer.close();
//            printOtherSolutions(mi);

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

    public void printOtherSolutions() throws TimeoutException {
        if (mi != null) {
            long n = mi.numberOfModelsFoundSoFar();
            long i = 0;
            while (mi.isSatisfiable()) {
                if (i++ == 0) {
                    System.out.println("$ Other models are: \n");
                }
                int[] sol = mi.model();
                for (String s : varMappings.keySet()) {
                    solMappings.put(s, sol[varMappings.get(s) - 1] > 0 ? 1 : 0);
                }

                System.out.println(solMappings.toString() + "\n");
            }
        }
    }


}
