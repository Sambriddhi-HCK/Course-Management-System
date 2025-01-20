package Database;

import Backend.Details.TutorDetails;
import Backend.Details.StudentDetails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database extends DBConnection{
    static final String DB_URL = "jdbc:mysql://localhost:3306/crs";
    static final String Username = "root";
    static final String Password = "";

    /*
     * This method checks whether the entered email is already present in the StudentCreds or in the Tutor table
     */
    public boolean checkUser(String email) {
        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String query = "SELECT Email FROM StudentCreds WHERE Email = ? UNION SELECT email FROM Tutor WHERE email = ? UNION SELECT adminMail FROM Admin WHERE adminMail = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
     * This method adds the details of a student in Student and StudentCreds tables
    */

    public void addStudent(String fName, String mName, String lName, String phone, String dob, String email, String password,String course) {
        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String insertQuery = "INSERT INTO Student (Firstname, Middlename, Lastname, PhoneNumber, DOB, CourseEnrolled) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, fName);
                preparedStatement.setString(2, mName);
                preparedStatement.setString(3, lName);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, dob);
                preparedStatement.setString(6, course);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String insertQuery2 = "INSERT INTO StudentCreds (Email, Password) VALUES ( ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery2)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String insertQuery3 = "INSERT INTO student_year (semester, level) VALUES ( ? , ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery3)) {
                preparedStatement.setString(1, "1");
                preparedStatement.setString(2, "4");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
     * This method adds the details of an instructor in Tutor table
    */

    public void addTeacher(String fname, String mname, String lname, String phone, String email, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String query = "Insert into Tutor (firstName, middleName, lastName, phoneNumber, email, password ) VALUES (?, ?, ?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, fname);
                preparedStatement.setString(2, mname);
                preparedStatement.setString(3, lname);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method adds the details of an admin in the admin table
     */
    public void addAdmin(String email, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String query = "Insert into Admin (adminMail, password ) VALUES (?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /*
     * This method retrieves the details of a student by the provided email
     */
    public StudentDetails getStudentDetailsByEmail(String email) {
        StudentDetails details = new StudentDetails();

        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String query = "SELECT s.* FROM Student s JOIN StudentCreds sc ON s.Student_Id = sc.id " +
                    "WHERE sc.Email = ? ;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        details.setStdId(resultSet.getInt("Student_Id"));
                        details.setFirstName(resultSet.getString("Firstname"));
                        details.setMiddleName(resultSet.getString("Middlename"));
                        details.setLastName(resultSet.getString("Lastname"));
                        details.setPhone(resultSet.getString("PhoneNumber"));
                        details.setDob(resultSet.getDate("DOB"));
                        details.setCourseEnrolled(resultSet.getString("CourseEnrolled"));
                        details.setDateEnrolled(resultSet.getDate("DateEnrolled"));
                    }
                    return details;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * This method retrieves the details of an instructor by the provided email
     */
    public TutorDetails getTutorDetailsByEmail(String email) {
        TutorDetails details = new TutorDetails();

        try (Connection connection = DriverManager.getConnection(DB_URL, Username, Password)) {
            String query = "SELECT * FROM Tutor WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        details.setTutId(resultSet.getInt("id"));
                        details.setFirstName(resultSet.getString("firstName"));
                        details.setMiddleName(resultSet.getString("middleName"));
                        details.setLastName(resultSet.getString("lastName"));
                        details.setPhoneNumber(resultSet.getString("phoneNumber"));
                        details.setEmail(resultSet.getString("email"));
                    }
                    return details;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}