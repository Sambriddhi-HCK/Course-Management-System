package GUI.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Database.CheckCredentials;
import GUI.Dashboard.Admin.AdminDashboard;

public class adminLogin extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel NComponents;
    private JLabel title;
    private JLabel topIcon;
    private JPanel pnlEast;
    private JPanel pnlSouth;
    private JPanel buttons;
    private JButton btnLogin;
    private JPanel pnlWest;
    private JPanel pnlCenter;
    private JPanel InrWest;
    private JLabel Password;
    private JLabel Email;
    private JPasswordField txtPassword;
    private JTextField txtEmail;
    private JPanel Main;
    private JPasswordField txtConfirm;
    private JPanel InrEast;
    private JLabel confirm;
    private JButton btnBack;

    public adminLogin(JFrame parent) {
        super(parent);
        setContentPane(pnlMain);
        setTitle("AdminLogin Page");
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
                CheckCredentials db = new CheckCredentials();
                if(password.equals(confirm)) {
                    if(db.checkAdmin(email, password)){
                        if (db.isVerified(email)) {
                            JOptionPane.showMessageDialog(null, "Login Successful");
                            new AdminDashboard(null);
                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(null, "Account not verified");
                        }
                    } else{
                        JOptionPane.showMessageDialog(null, "Invalid Credentials");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match");
                }
            }
        });
    }
        public static void main(String[] args) {

        adminLogin admin = new adminLogin(null);
        }
}
