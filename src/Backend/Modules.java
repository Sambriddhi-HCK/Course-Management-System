package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;

import Backend.Details.ModuleDetails;
import Backend.Interfaces.ModuleInterface;
import Database.DBConnection;
import exceptions.Exc;

/**
 * This class file implements the CourseInterface where methods are predefined and
 * inherits the DBConnection for Database Connectivity

 * This Class contains all the methods required for CRUD functionality of Module
 */
public class Modules extends DBConnection implements ModuleInterface {

    /**
     * This function is implemented from CourseInterface Interface
     * which is used to Create Module

     *Details of the Module Stored in the Course Detail Class

     * returns true if module is successfully added to Database
     * 			returns false if error occurs
     */
    @Override
    public boolean createModule(ModuleDetails module) {
        Connection con = getConnection();

        try {

            PreparedStatement st = con.prepareStatement("INSERT INTO modules "
                    + "(`moduleCode`, `moduleName`, `moduleType`, `semester`, `courseId`, `level`)"
                    + "VALUES (?, ?, ?, ?, ?,?)");

            st.setString(1, module.getModuleCode());
            st.setString(2, module.getModuleName());
            st.setString(3, module.getModuleType());
            st.setInt(4, module.getSemester());
            st.setString(5, module.getCourseField());
            st.setString(5, module.getLevel());

            st.executeUpdate();

            con.close();

            return true;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                e.printStackTrace();
                new Exc("Module is Already Registered in Database.");
            } else {
                new Exc("Error Occurred While Adding Module in Database.", e);
            }

            return false;
        }
    }

    /**
     * This function is implemented from CourseInterface which is
     * used to Update Module
     *Details of the Module Stored in the Course Detail Class
     *new id to be assigned as String

     * returns true if Module is successfully updated in Database
     * 			returns false if error occurs
     */
    @Override
    public boolean updateModule(ModuleDetails module, String newCode) {
        try {
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement("UPDATE modules SET "
                    + "`moduleCode`=?, `moduleName`=?, `moduleType`=?, `semester`=?, `courseId`=? "
                    + "WHERE moduleCode=?");

            st.setString(1, newCode);
            st.setString(2, module.getModuleName());
            st.setString(3, module.getModuleType());
            st.setInt(4, module.getSemester());
            st.setString(5, module.getCourseField());
            st.setString(6, module.getModuleCode());

            st.executeUpdate();

            con.close();

            return true;
        } catch (SQLException e) {
            new Exc(null, e);

            return false;
        }
    }

    /**
     * This function is implemented from CourseInterface which is
     * used to Remove Module
     *ID of the Module to be removed
     *returns true if Module is successfully Deleted from Database
     * 			returns false if error occurs
     */
    @Override
    public boolean removeModule(String moduleCode) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement("DELETE FROM modules WHERE moduleCode=?");
            ps.setString(1, moduleCode);
            ps.execute();

            con.close();

            return true;
        } catch (Exception e) {
            new Exc("Error Occurred While Deleting Module", e);
            return false;
        }
    }

    /**
     * This function is implemented from CourseInterface which is
     * used to get all existing Module from database
     *
     *
     * @returns HashMap where Module name is set as the key and
     * 			Module code is set as the value
     */
    @Override
    public HashMap<String, String> moduleList() {
        Connection con = getConnection();

        try {
            HashMap<String, String> modules= new HashMap<>();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT moduleCode, moduleName FROM modules");

            while(rs.next()) {
                modules.put(
                        rs.getString("moduleName"),
                        rs.getString("moduleCode")
                );
            }

            con.close();

            return modules;
        } catch (SQLException e) {
            e.printStackTrace();
            new Exc("Error While Reading Data From the Database");

            return null;
        }
    }

    /**
     * This function is implemented from CourseInterface which is
     * used to get Details of existing Module of database
     *
     *
     * returns Details of the Module stored in the CourseDetails class
     */
    @Override
    public ModuleDetails getModulesDetails(String moduleCode) throws NullPointerException {
        Connection con = getConnection();
        try {
            ModuleDetails moduleDetails = new ModuleDetails();
            PreparedStatement stmt = con.prepareStatement("SELECT modules.*, courses.courseName "
                    + "FROM modules "
                    + "INNER JOIN courses ON modules.courseId=courses.courseCode WHERE moduleCode=?");
            stmt.setString(1, moduleCode);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            moduleDetails.setModuleCode(rs.getString("moduleCode"));
            moduleDetails.setModuleName(rs.getString("moduleName"));
            moduleDetails.setModuleType(rs.getString("moduleType"));
            moduleDetails.setSemester(rs.getInt("semester"));
            moduleDetails.setCourseField(rs.getString("courseField"));
            moduleDetails.setCourseFieldName(rs.getString("courseName"));

            con.close();

            return moduleDetails;
        } catch (SQLException e) {
            new Exc("Error While Reading Module Details");
            return null;
        }
    }

}