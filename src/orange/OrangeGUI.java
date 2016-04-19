package orange;

import formula.FormulaLexer;
import formula.FormulaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class OrangeGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JCheckBox subsumptionCheckBox;
    private JCheckBox unitPropagationCheckBox;
    private JLabel pastResult;
    private JTextArea textArea2;

    private List<String> pastInputList;

    public OrangeGUI() {
        setTitle("OrangeGUI");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        pastInputList = new ArrayList<String>();

    }

    private void onOK() {

        OrangeResultGUI resultGUI = new OrangeResultGUI();
        String input = textArea1.getText();
        String result = start(input);

        pastInputList.add(input);
        textArea2.setText(result + ": " + input + "\n" + textArea2.getText());
        pack();

        resultGUI.pack();

        resultGUI.setVisible(true);

    }

    public String start(String input) {
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
        OrangeFormula formula = null;
        try {
            formula = visitor.visit(tree);
        } catch (Exception e) {
            System.out.println("Invalid formula. Couldn't parse it.");
            e.printStackTrace();
        }

        if (formula != null) {

            //Print the formula after the optimisation
            System.out.println("The original result cnf is: \n");
            System.out.println(formula.toString() + "\n");

            if (subsumptionCheckBox.isSelected()) {
                OrangeFormulaOptimiser.subsumption(formula);
            }

            if (unitPropagationCheckBox.isSelected()) {
                OrangeFormulaOptimiser.unitPropagation(formula);
            }

//            formula = OrangeFormulaOptimiser.optimise(formula);

            OrangeFormulaSolver solver = new OrangeFormulaSolver(formula);

            System.out.println("The optimised result cnf is: \n");
            System.out.println(formula.toString() + "\n");

            return solver.solve() ? "SAT" : "UNSAT";

        } else {
            return "ERROR";
        }
    }

    private void onCancel() {
        textArea1.setText("");
    }

    public static void main(String[] args) {
        OrangeGUI dialog = new OrangeGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
