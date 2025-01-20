package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import Backend.Details.StudentDetails;
import exceptions.Exc;

/**
 * tables related to student
 *
 */
public class StudentTableModels extends DBConnection {

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
            } else {
                new Exc("Failed To Fetch Data From the Database");
            }
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getTutorDefaultTableModel(ArrayList<String> enrolledModule) {
        int columnLength;

        DefaultTableModel tutorTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("First Name", "Last Name", "Module", "Email"));

        tutorTableModel.setColumnIdentifiers(tableHeader.toArray());
        Connection con = getConnection();

        try {
            PreparedStatement st = con.prepareStatement("SELECT Tutor.firstName, Tutor.lastName, tutor_enrollment.module_code, Tutor.email FROM Tutor INNER JOIN tutor_enrollment ON Tutor.id=tutor_enrollment.tutor_id " +
                    "WHERE tutor_enrollment.module_code= ?");

            for (String moduleCode : enrolledModule) {
                st.setString(1, moduleCode);

                ResultSet rs = st.executeQuery();

                ResultSetMetaData rsmd = rs.getMetaData();

                columnLength = rsmd.getColumnCount();
                String columnNames[] = new String[columnLength];

                for (int i = 0; i < columnLength; i++) {
                    columnNames[i] =  rsmd.getColumnName(i+1);
                }

                while(rs.next()) {
                    ArrayList<Object> data = new ArrayList<>();
                    for (String string : columnNames) {
                        data.add(rs.getObject(string));
                    }
                    tutorTableModel.addRow(data.toArray());
                }
            }

            return tutorTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Failed To Fetch Data From the Database");
            }
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getReportDefaultTableModel(StudentDetails studentDetails) {
        int columnLength;

        DefaultTableModel moduleTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Module Code", "Module Name", "Module Type", "Semester", "Level", "Marks"));

        moduleTableModel.setColumnIdentifiers(tableHeader.toArray());

        Connection con = getConnection();
        try {
            PreparedStatement st = con.prepareStatement("SELECT Student_Enrollment.module_code, modules.moduleName, modules.moduleType, modules.semester, modules.level"
                    + "Student_Enrollment.final_grade "
                    + "FROM Student_Enrollment "
                    + "INNER JOIN modules ON Student_Enrollment.module_code=modules.moduleCode "
                    + "WHERE Student_Enrollment.student_id=?");
            st.setInt(1, studentDetails.getStdId());

            ResultSet rs = st.executeQuery();

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            while(rs.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    if (string.equals("final_grade") && rs.getObject(string) == null) {
                        data.add("Not Graded Yet");
                    } else {
                        data.add(rs.getObject(string));
                    }
                }

                moduleTableModel.addRow(data.toArray());
            }

            return moduleTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Failed To Fetch Data From the Database");
            }
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getModuleDefaultTableModel(StudentDetails studentDetails) {
        int columnLength;

        DefaultTableModel moduleTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Module Code", "Module Name", "Module Type", "Semester", "Level","Course Code", "Course Name"));

        moduleTableModel.setColumnIdentifiers(tableHeader.toArray());
        Connection con = getConnection();

        try {
            ResultSet st = con.createStatement().executeQuery("SELECT modules.*, courses.courseName FROM modules INNER JOIN courses ON modules.courseId=courses.courseCode WHERE modules.courseId='"+studentDetails.getCourseEnrolled()+"'");

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
            } else {
                new Exc("Failed To Fetch Data From the Database");
            }
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getEnrolledCourseTableModel(StudentDetails studentDetails) {
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

        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT Student_Enrollment.enrollment_id, Student_Enrollment.module_code, "
                    + "courses.courseCode, courses.courseName "
                    + "FROM Student_Enrollment "
                    + "INNER JOIN modules ON Student_Enrollment.module_code=modules.moduleCode "
                    + "INNER JOIN courses ON modules.courseId=courses.courseCode "
                    + "INNER JOIN student_year ON student_year.studentId=student_enrollment.student_id "
                    + "WHERE Student_Enrollment.student_id=?");
            ps.setInt(1, studentDetails.getStdId());

            ResultSet st = ps.executeQuery();

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
            } else {
                new Exc("Failed To Fetch Data From the Database");
            }
            return new DefaultTableModel();
        }
    }

    public DefaultTableModel getEnrollableCourseTableModel(StudentDetails studentDetails) {
        int columnLength;

        DefaultTableModel enrollableModuleTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Module Code", "Module Name"));

        enrollableModuleTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();

            PreparedStatement st = con.prepareStatement("SELECT moduleCode, moduleName FROM modules WHERE courseId=? AND semester=?");
            st.setString(1, studentDetails.getCourseEnrolled());
            st.setInt(2, studentDetails.getCurrentSemester());


            ResultSet rs = st.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            while(rs.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(rs.getObject(string));
                }

                enrollableModuleTableModel.addRow(data.toArray());
            }

            return enrollableModuleTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Failed To Fetch Data From the Database");
            }
            return new DefaultTableModel();
        }
    }

}
