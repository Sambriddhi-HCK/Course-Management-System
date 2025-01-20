package GUI.WelcomePage;

import Database.Database;
import Backend.Interfaces.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentSignUp extends JDialog {
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel pnlEast;
    private JPanel pnlSouth;
    private JPanel pnlWest;
    private JPanel pnlCenter;
    private JButton btnAdd;
    private JButton btnSignUp;
    private JLabel title;
    private JLabel userIcon;
    private JLabel top;
    private JPanel entryBox;
    private JLabel fName;
    private JLabel mName;
    private JLabel lName;
    private JLabel phn;
    private JLabel email;
    private JLabel DOB;
    private JLabel password;
    private JPanel labels;
    private JLabel confirm;
    private JPanel entries;
    private JTextField txtMName;
    private JTextField txtFName;
    private JTextField txtLName;
    private JPasswordField txtConfirm;
    private JTextField txtPhone;
    private JTextField txtDOB;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<Object> txtCourse;
    private JLabel course;
    private JPanel buttons;
    private JLabel loginPrompt;
    private JButton btnLogin;
    private JPanel pnllMainn;

    public StudentSignUp(JFrame parent) {

        super(parent);
        setContentPane(pnlMain);
        setTitle("Student SignUp Page");
        setMinimumSize(new Dimension(500, 750));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fName = txtFName.getText();
                String mName = txtMName.getText();
                String lName = txtLName.getText();
                String phone = txtPhone.getText();
                String dob = txtDOB.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                String confirm = new String(txtConfirm.getPassword());
                String course = (String) txtCourse.getSelectedItem();

                Validation validator = new Validation();
                Database db = new Database();

                if (fName.isEmpty() || lName.isEmpty() || dob.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill the fields with a *");
                } else if (!password.equals(confirm)) {
                    JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match");
                } else if (!validator.isValidName(fName) || !validator.isValidName(lName)) {
                    JOptionPane.showMessageDialog(null, "Invalid First or Last Name");
                } else if (!validator.isValidPhoneNumber(phone)) {
                    JOptionPane.showMessageDialog(null, "Invalid Phone Number");
                } else if (!validator.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid Email Address");
                } else if (!validator.isValidDateOfBirth(dob)) {
                    JOptionPane.showMessageDialog(null, "Invalid Date of Birth");
                } else if (!validator.isStrongPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Password must be strong with at least one uppercase letter, one lowercase letter, one digit, and one special character");
                } else {
                    if (db.checkUser(email)) {
                        JOptionPane.showMessageDialog(null, "User already exists");
                        dispose();
                    } else {
                        db.addStudent(fName, mName, lName, phone, dob, email, password, course);
                        JOptionPane.showMessageDialog(null, "User added successfully");
                        dispose();
                        new Login(null);
                    }
                }
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(null);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        StudentSignUp std = new StudentSignUp(null);
    }
}

