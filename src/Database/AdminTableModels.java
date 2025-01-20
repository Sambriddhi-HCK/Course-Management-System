package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import exceptions.Exc;

/**
 * This class contains the methods of getting the TableModel for the JTable
*/
public class AdminTableModels extends DBConnection {

    /**
     * This method is used to get the Table Model of Course for Admin View
     */
    public DefaultTableModel getCourseDefaultTableModel() {
        int columnLength;

        DefaultTableModel courseTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Setting the Table Header
        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("Course Code", "Course Name", "Total Modules", "Total Semester", "Course Length (Months)", "Availability"));

        courseTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT * FROM courses");

            ResultSetMetaData rsmd = st.getMetaData();

            // counting the column
            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            // While ResultSet contains value append the data to the Table Model
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
                new Exc("Error While Fetching Data from the Database");
            }
            // return null DefaultTableModel if error Occurs
            return new DefaultTableModel();
        }
    }

    /**
     * This is used to get the Table Model of Student for Admin View
     */
    public DefaultTableModel getStudentDefaultTableModel() {
        int columnLength;

        DefaultTableModel studentTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Setting the Table Header
        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("ID", "First Name","Middle Name", "Last Name", "Phone Number", "DOB", "Course Enrolled", "Date Enrolled", "Email" , "Level", "Semester"));

        studentTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT Student.*, StudentCreds.email, student_year.level, student_year.semester "
                    + "FROM Student "
                    + "INNER JOIN StudentCreds ON Student.Student_Id=StudentCreds.id INNER JOIN student_year ON Student.Student_Id=student_year.studentId");

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }

            // While ResultSet contains value append the data to the Table Model
            while(st.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(st.getObject(string));
                }

                studentTableModel.addRow(data.toArray());
            }

            return studentTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Error While Fetching Data from the Database");
            }
            return new DefaultTableModel();
        }
    }

    /**
     * This is used to get the Table Model of Tutor for Admin View
     */
    public DefaultTableModel getTutorDefaultTableModel() {
        int columnLength;

        DefaultTableModel tutorTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Setting the Table Header
        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("ID", "First Name", "Middle Name", "Last Name", "Date Joined", "Phone Number", "Email"));

        tutorTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT Tutor.id, Tutor.firstName, Tutor.middleName, Tutor.lastName, Tutor.dateJoined, Tutor.phoneNumber, Tutor.email FROM Tutor ");

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }
            // While ResultSet contains value append the data to the Table Model
            while(st.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(st.getObject(string));
                }

                tutorTableModel.addRow(data.toArray());
            }

            return tutorTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Error While Fetching Data from the Database");
            }
            // return null DefaultTableModel if error Occurs
            return new DefaultTableModel();
        }
    }

    /**
     * This is used to get the Table Model of Report for Admin View
     */
    public DefaultTableModel getReportDefaultTableModel() {
        int columnLength;

        DefaultTableModel reportTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Setting the Table Header
        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("ID", "First Name", "Middle Name", "Last Name", "Module", "Final Grade"));

        reportTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT Student.Student_Id, Student.Firstname, Student.Middlename,Student.Lastname, "
                    + "Student_Enrollment.module_code, Student_Enrollment.final_grade "
                    + "FROM Student "
                    + "INNER JOIN Student_Enrollment ON Student.Student_Id=Student_Enrollment.student_id");

            ResultSetMetaData rsmd = st.getMetaData();

            columnLength = rsmd.getColumnCount();
            String columnNames[] = new String[columnLength];

            for (int i = 0; i < columnLength; i++) {
                columnNames[i] =  rsmd.getColumnName(i+1);
            }
            // While ResultSet contains value append the data to the Table Model
            while(st.next()) {
                ArrayList<Object> data = new ArrayList<>();
                for (String string : columnNames) {
                    data.add(st.getObject(string));
                }

                reportTableModel.addRow(data.toArray());
            }

            return reportTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Error While Fetching Data from the Database");
            }
            // return null DefaultTableModel if error Occurs
            return new DefaultTableModel();
        }
    }

    /**
     * This is used to get the Table Model of Modules for Admin View
     */
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
            } else {
                new Exc("Error While Fetching Data from the Database");
            }
            // return null DefaultTableModel if error Occurs
            return new DefaultTableModel();
        }
    }

    /**
     * This is used to get the Table Model of Registered Tutors for Admin View
     */
    public DefaultTableModel getRegisteredTutor() {
        int columnLength;

        DefaultTableModel registeredTutorTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("ID", "First Name", "Last Name", "Module Code", "Enrollment Date"));

        registeredTutorTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT Tutor.id, Tutor.firstName, Tutor.lastName, tutor_enrollment.module_code "
                    + "FROM Tutor "
                    + "INNER JOIN tutor_enrollment ON Tutor.id=tutor_enrollment.tutor_id ");

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

                registeredTutorTableModel.addRow(data.toArray());
            }

            return registeredTutorTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Error While Fetching Data from the Database");
            }
            // return null DefaultTableModel if error Occurs
            return new DefaultTableModel();
        }
    }

    /**
     * This method displays report of the searched Student from entered student id for Admin View
     */

    public TableModel getSelectedStudentReportTableModel(int id) {
        int columnLength;

        DefaultTableModel reportTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Object> tableHeader = new ArrayList<>(
                Arrays.asList("ID", "First Name", "Middle Name", "Last Name", "Module", "Final Grade"));

        reportTableModel.setColumnIdentifiers(tableHeader.toArray());

        try {
            Connection con = getConnection();
            ResultSet st = con.createStatement().executeQuery("SELECT Student.Student_Id, Student.Firstname, Student.Middlename,Student.Lastname, "
                    + "Student_Enrollment.module_code, Student_Enrollment.final_grade "
                    + "FROM Student "
                    + "INNER JOIN Student_Enrollment ON Student.Student_Id=Student_Enrollment.student_id "
                    + "WHERE Student.Student_Id="+id);

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

                reportTableModel.addRow(data.toArray());
            }

            return reportTableModel;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                new Exc("Cannot Connect To the Server...", e);
            } else {
                new Exc("Error While Fetching Data from the Database");
            }
            return new DefaultTableModel();
        }
    }
}
