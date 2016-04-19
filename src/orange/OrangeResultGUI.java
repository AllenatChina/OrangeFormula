package orange;

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
    private JButton buttonOK;
    private JTextArea output;

    public OrangeResultGUI() {
        setTitle("Result");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
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