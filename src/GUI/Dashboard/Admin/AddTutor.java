package GUI.Dashboard.Admin;

import Backend.Details.TutorDetails;
import Backend.Interfaces.Validation;
import Backend.Tutors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTutor extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel InrTop;
    private JPanel title;
    private JPanel pnlLogout;
    private JLabel logout;
    private JLabel pageTitle;
    private JPanel pnlIcon;
    private JLabel Icon;
    private JPanel pnlEast;
    private JPanel pnlWest;
    private JPanel pnlSouth;
    private JPanel pnlCenter;
    private JPanel pnlButtons;
    private JButton btnAddTutor;
    private JButton btnCancelFromTutor;
    private JPanel entryBox;
    private JPanel labels;
    private JLabel lName;
    private JLabel phn;
    private JLabel mName;
    private JLabel email;
    private JLabel password;
    private JLabel confirm;
    private JLabel fName;
    private JPanel entries;
    private JTextField txtLName;
    private JTextField txtphone;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtconfirm;
    private JTextField txtMName;
    private JTextField txtFName;

    public AddTutor(JFrame parent) {
        super(parent, "Add Tutor", true);
        setContentPane(pnlMain);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnCancelFromTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminDashboard(null).setVisible(true);
            }
        });


        btnAddTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fName = txtFName.getText();
                String mName = txtMName.getText();
                String lName = txtLName.getText();
                String phone = txtphone.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                String confirm = new String(txtconfirm.getPassword());
                Validation validator = new Validation();
                TutorDetails tutor = new TutorDetails();

                if (fName.isEmpty() || mName.isEmpty() || lName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!validator.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid email", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!validator.isValidPhoneNumber(phone)) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirm)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    tutor.setFirstName(fName);
                    tutor.setMiddleName(mName);
                    tutor.setLastName(lName);
                    tutor.setPhoneNumber(phone);
                    tutor.setEmail(email);
                    tutor.setPassword(password);
                    Tutors tutors = new Tutors();
                    int id = tutors.generateTutorID();
                    tutor.setTutId(id);

                    boolean execute = tutors.createTutor(tutor);
                    if (execute) {
                        JOptionPane.showMessageDialog(null, "Tutor added successfully");
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
