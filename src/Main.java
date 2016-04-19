import formula.FormulaLexer;
import formula.FormulaParser;
import orange.OrangeFormula;
import orange.OrangeFormulaOptimiser;
import orange.OrangeFormulaSolver;
import orange.OrangeFormulaVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;

import java.io.FileInputStream;

/**
 * Created by yin on 11/04/16.
 */
public class Main {

    public static void main(String[] args){

        String input = null;
        if (args.length > 0) {
            input = args[0];
        }
        OrangeFormula formula = null;
        try {
            formula = parseInput(input);
        } catch (Exception e) {
            System.out.println("Invalid formula. Couldn't parse it.");
            e.printStackTrace();
        }

        if (formula != null) {

            //Print the formula after the optimisation
            System.out.println("The original result cnf is: \n");
            System.out.println(formula.toString() + "\n");

            formula = OrangeFormulaOptimiser.optimise(formula);

            OrangeFormulaSolver solver = new OrangeFormulaSolver(formula);

            //TODO: print the optimized formula
            System.out.println("The optimised result cnf is: \n");
            System.out.println(formula.toString() + "\n");

            solver.solve();

        }


    }

    /**
     * Parse the input into a formula
     * @param input the input filename, set to null if you want to use shell (System.in)
     * @return
     * @throws Exception
     */
    private static OrangeFormula parseInput(String input) throws Exception {
        ANTLRInputStream inputStream = new ANTLRInputStream(input == null ? System.in : new FileInputStream(input));

        FormulaLexer lexer = new FormulaLexer(inputStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        FormulaParser parser = new FormulaParser(tokenStream);

        parser.setBuildParseTree(true);

        RuleContext tree = parser.formula();

        System.out.println(tree.toStringTree(parser));

        OrangeFormulaVisitor visitor = new OrangeFormulaVisitor();

        //Print the parseTree generated from the input
//        Trees.inspect(tree, parser);

        return visitor.visit(tree);
    }

}
