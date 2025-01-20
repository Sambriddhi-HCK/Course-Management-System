package GUI.Dashboard.Tutor;

import GUI.WelcomePage.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarksForm extends JDialog{

    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel InrTop;
    private JPanel title;
    private JLabel pageTitle;
    private JPanel pnlIcon;
    private JLabel Icon;
    private JPanel pnlButtons;
    private JButton btnAssignMarks;
    private JButton btnCancelFromAssignMarks;
    private JPanel pnlCenter;
    private JPanel pnlEntryBox;
    private JPanel labels;
    private JLabel stdId;
    private JLabel moduleCode;
    private JLabel marks;
    private JPanel entries;
    private JTextField txtStudentId;
    private JTextField txtModuleCode;
    private JTextField txtMarks;

    public MarksForm(JFrame parent) {
        super(parent);
        setContentPane(pnlMain);
        setTitle("AdminLogin Page");
        setMinimumSize(new Dimension(500, 450));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        btnCancelFromAssignMarks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(null);
                dispose();
            }
        });

    }
}
