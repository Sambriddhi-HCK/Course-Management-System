package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import Backend.Details.StudentDetails;
import Backend.Interfaces.StudentInterface;
import Database.DBConnection;
import exceptions.DuplicateStudentModuleRegistrationException;
import exceptions.Exc;
import exceptions.StudentModuleCountLimitException;

/**
 * This class file implements the StudentInterface where methods are predefined and
 * inherits the DBConnector for Database Connectivity
 * This Class contains all the methods required for CRUD functionality of Students
 */
public class Students extends DBConnection implements StudentInterface {

    /**
     * This function is implemented from StudentInterface Interface
     * which is used to Create Student
     * Details of the Students are Stored in the Student Detail Class
     * returns true if module is successfully added to Database
     * returns false if error occurs
     */

    @Override
    public boolean createStudent(StudentDetails stdDetails) {
        try {
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement("INSERT INTO Student "
                    + "( Firstname, Middlename, Lastname, PhoneNumber, DOB, CourseEnrolled) "
                    + "Values ( ?, ?, ?, ?, ?, ?);");
            st.setString(1, stdDetails.getFirstName());
            st.setString(2, stdDetails.getMiddleName());
            st.setString(3, stdDetails.getLastName());
            st.setString(4, stdDetails.getPhone());
            st.setDate(5, stdDetails.getDob());
            st.setString(6, stdDetails.getCourseEnrolled());

            st.execute();

            st = con.prepareStatement("INSERT INTO studentcreds (id, Email, Password) VALUES (?, ?, ?)");
            st.setInt(1, stdDetails.getStdId());
            st.setString(2, stdDetails.getEmail());
            st.setString(3, stdDetails.getPassword());

            st.execute();

            st = con.prepareStatement("INSERT INTO student_year (studentId, semester, level) VALUES (?, 1, 4)");
            st.setInt(1, stdDetails.getStdId());

            st.execute();

            con.close();

            return true;

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                e.printStackTrace();
                new Exc("Student is Already Registered.");
            } else {
                new Exc("Error While Adding Student Detail", e);
            }
            return false;
        }
    }

    /**
     * This function is implemented from StudentInterface which is
     * used to Remove Student
     * ID of the Student to be removed
     * returns true if Student is successfully Deleted from Database
     * returns false if error occurs
     */
    @Override
    public boolean removeStudent(int stdID) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("DELETE FROM studentcreds WHERE id=?");
            ps.setInt(1, stdID);
            ps.execute();

            con.close();

            return true;
        } catch (Exception e) {
            new Exc("Error Occurred While Deleting Student Details", e);
            return false;
        }
    }

    /**
     * This function is implemented from StudentInterface which is
     * used to Update Student Information

     * Details of the Students are Stored in the Student Detail Class
     * new id to be assigned as String
     * returns true if Student is successfully updated in Database
     * returns false if error occurs
     */
    @Override
    public boolean updateStudent(StudentDetails stdDetails) {
        Connection con = getConnection();

        try {

            PreparedStatement st = con.prepareStatement("UPDATE Student SET "
                    + "Firstname=?, Lastname=?, CourseEnrolled=? "
                    + "WHERE Student_Id=?");

            st.setString(1, stdDetails.getFirstName());
            st.setString(2, stdDetails.getLastName());
            st.setString(3, stdDetails.getCourseEnrolled());
            st.setInt(4, stdDetails.getStdId());

            st.executeUpdate();

            con.close();

            return true;
        } catch (SQLException e) {
            new Exc("Error in Database", e);

            return false;
        }
    }

    /**
     * This function is implemented from StudentInterface which is
     * used to enroll Certain Student to a Course

     * returns true if student is successfully enrolled
     * returns false if error occurs
     */
    @Override
    public boolean enrollModule(StudentDetails stdDetails, String moduleCode) {
        Connection con = getConnection();

        try {
            PreparedStatement st = con.prepareStatement("SELECT module_code FROM Student_Enrollment WHERE student_id=?");
            st.setInt(1, stdDetails.getStdId());

            ResultSet rs = st.executeQuery();

            int registerCount = 0;

            while(rs.next()) {
                registerCount++;
            }

            if (registerCount >= 4) {
                throw new StudentModuleCountLimitException();
            }

            st = con.prepareStatement("SELECT module_code FROM Student_Enrollment WHERE student_id=? AND module_code=?");
            st.setInt(1, stdDetails.getStdId());
            st.setString(2, moduleCode);

            rs = st.executeQuery();

            if(rs.next()) {
                throw new DuplicateStudentModuleRegistrationException();
            }

            st = con.prepareStatement("INSERT INTO Student_Enrollment"
                    + "(student_id, module_code) "
                    + "VALUES (?, ?)");

            st.setInt(1, stdDetails.getStdId());
            st.setString(2, moduleCode);

            st.executeUpdate();

            con.close();

            return true;
        } catch (DuplicateStudentModuleRegistrationException | StudentModuleCountLimitException | SQLException e) {
            if (e instanceof SQLException) {
                new Exc("Error While Adding Data to Database");
            } else {
                new Exc(e.getMessage());
            }

            return false;
        }
    }


    /**
     * This function is implemented from StudentInterface which is
     * used to get Details of existing Student in database

     * returns Details of the Student stored in the CourseDetails class
     */
    @Override
    public StudentDetails getStudentDetails(int stdID) {
        StudentDetails studentDetails = new StudentDetails();

        Connection con = getConnection();

        try {

            PreparedStatement st = con.prepareStatement("SELECT Student.*, StudentCreds.Email, StudentCreds.Password, student_year.semester ,student_year.level "
                    + "FROM Student "
                    + "INNER JOIN StudentCreds ON Student.Student_Id=StudentCreds.id "
                    + "INNER JOIN student_year ON StudentCreds.id=student_year.studentId "
                    + "WHERE Student.Student_Id=?");
            st.setInt(1, stdID);

            ResultSet rs = st.executeQuery();

            rs.next();

            studentDetails.setStdId(stdID);
            studentDetails.setFirstName(rs.getString("Firstname"));
            studentDetails.setMiddleName(rs.getString("Middlename"));
            studentDetails.setLastName(rs.getString("Lastname"));
            studentDetails.setDob(rs.getDate("DOB"));
            studentDetails.setPhone(rs.getString("PhoneNumber"));
            studentDetails.setCourseEnrolled(rs.getString("CourseEnrolled"));
            studentDetails.setDateEnrolled(rs.getDate("DateEnrolled"));
            studentDetails.setEmail(rs.getString("Email"));
            studentDetails.setPassword(rs.getString("Password"));
            studentDetails.setCurrentSemester(rs.getInt("semester"));
            studentDetails.setAcademicYear(rs.getInt("level"));

            con.close();

            return studentDetails;
        } catch (SQLException e) {
            e.printStackTrace();
            new Exc("Error While Reading Data From the Database");

            return null;
        }
    }

    /**
     * This function is implemented from StudentInterface which is
     * used to get generate ID for Student
     * returns int ID for the Student to be assigned
     */
    @Override
    public int generateStudentID() {
        try {
            int id = 0;
            Connection con = getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(Student_Id) AS id FROM Student");

            rs.next();

            id = rs.getInt("id") + 1;

            con.close();

            return id;
        } catch (SQLException e) {
            return 0;
        }
    }


    /**
     * This function is implemented from StudentInterface which is
     * used to get enrolled module of a student

     * returns ArrayList which contains the array of enrolled module
     */
    @Override
    public ArrayList<String> enrolledModule(StudentDetails stdDetails) {

        Connection con = getConnection();

        try {
            ArrayList<String> module= new ArrayList<String>();

            PreparedStatement st = con.prepareStatement("SELECT module_code FROM Student_Enrollment WHERE student_id=?");
            st.setInt(1, stdDetails.getStdId());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                module.add(rs.getString(1));
            }

            con.close();

            return module;
        } catch (SQLException e) {
            e.printStackTrace();
            new Exc("Error While Reading Data From the Database");

            return null;
        }
    }
    @Override
    public String getCourse(StudentDetails stdDetails) {

        Connection con = getConnection();

        try {
            String course = null;

            PreparedStatement st = con.prepareStatement("SELECT CourseEnrolled FROM Student WHERE Student_Id=?");
            st.setInt(1, stdDetails.getStdId());

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                course = rs.getString("CourseEnrolled");
            }

            con.close();

            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            new Exc("Error While Reading Data From the Database");

            return null;
        }
    }

}

