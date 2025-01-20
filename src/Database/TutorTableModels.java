package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import Backend.Details.TutorDetails;
import exceptions.Exc;

public class TutorTableModels extends DBConnection {

    public DefaultTableModel getCourseDefaultTableModel() {
        int columnLength;

        DefaultTableModel courseTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Course Code", "Course Name", "Total Modules", "Total Semester", "Course Length (Months)", "Availability"));

        courseTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT * FROM courses");

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            while(st.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(st.getObject(string));
                }

                courseTableModel.addRow(data.toArray());
            }

            return courseTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            }
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getStudentDefaultTableModel(TutorDetails t) {
        int columnLength;

        DefaultTableModel studentTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("First Name", "Middle Name","Last Name", "Email"));

        studentTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();

            CallableStatement st = con.prepareCall("SELECT Student.Firstname, Student.Middlename, Student.Lastname, StudentCreds.Email FROM Student "
                    + "INNER JOIN StudentCreds ON Student.Student_Id=StudentCreds.id INNER JOIN Student_Enrollment ON StudentCreds.id=Student_Enrollment.student_id "
                    + "WHERE Student_Enrollment.module_code in ( ?, ?, ?, ? ) " );

            st.setString(1, t.getModule1code());
            st.setString(2, t.getModule2code());
            st.setString(3, t.getModule3code());
            st.setString(4, t.getModule4code());
            System.out.println(t.getModule1code());
            System.out.println(t.getModule2code());
            System.out.println(t.getModule3code());
            System.out.println(t.getModule4code());
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                ArrayList<Object> data = new ArrayList<>();
                data.add(rs.getString("Firstname"));
                data.add(rs.getString("Middlename"));
                data.add(rs.getString("Lastname"));
                data.add(rs.getString("Email"));

                System.out.println(rs.getString("Firstname"));
                System.out.println(rs.getString("Middlename"));
                System.out.println(rs.getString("Lastname"));
                System.out.println(rs.getString("Email"));

                studentTableModel.addRow(data.toArray());
            }

            return studentTableModel;
        } catch (NullPointerException | SQLException e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            }
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getReportDefaultTableModel(TutorDetails t) {
        int columnLength;

        DefaultTableModel studentTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Student ID", "First Name", "Last Name", "Module Code", "Final Grade"));

        studentTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT Student.Student_Id, Student.Firstname, Student.Lastname, Student_Enrollment.module_code, Student_Enrollment.final_grade "
                    + "FROM Student "
                    + "INNER JOIN StudentCreds ON Student.id=StudentCreds.id "
                    + "INNER JOIN Student_Enrollment ON StudentCreds.id=Student_Enrollment.student_id "
                    + "WHERE Student_Enrollment.module_code='"+t.getModules()+"'");

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            while(st.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(st.getObject(string));
                }

                studentTableModel.addRow(data.toArray());
            }

            return studentTableModel;
        } catch (NullPointerException | SQLException e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            }
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getModuleDefaultTableModel() {
        int columnLength;

        DefaultTableModel moduleTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Module Code", "Module Name", "Module Type", "Semester", "Course Code", "Level", "Course Name"));

        moduleTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT modules.*, courses.courseName "
                    + "FROM modules INNER JOIN courses ON modules.courseId=courses.courseCode");

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            while(st.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(st.getObject(string));
                }

                moduleTableModel.addRow(data.toArray());
            }

            return moduleTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            }
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getEnrolleDefaultTableModel() {
        int columnLength;

        DefaultTableModel enrolledCourseTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Enrollment ID", "Module Code", "Course Code", "Course Name"));

        enrolledCourseTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT tutor_enrollment.enrollment_id, tutor_enrollment.module1_code, "
                    + "tutor_enrollment.module2_code, tutor_enrollment.module3_code, tutor_enrollment.module4_code, "
                    + "courses.courseCode, courses.courseName "
                    + "FROM tutor_enrollment "
                    + "INNER JOIN modules ON "
                    + "tutor_enrollment.module1_code=modules.moduleCode AND tutor_enrollment.module2_code=modules.moduleCode AND "
                    + "tutor_enrollment.module3_code=modules.moduleCode AND tutor_enrollment.module4_code=modules.moduleCode "
                    + "INNER JOIN courses ON modules.courseId=courses.courseCode");

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            while(st.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(st.getObject(string));
                }

                enrolledCourseTableModel.addRow(data.toArray());
            }

            return enrolledCourseTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            }
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

}
