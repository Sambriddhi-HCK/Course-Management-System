package GUI.WelcomePage;

import Backend.Interfaces.Validation;
import Database.Database;
import Backend.Details.LoginDetails;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherSignUp extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel pnlWest;
    private JPanel pnlSouth;
    private JPanel pnlEast;
    private JLabel title;
    private JLabel userIcon;
    private JPanel pnlCenter;
    private JPanel entryBox;
    private JPanel labels;
    private JPanel entries;
    private JLabel fName;
    private JLabel mName;
    private JLabel lName;
    private JLabel phn;
    private JLabel email;
    private JLabel password;
    private JLabel confirm;
    private JTextField txtMName;
    private JTextField txtFName;
    private JTextField txtLName;
    private JTextField txtphone;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtconfirm;
    private JPanel buttons;
    private JLabel pnllogin;
    private JButton btnLogin;
    private JButton btnSignUp;

    private LoginDetails details = new LoginDetails();


    public TeacherSignUp( JFrame parent) {

        super(parent);
        setContentPane(pnlMain);
        setTitle("Teacher SignUp Page");
        setMinimumSize(new Dimension(500,750));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(null);
                dispose();
            }
        });
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = txtFName.getText();
                String mname = txtMName.getText();
                String lname = txtLName.getText();
                String phone = txtphone.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                String confirm = new String(txtconfirm.getPassword());
                Validation validator = new Validation();


                if (fname.isEmpty() || lname.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill the fields with a *");
                } else if (!password.equals(confirm)) {
                    JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match");
                } else if (!validator.isValidName(fname) || !validator.isValidName(lname)) {
                    JOptionPane.showMessageDialog(null, "Invalid First or Last Name");
                } else if (!validator.isValidPhoneNumber(phone)) {
                    JOptionPane.showMessageDialog(null, "Invalid Phone Number");
                } else if (!validator.isStrongPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Password must be strong with at least one uppercase letter, one lowercase letter, one digit, and one special character");
                } else if (!validator.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid Email");
                } else {
                    Database db = new Database();
                    if (db.checkUser(email)) {
                        JOptionPane.showMessageDialog(null, "User already exists");
                    } else {
                        db.addTeacher(fname, mname, lname, phone, email, password);
                        JOptionPane.showMessageDialog(null, "User added successfully");
                        dispose();
                        new Login(null);
                    }
                }
            }
        });
    }

}
