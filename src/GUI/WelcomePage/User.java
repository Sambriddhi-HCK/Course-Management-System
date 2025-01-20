package GUI.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User extends JDialog{
    private JPanel userPnlMain;
    private JPanel pnlCenter;
    private JPanel pnl;
    private JComboBox<String> choosenUser;
    private JLabel ask;
    private JPanel btnpnl;
    private JButton saveButton;
    private JButton btnBack;


    public User(JFrame parent) {
        super(parent);
        setContentPane(userPnlMain);
        setTitle("Choose user");
        setMinimumSize(new Dimension(500,450));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) choosenUser.getSelectedItem();
                if ("Student".equals(selectedUser)) {
                    // Save data to the Student table
                    new StudentSignUp(parent);
                    dispose();
                } else if ("Teacher".equals(selectedUser)) {
                    // Save data to the Teacher table
                    new TeacherSignUp(parent);
                    dispose();
                } else if ("Admin".equals(selectedUser)) {
                    // Save data to the Admin table
                    new AdminSignUp(parent);
                    dispose();
                }
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(null);
                dispose();
            }
        });
    }

}

