package orange;

import formula.FormulaLexer;
import formula.FormulaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class OrangeResultGUI extends JDialog{
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextArea output;
    private JLabel processLabel;
    private JButton buttonStop;

    public OrangeResultGUI() {
        setTitle("Result");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (work != null) {
                    if (work.getState() == Thread.State.TERMINATED) {
                        printProcess("Already stopped!");
                    }else {
                        work.stop();
                        printProcess("Stopped!");
                        result = "STOPPED";
                    }
                    System.gc();
                }
            }
        });

        PrintStream ps = System.out;
        System.setOut(new PrintStream(new StreamCapturer(ps)));
    }

    private void onOK() {
        dispose();
    }

    public void appendText(final String text) {
        if (EventQueue.isDispatchThread()) {
            output.append(text);
            output.setCaretPosition(output.getText().length());
        } else {

            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    appendText(text);
                }
            });

        }
    }

    public String result = "";

    private Thread work;

    public void parse(final String input, final boolean subsump, final boolean unitprop, final boolean purelit) {

        work = new Thread() {
            @Override
            public void run() {

                OrangeFormula formula = null;

                printProcess("Parsing the input...");
                try {

                    ANTLRInputStream inputStream = new ANTLRInputStream(input);
                    FormulaLexer lexer = new FormulaLexer(inputStream);
                    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                    FormulaParser parser = new FormulaParser(tokenStream);
                    parser.setBuildParseTree(true);
                    RuleContext tree = parser.formula();

                    System.out.println(tree.toStringTree(parser));

                    OrangeFormulaVisitor visitor = new OrangeFormulaVisitor();

                    //Print the parseTree generated from the input
//        Trees.inspect(tree, parser);

                    formula = visitor.visit(tree);

                } catch (Exception e) {
                    System.out.println("Invalid formula. Couldn't parse it.");
                    result = "ERROR";
                    printProcess("Error");
                    e.printStackTrace();
                }

                if (formula != null) {

                    //Print the formula after the optimisation
                    System.out.println("The original result cnf is: \n");
                    System.out.println(formula.toString() + "\n");

                    boolean ifCanOpt = subsump || unitprop || purelit;

                    while (ifCanOpt) {

                        if (subsump) {
                            printProcess("Doing Subsumption...");
                            ifCanOpt = OrangeFormulaOptimiser.subsumption(formula);
                        }

                        if (unitprop) {
                            printProcess("Doing Unit propagation...");
                            ifCanOpt = OrangeFormulaOptimiser.unitPropagation(formula);
                        }

                        if (purelit) {
                            printProcess("Doing Pure Literals...");
                            ifCanOpt = OrangeFormulaOptimiser.pureLiterals(formula);
                        }

                    }

                    OrangeFormulaSolver solver = new OrangeFormulaSolver(formula);

                    System.out.println("The optimised result cnf is: \n");
                    System.out.println(formula.toString() + "\n");

                    printProcess("Solving with Sat4j...");
                    result = solver.solve() ? "SAT" : "UNSAT";

                    printProcess("Done!");

                } else {
                    result = "ERROR";
                    printProcess("Error");
                }

            }

        };
        work.start();

    }

    private void printProcess(String process) {
        processLabel.setText(process);
    }

    public class StreamCapturer extends OutputStream {

        private StringBuilder buffer;
        private PrintStream old;

        public StreamCapturer(PrintStream old) {
            buffer = new StringBuilder(128);
            this.old = old;
        }

        @Override
        public void write(int b) throws IOException {
            char c = (char) b;
            String value = Character.toString(c);
            buffer.append(value);
            if (value.equals("\n")) {
                appendText(buffer.toString());
                buffer.delete(0, buffer.length());
            }
            old.print(c);
        }
    }

}