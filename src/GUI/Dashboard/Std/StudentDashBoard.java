package GUI.Dashboard.Std;

import Backend.Details.StudentDetails;
import Customs.CustomTable;
import Database.AdminTableModels;
import Database.StudentTableModels;
import Backend.Students;
import exceptions.Exc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StudentDashBoard  extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel pnlCenter;
    private JLabel Icon;
    private JPanel title;
    private JLabel pageTitle;
    private JLabel logout;
    private JPanel pnlLogout;
    private JPanel pnlModule;
    private JPanel InrTop;
    private JPanel pnlIcon;
    private JPanel pnlInrNorth;
    private JPanel pnlCards;
    private JPanel InrNorth;
    private JLabel Tutors;
    private JLabel Courses;
    private JLabel Modules;
    private JLabel Report;
    private JPanel pnlTutor;
    private JPanel pnlTutorCRUD;
    private JPanel pnlTutorButtons;
    private JPanel pnlTInfo;
    private JTextArea pnlTutorPrompt;
    private JPanel pnlTutorCenter;
    private JPanel pnlCourse;
    private JPanel pnlCourseCenter;
    private JPanel pnlCourseCRUD;
    private JPanel pnlCInfo;
    private JPanel pnlModuleCRUD;
    private JPanel pnlModuleInfo;
    private JPanel pnlModuleSpace;
    private JPanel pnlModuleButtons;
    private JButton btnModuleAddToStd;
    private JPanel pnlModuleCenter;
    private JPanel pnlReport;
    private JPanel pnlReportCenter;
    private JLabel pnlEnrolledCourse;
    private JLabel Info;
    private JTextArea ModulesInfoStd;
    private int id;

    public StudentDashBoard(JFrame parent, int id) {
        super(parent);
        this.id = id;
        setContentPane(pnlMain);
        setTitle("Student Dashboard");
        setMinimumSize(new Dimension(1000,700));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Tutors.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCards.removeAll();
                pnlCards.add(pnlTutor);
                setTitle("Student - Tutors");
                pnlCards.repaint();
                pnlCards.revalidate();

            }
        });
        Courses.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCards.removeAll();
                pnlCards.add(pnlCourse);
                setTitle("Student - Courses");
                pnlCards.repaint();
                pnlCards.revalidate();
            }
        });
        Modules.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCards.removeAll();
                pnlCards.add(pnlModule);
                setTitle("Student - Modules");
                pnlCards.repaint();
                pnlCards.revalidate();
            }
        });
        Report.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCards.removeAll();
                pnlCards.add(pnlReport);
                setTitle("Student - Report");
                pnlCards.repaint();
                pnlCards.revalidate();

                AdminTableModels adminTableModels = new AdminTableModels();
                CustomTable report_table = new CustomTable();
                report_table.setModel(adminTableModels.getSelectedStudentReportTableModel(id));
                pnlReportCenter.removeAll();
                pnlReportCenter.add(new JScrollPane(report_table));
                pnlReportCenter.repaint();
                pnlReportCenter.revalidate();
            }
        });
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new GUI.WelcomePage.Login(new JFrame());
            }
        });

        btnModuleAddToStd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomTable course_table = (CustomTable) ((JScrollPane) pnlModule.getComponent(0)).getViewport().getView();
                StudentDetails studentDetails = new Students().getStudentDetails(id);
                StudentTableModels studentTableModels = new StudentTableModels();

                int row = course_table.getSelectedRow();
                if (row < 0) {
                    new Exc("Please Select From the Enrollable Module Table");
                    return;
                }
                String selectedCourse = (String) course_table.getValueAt(row, 0);

                new Students().enrollModule(studentDetails, selectedCourse);

                course_table.setModel(studentTableModels.getEnrolledCourseTableModel(studentDetails));
            }
        });
    }

    private void createUIComponents() {
        StudentDetails studentDetails = new Students().getStudentDetails(this.id);
        StudentTableModels studentTableModels = new StudentTableModels();

        ArrayList<String> modulesEnrolled = new Students().enrolledModule(studentDetails);
        String course = new Students().getCourse(studentDetails);

        pnlTutorCenter = new JPanel();
        CustomTable tutor_table = new CustomTable();
        tutor_table.setModel(studentTableModels.getTutorDefaultTableModel(modulesEnrolled));
        pnlTutorCenter.add(new JScrollPane(tutor_table));

        pnlCourseCenter = new JPanel();
        CustomTable course_table = new CustomTable();
        course_table.setModel(studentTableModels.getCourseDefaultTableModel());
        pnlCourseCenter.add(new JScrollPane(course_table));

        pnlEnrolledCourse = new JLabel();
        pnlEnrolledCourse.add(new JLabel(course));

        pnlModuleCenter = new JPanel();
        CustomTable module_table = new CustomTable();
        module_table.setModel(studentTableModels.getEnrollableCourseTableModel(studentDetails));
        pnlModuleCenter.add(new JScrollPane(module_table));

        pnlReportCenter = new JPanel();
        CustomTable report_table = new CustomTable();
        report_table.setModel(studentTableModels.getReportDefaultTableModel(studentDetails));
        pnlReportCenter.add(new JScrollPane(report_table));
    }
}
