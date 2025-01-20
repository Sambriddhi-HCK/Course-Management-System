package exceptions;


import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Exc extends JDialog {

        private final JPanel contentPanel = new JPanel();

        public Exc(String msg) {
            this(msg, null);
        }

        /**
         * Create the dialog.
         */
        public Exc(String msg, Exception error) {
            setTitle("Error");
            setResizable(false);
            setBounds(100, 100, 450, 300);
            setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);

            getContentPane().setLayout(new BorderLayout());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(new BorderLayout(0, 0));
            {
                JLabel message = new JLabel(msg);
                message.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
                message.setPreferredSize(new Dimension(0, 50));
                message.setHorizontalTextPosition(SwingConstants.CENTER);
                message.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(message, BorderLayout.NORTH);
            }
            {
                JScrollPane scrollPane = new JScrollPane();
                contentPanel.add(scrollPane, BorderLayout.CENTER);
                {
                    StringWriter sw = new StringWriter();
                    try {
                        error.printStackTrace(new PrintWriter(sw));
                        String exceptionAsString = sw.toString();
                        JTextArea textArea = new JTextArea(exceptionAsString);
                        textArea.setEditable(false);
                        textArea.setWrapStyleWord(true);
                        textArea.setLineWrap(true);
                        textArea.setFont(new Font("Monospaced", Font.PLAIN, 9));
                        scrollPane.setViewportView(textArea);
                    } catch (Exception e) {}
                }
            }
            {
                JPanel buttonPane = new JPanel();
                buttonPane.setPreferredSize(new Dimension(10, 50));
                getContentPane().add(buttonPane, BorderLayout.SOUTH);
                buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                {
                    JButton okButton = new JButton("OK");
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                        }
                    });
                    okButton.setActionCommand("OK");
                    buttonPane.add(okButton);
                    getRootPane().setDefaultButton(okButton);
                }
            }

            setVisible(true);

    }
}
