package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import Backend.Details.TutorDetails;
import Backend.Interfaces.TutorInterface;
import Database.DBConnection;
import exceptions.DuplicateTutorModuleRegistrationException;
import exceptions.TutorModuleCountLimitException;
import exceptions.Exc;

/**
 * This class implements the TutorInterface where methods are predefined and
 * inherits the DBConnection for Database Connectivity

 * This class contains all the methods required for CRUD functionality of Tutor
 */
public class Tutors extends DBConnection implements TutorInterface {

    /**
     * This function is implemented from TutorInterface Interface
     * which is used to Create Tutor

     * Details of the Tutor are Stored in the TutorDetailClass

     * returns true if Tutor is successfully added to Database
     * 			returns false if error occurs
     */
    @Override
    public boolean createTutor(TutorDetails t) {
        Connection con = getConnection();

        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM Tutor WHERE email=?");

            st.setInt(1, t.getTutId());

            ResultSet rSet = st.executeQuery();
            rSet.next();

            if (rSet.isLast()) {
                throw new SQLIntegrityConstraintViolationException();
            }

            st = con.prepareStatement("INSERT INTO Tutor "
                    + "(firstName, middleName, lastName, phoneNumber, email, password) "
                    + "Values (?, ?, ?, ?, ?)");

            st.setInt(1, t.getTutId());
            st.setString(2, t.getFirstName());
            st.setString(3, t.getLastName());
            st.setString(4, t.getPhoneNumber());
            st.setString(5, t.getEmail());
            st.executeUpdate();

            con.close();

            return true;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                e.printStackTrace();
                new Exc("Email is Already in Use");
            } else {
                new Exc(null, e);
            }
            return false;
        }
    }

    /**
     * This function is implemented from TutorInterface which is
     * used to Update Tutor Information

     * Details of the Tutor are Stored in the TutorDetailClass
     * new id to be assigned as String

     * returns true if Tutor Information is successfully updated in Database
     * 	returns false if error occurs
     */
    @Override
    public boolean updateTutor(TutorDetails t) {
        try {
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement("SELECT * FROM Tutor WHERE Email=?");

            st.setString(1, t.getEmail());

            ResultSet rSet = st.executeQuery();
            rSet.next();

            if (rSet.isLast()) {
                throw new SQLIntegrityConstraintViolationException("Email is Already Registered...");
            }

            st = con.prepareStatement("UPDATE Tutor SET "
                    + "firstName=?, lastName=? "
                    + "WHERE id=?");

            st.setString(1, t.getFirstName());
            st.setString(2, t.getLastName());
            st.setInt(3, t.getTutId());

            st.executeUpdate();

            PreparedStatement anotherSt = con.prepareStatement("UPDATE Tutor SET email=? "
                    + "WHERE id=?");

            anotherSt.setString(1, t.getEmail());
            anotherSt.setInt(2, t.getTutId());

            anotherSt.execute();

            st = con.prepareStatement("UPDATE `tutor_enrollment` SET "
                    + "`module_code`=? WHERE `tutor_id`=?");

            st.setString(1, t.getModules().get(0));
            st.setInt(2, t.getTutId());

            con.close();

            return true;
        } catch (SQLException e) {
            new Exc("Error updating Tutor information...", e);

            return false;
        }
    }

    /**
     * This function is implemented from TutorInterface which is
     * used to Remove Tutor

     * ID of the Tutor to be removed

     * returns true if Tutor is successfully Deleted from Database
     * 			returns false if error occurs
     */
    @Override
    public boolean deleteTutor(int tutID) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("DELETE FROM Tutor WHERE id=?");
            ps.setInt(1, tutID);
            ps.execute();

            con.close();

            return true;
        } catch (Exception e) {
            new Exc("Error Occurred While Deleting Tutor Details", e);
            return false;
        }
    }

    /**
     * This function is implemented from TutorInterface which is
     * used to enroll Certain Tutor to a Module

     * returns true if Module is successfully enrolled
     * false if error occurs
     */
    @Override
    public boolean enrollModule(TutorDetails tutorDetails) {
        Connection con = getConnection();
        try {
            PreparedStatement st = con.prepareStatement("SELECT module_code FROM `tutor_enrollment` WHERE `tutor_id`=?");
            st.setInt(1, tutorDetails.getTutId());

            ResultSet rs = st.executeQuery();

            int registerCount = 0;

            while(rs.next()) {
                registerCount++;
            }

            if (registerCount >= 4) {
                throw new TutorModuleCountLimitException();
            }

            st = con.prepareStatement("SELECT module_code FROM tutor_enrollment WHERE tutor_id=? AND module_code=?");
            st.setInt(1, tutorDetails.getTutId());
            st.setString(2, tutorDetails.getModules().get(1));

            rs = st.executeQuery();

            if(rs.next()) {
                throw new DuplicateTutorModuleRegistrationException();
            }

            st = con.prepareStatement("INSERT INTO `tutor_enrollment`(`tutor_id`, `module_code`) "
                    + "VALUES (?, ?)");

            st.setInt(1, tutorDetails.getTutId());
            st.setString(2, tutorDetails.getModules().get(2));

            st.execute();

            con.close();

            return true;
        } catch (SQLException | TutorModuleCountLimitException | DuplicateTutorModuleRegistrationException e) {
            if (e instanceof SQLException) {
                new Exc("Error Occurred While Registering Tutor Details");
            } else {
                new Exc(e.getMessage());
            }
            return false;
        }
    }

    @Override
    public TutorDetails getTutorDetails(int tutID) {
        try {
            ArrayList<String> modules = new ArrayList<String>();

            TutorDetails tutorDetails= new TutorDetails();
            Connection con = getConnection();

            PreparedStatement stmt = con.prepareStatement("SELECT Tutor.firstName, Tutor.middleName, Tutor.lastName, Tutor.phoneNumber, Tutor.email, "
                    + " tutor_enrollment.module_code, Tutor.password FROM Tutor "
                    + "INNER JOIN tutor_enrollment ON Tutor.id=tutor_enrollment.tutor_id "
                    + "WHERE Tutor.id=? ");

            stmt.setInt(1, tutID);

            ResultSet rs = stmt.executeQuery();

            rs.next();
            tutorDetails.setTutId(tutID);
            tutorDetails.setFirstName(rs.getString("firstName"));
            tutorDetails.setMiddleName(rs.getString("middleName"));
            tutorDetails.setLastName(rs.getString("lastName"));
            tutorDetails.setPhoneNumber(rs.getString("phoneNumber"));
            tutorDetails.setEmail(rs.getString("email"));
            modules.add(rs.getString("module_code"));
            tutorDetails.setPassword(rs.getString("password"));

            while (rs.next()) {
                modules.add(rs.getString("module_code"));
            }
            tutorDetails.setModules(modules);

            con.close();

            return tutorDetails;
        } catch (SQLException e) {
            new Exc("Error Occurred While Appending Data to Database.", e);

            return null;
        }
    }


    /**
     * This function is implemented from TutorInterface which is
     * used to get assigned modules for the entered tutID
     */

    public ArrayList<String> getAssignedModules(int tutID) {
        try {
            ArrayList<String> modules = new ArrayList<String>();

            Connection con = getConnection();

            PreparedStatement stmt = con.prepareStatement("SELECT module_code FROM tutor_enrollment WHERE tutor_id=?");

            stmt.setInt(1, tutID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                modules.add(rs.getString("module_code"));
            }

            con.close();

            return modules;
        } catch (SQLException e) {
            new Exc("Error Occurred While Appending Data to Database.", e);

            return null;
        }
    }



    /**
     * This function is implemented from TutorInterface which is
     * used to get generate ID for Tutor

     * returns int ID for the Tutor to be assigned
     */
    @Override
    public int generateTutorID() {
        Connection con = getConnection();

        try {

            PreparedStatement stmt = con.prepareStatement("SELECT MAX(id) AS id FROM Tutor");

            ResultSet rs = stmt.executeQuery();

            rs.next();
            int tutorID = rs.getInt("id") + 1;

            con.close();

            return tutorID;
        } catch (SQLException e) {
            new Exc("Error Occurred While Generating ID.", e);

            return 0;
        }
    }


    /**
     * This function is implemented from TutorInterface which is
     * used to get generate ID for Tutor

     * returns int ID for the Tutor to be assigned
     */
    @Override
    public boolean assignMarks(float mark, int stdId, String moduleCode) {
        Connection con = getConnection();

        try {

            PreparedStatement stmt = con.prepareStatement("UPDATE Student_Enrollment SET final_grade=? WHERE student_id=? AND module_code=?");
            stmt.setFloat(1, mark);
            stmt.setInt(2, stdId);
            stmt.setString(3, moduleCode);

            stmt.executeUpdate();

            con.close();

            return true;
        } catch (SQLException e) {
            new Exc("Error Occurred While Assigning Mark", e);

            return false;
        }
    }
}