package Database;
/*
 * This method checks whether the user's credentials are valid or not in StudentCreds and tutorCreds tables
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckCredentials extends DBConnection{

    public boolean checkStudent(String email, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM StudentCreds WHERE Email=? AND Password=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();  // Returns true if a matching user is found
                }
            }
        } catch (SQLException exc) {
            System.out.println("Error checking credentials: " + exc.getMessage());
            exc.printStackTrace();
        }
        return false;
    }
    public boolean checkTeacher(String email, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM Tutor WHERE email=? AND password=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();  // Returns true if a matching user is found
                }
            }
        } catch (SQLException exc) {
            System.out.println("Error checking credentials: " + exc.getMessage());
            exc.printStackTrace();
        }
        return false;
    }
    public boolean checkAdmin(String email, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM Admin WHERE adminMail=? AND password=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();  // Returns true if a matching user is found
                }
            }
        } catch (SQLException exc) {
            System.out.println("Error checking credentials: " + exc.getMessage());
            exc.printStackTrace();
        }
        return false;
    }

    /*
     * This method checks whether the entered email for admin has been verified in the database or not
     */
    public boolean isVerified(String email) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM Admin WHERE adminMail=? AND isVerified=1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException exc) {
            System.out.println("Error checking credentials: " + exc.getMessage());
            exc.printStackTrace();
            return false;
        }
    }
}
