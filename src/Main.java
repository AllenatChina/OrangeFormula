import formula.FormulaLexer;
import formula.FormulaParser;
import orange.OrangeFormula;
import orange.OrangeFormulaOptimiser;
import orange.OrangeFormulaSolver;
import orange.OrangeFormulaVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.io.FileInputStream;
import java.util.BitSet;

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
            System.exit(-1);
        }

        if (formula != null) {

            //Print the formula after the optimisation
            System.out.println("The original result cnf is: \n");
            System.out.println(formula.toString() + "\n");

            formula = OrangeFormulaOptimiser.optimise(formula);

            OrangeFormulaSolver solver = new OrangeFormulaSolver(formula);

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

        parser.addErrorListener(new ANTLRErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                System.out.println("Syntax error.");
            }

            @Override
            public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
                System.out.println("Ambiguity.");
            }

            @Override
            public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
                System.out.println("Attempting Full Context.");
            }

            @Override
            public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
                System.out.println("Context Sensitivity.");
            }
        });
        RuleContext tree = parser.formula();

        System.out.println(tree.toStringTree(parser));

        OrangeFormulaVisitor visitor = new OrangeFormulaVisitor();

        //Print the parseTree generated from the input
//        Trees.inspect(tree, parser);

        return visitor.visit(tree);
    }

}
