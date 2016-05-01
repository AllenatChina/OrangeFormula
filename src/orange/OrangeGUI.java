package orange;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OrangeGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JCheckBox subsumptionCheckBox;
    private JCheckBox unitPropagationCheckBox;
    private JCheckBox pureLiteralsCheckBox;
    private JList list;
    private JScrollPane listSP;

    DefaultListModel model;

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

        model = new DefaultListModel();
        list.setModel(model);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (list.getSelectedIndex() >= 0) {
                        textArea1.setText(((ResultCell) model.get(list.getSelectedIndex())).input);
                        list.ensureIndexIsVisible(list.getSelectedIndex());
                    }
                }
            }
        });

        listSP.setVisible(false);

    }

    private void onOK() {

        final OrangeResultGUI resultGUI = new OrangeResultGUI();
        final String input = textArea1.getText();
        resultGUI.parse(input,
                subsumptionCheckBox.isSelected(), unitPropagationCheckBox.isSelected(), pureLiteralsCheckBox.isSelected());

        resultGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                listSP.setVisible(true);
                model.addElement(ResultCell.newCell(input, resultGUI.result));
                list.setSelectedIndex(model.size() - 1);
                pack();
            }
        });

        resultGUI.pack();

        resultGUI.setVisible(true);

    }

    private static class ResultCell {

        String input;
        String result;

        private static ResultCell newCell(String input, String result) {
            ResultCell cell = new ResultCell();
            cell.input = input;
            cell.result = result;
            return cell;
        }

        @Override
        public String toString() {
            String ret = result + ": \n" + input;
            return ret.length() > 50 ? ret.substring(0, 50) + "..." : ret;
        }
    }


    private void onCancel() {
        textArea1.setText("");
        list.clearSelection();
    }

    public static void main(String[] args) {
        OrangeGUI dialog = new OrangeGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
