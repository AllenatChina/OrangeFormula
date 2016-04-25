package orange;

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
    private JTextArea pastResults;
    private JCheckBox pureLiteralsCheckBox;

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

        final OrangeResultGUI resultGUI = new OrangeResultGUI();
        final String input = textArea1.getText();
        resultGUI.parse(input,
                subsumptionCheckBox.isSelected(), unitPropagationCheckBox.isSelected(), pureLiteralsCheckBox.isSelected());

        resultGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                String result = resultGUI.result;
                pastResults.setText(result + ": " + input + "\n" + pastResults.getText());
                pack();
            }
        });

        resultGUI.pack();

        resultGUI.setVisible(true);

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
