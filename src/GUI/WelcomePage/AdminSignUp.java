package GUI.WelcomePage;

import Backend.Interfaces.Validation;
import Database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminSignUp extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel NComponents;
    private JLabel topIcon;
    private JPanel pnlEast;
    private JPanel pnlSouth;
    private JPanel buttons;
    private JButton btnLogin;
    private JLabel title;

    private JButton btnBack;
    private JPanel pnlWest;
    private JPanel pnlCenter;
    private JPanel InrWest;
    private JLabel Email;
    private JLabel confirm;
    private JLabel Password;
    private JPanel InrEast;
    private JTextField txtEmail;
    private JPasswordField txtConfirm;
    private JPasswordField txtPassword;


    public AdminSignUp(JFrame parent) {
        super(parent);
        setContentPane(pnlMain);
        setTitle("Admin SignUP Page");
        setMinimumSize(new Dimension(500, 450));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(null);
                dispose();
            }
        });


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String password = txtPassword.getText();
                String confirm = txtConfirm.getText();

                Database db = new Database();
                Validation validator = new Validation();

                if (email.isEmpty() ||  password.isEmpty() || confirm.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                }else if(!password.equals(confirm)) {
                    JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match");
                }else if (!validator.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid Email Address");
                } else if(db.checkUser(email)){
                        JOptionPane.showMessageDialog(null, "User already exists");
                        dispose();
                } else if (!validator.isStrongPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Password must be strong with at least one uppercase letter, one lowercase letter, one digit, and one special character");
                }else {
                    db.addAdmin(email, password);
                    JOptionPane.showMessageDialog(null, "User added successfully");
                    dispose();
                    new adminLogin(null);
                }
            }
        });
    }
}
