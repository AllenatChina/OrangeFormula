package orange;

import formula.FormulaLexer;
import formula.FormulaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.sat4j.specs.TimeoutException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        System.setOut(new PrintStream(new StreamCapturer()));
    }

    private void onOK() {
        if (result.isEmpty()) {
            result = "STOPPED";
        }
        dispose();
    }

    public void appendText(final String text) {
        if (EventQueue.isDispatchThread()) {
            output.append(text);
            output.setCaretPosition(output.getText().length());
            if (logWriter != null) {
                try {
                    logWriter.write(text);
                    logWriter.newLine();
                    logWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
    private BufferedWriter logWriter;

    public void parse(final String input, final boolean subsump, final boolean unitprop, final boolean purelit) {

        work = new Thread() {
            @Override
            public void run() {

                Calendar calendar = Calendar.getInstance();
                String id = new SimpleDateFormat("yyyyMMdd_HHmmss").format(calendar.getTime());
                try {
                    logWriter = new BufferedWriter(new FileWriter("result_" + id + ".txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long start = System.currentTimeMillis();

                OrangeFormula formula = null;

                printProcess("Parsing the input...");
                try {

                    ANTLRInputStream inputStream = new ANTLRInputStream(input);
                    FormulaLexer lexer = new FormulaLexer(inputStream);
                    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                    FormulaParser parser = new FormulaParser(tokenStream);
                    parser.setBuildParseTree(true);

                    parser.addErrorListener(new OrangeErrorListener());

                    RuleContext tree = parser.formula();

                    System.out.println(tree.toStringTree(parser));

                    OrangeFormulaVisitor visitor = new OrangeFormulaVisitor();

                    //Print the parseTree generated from the input
                    //        Trees.inspect(tree, parser);

                    formula = visitor.visit(tree);


                } catch (Exception e) {
                    System.out.println("\nIllegal formula. Couldn't parse it.");
                    result = "ERROR";
                    printProcess("Error");
                }

                long toParsed = System.currentTimeMillis();

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

                    long toOptimised = System.currentTimeMillis();

                    final OrangeFormulaSolver solver = new OrangeFormulaSolver(formula);
                    solver.setId(id);

                    System.out.println("The optimised result cnf is: \n");
                    System.out.println(formula.toString() + "\n");

                    printProcess("Solving with Sat4j...");
                    result = solver.solve() ? "SAT" : "UNSAT";

                    long toSolved = System.currentTimeMillis();

                    buttonStop.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (work != null) {
                                if (work.getState() == Thread.State.TERMINATED) {

                                    if (buttonStop.getText().equals("STOP")) {
                                        printProcess("Already stopped!");
                                    } else {
                                        if (result.equals("UNSAT")) {

                                            System.out.println("$ The cnf is unsatisfiable.");

                                        } else if (result.equals("SAT")){
                                            buttonStop.setText("STOP");
                                            printProcess("Finding more models with Sat4j...");
                                            try {
                                                solver.printOtherSolutions();
                                            } catch (TimeoutException e1) {
                                                System.out.println("$ The solver is unable to finish solving in a short time. TIMEOUT!");
                                            }
                                            printProcess("DONE!");
                                        } else if (result.equals("ERROR")) {
                                            System.out.println("Error, NO model.");
                                        }
                                    }
                                }else {
                                    if (buttonStop.getText().equals("STOP")) {
                                        work.stop();
                                        printProcess("Stopped!");
                                    }
                                }
                                System.gc();
                            }
                        }
                    });

                    printElapsedTime(start, toParsed, toOptimised, toSolved);

                    printProcess("Done!");

                } else {
                    result = "ERROR";
                    printProcess("Error");
                }

            }

        };
        work.start();

    }

    private void printElapsedTime(long start, long toParsed, long toOptimised, long toSolved) {
        System.out.println("\n======================================");
        System.out.println("- Time for parsing: " + (toParsed - start) + " milliseconds");
        System.out.println("- Time for optimising: " + (toOptimised - toParsed) + " milliseconds");
        System.out.println("- Time for solving: " + (toSolved - toOptimised) + " milliseconds");
        System.out.println("+ Total elasped time: " + (toSolved - start) + " milliseconds");
        System.out.println("======================================\n");
    }

    private void printProcess(String process) {
        processLabel.setText(process);
    }

    public class StreamCapturer extends OutputStream {

        private StringBuilder buffer;

        public StreamCapturer() {
            buffer = new StringBuilder(128);
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
        }
    }

}