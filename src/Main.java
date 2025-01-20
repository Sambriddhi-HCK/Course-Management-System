import Database.DBConnection;
import java.sql.Connection;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import exceptions.Exc;
import GUI.WelcomePage.Login;

public class Main extends DBConnection{
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new Login(null);
                } catch (Exception e) {
                    new Exc("Failed To Load The Application.", e);
                }
            }
        });

        DBConnection db = new DBConnection();
        Connection con = db.getConnection();
        try {
            con.close();
        }catch (Exception ex) {
            System.out.println("Database connection failed!");
        }
    }
}