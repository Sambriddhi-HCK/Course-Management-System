package GUI.WelcomePage;
import Backend.Details.StudentDetails;
import Backend.Details.TutorDetails;
import Database.CheckCredentials;
import Database.Database;
import GUI.Dashboard.Std.StudentDashBoard;
import GUI.Dashboard.Tutor.TeacherDashBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel pnlEast;
    private JPanel pnlSouth;
    private JPanel pnlWest;
    private JPanel buttons;
    private JButton btnLogin;
    private JButton btnSignUp;
    private JPanel pnlCenter;
    private JPanel NComponents;
    private JLabel title;
    private JLabel topIcon;
    private JTextField txtEmail;
    private JLabel Email;
    private JLabel Password;
    private JPasswordField txtPassword;
    private JPanel InrNorth;
    private JPanel InrSouth;
    private JButton btnAdmin;
    private JLabel prompt;

    public Login(JFrame parent) {
        super(parent);
        setContentPane(pnlMain);
        setTitle("Login Page");
        setMinimumSize(new Dimension(500,450));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginUser();
            }
        });

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new User(null);
                dispose();
            }
        });
        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new adminLogin(null);
                dispose();
            }
        });
    }

    private void loginUser() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        CheckCredentials db = new CheckCredentials();
        TutorDetails tutor;
        StudentDetails student;
        Database data = new Database();

        if (db.checkStudent(email, password)) {
            JOptionPane.showMessageDialog(btnLogin, "Welcome to the system");
            dispose();
            student = data.getStudentDetailsByEmail(email);
            System.out.println(student);
            if(student == null){
                JOptionPane.showMessageDialog(btnLogin, "Student details not found");
                return;
            }

            int id = student.getStdId();
            new StudentDashBoard(null,id);
        } else if (db.checkTeacher(email, password)) {
            JOptionPane.showMessageDialog(btnLogin, "Welcome to the system");
            dispose();
            tutor = data.getTutorDetailsByEmail(email);
            int id = tutor.getTutId();
            new TeacherDashBoard(null,id);
        } else {
            JOptionPane.showMessageDialog(btnLogin, "Username or password did not match");
        }
    }

    public static void main(String[] args) {
        Login login = new Login(null);
    }

}
