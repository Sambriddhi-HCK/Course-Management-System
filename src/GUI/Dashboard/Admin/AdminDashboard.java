package GUI.Dashboard.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Customs.CustomTable;
import Database.AdminTableModels;
import exceptions.Exc;
import Backend.Courses;

public class AdminDashboard extends JDialog {

    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel pnlLogout;
    private JLabel logout;
    private JPanel title;
    private JLabel pageTitle;
    private JLabel Icon;
    private JPanel InrTop;
    private JPanel pnlIcon;
    private JPanel pnlCenter;
    private JPanel InrNorth;
    private JLabel Students;
    private JLabel Tutors;
    private JLabel Courses;
    private JLabel Modules;
    private JLabel Report;
    private JPanel pnlCard;
    private JPanel pnlStudent;
    private JPanel pnlTutor;
    private JPanel pnlCourse;
    private JPanel pnlModule;
    private JPanel pnlReport;
    private JPanel pnlStdCRUD;
    private JPanel pnlStdCenter;
    private JPanel pnlTutorCRUD;
    private JPanel pnlCourseCenter;
    private JPanel pnlModuleCenter;
    private JPanel pnlReportCenter;
    private JButton btnAddTutor;
    private JButton btnUpdate;
    private JTextArea pnlStudentInfo;
    private JPanel pnlStdInfo;
    private JPanel pnlTutorCenter;
    private JPanel pnlTutorButtons;
    private JButton btnRemoveTutor;
    private JButton btnDeleteTutor;
    private JPanel pnlReportCRUD;
    private JTextField txtSearchStdReport;
    private JButton btnSearch;
    private JLabel prompt;
    private JPanel pnlCourseCRUD;
    private JTextArea pnlTutorPrompt;
    private JPanel space;
    private JPanel pnlTInfo;
    private JPanel pnlCourseButtons;
    private JPanel pnlCInfo;
    private JTextArea pnlCoursesPrompt;
    private JPanel pnlCSpace;
    private JButton btnAddCourses;
    private JButton btnDeleteCourse;
    private JButton btnCancelCourses;
    private JPanel pnlModuleCRUD;
    private JPanel pnlModuleInfo;
    private JPanel pnlModuleSpace;
    private JPanel pnlModuleButtons;
    private JButton pnlModuleAdd;
    private JButton pnlModuleDelete;

    public AdminDashboard(JFrame parent) {

        super(parent);
        setContentPane(pnlMain);
        setTitle("Admin Dashboard");
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Students.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCard.removeAll();
                pnlCard.add(pnlStudent);
                setTitle("Admin - Students");
                pnlCard.repaint();
                pnlCard.revalidate();
            }
        });
        Tutors.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCard.removeAll();
                pnlCard.add(pnlTutor);
                setTitle("Admin - Tutors");
                pnlCard.repaint();
                pnlCard.revalidate();

            }
        });
        Courses.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCard.removeAll();
                pnlCard.add(pnlCourse);
                setTitle("Admin - Courses");
                pnlCard.repaint();
                pnlCard.revalidate();
            }
        });
        Modules.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCard.removeAll();
                pnlCard.add(pnlModule);
                setTitle("Admin - Modules");
                pnlCard.repaint();
                pnlCard.revalidate();
            }
        });
        Report.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCard.removeAll();
                pnlCard.add(pnlReport);
                setTitle("Admin - Report");
                pnlCard.repaint();
                pnlCard.revalidate();
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
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = txtSearchStdReport.getText();

                if (search.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a student ID");
                } else {
                    AdminTableModels adminTableModels = new AdminTableModels();
                    CustomTable report_table = new CustomTable();
                    report_table.setModel(adminTableModels.getSelectedStudentReportTableModel(Integer.parseInt(search)));
                    pnlReportCenter.removeAll();
                    pnlReportCenter.add(new JScrollPane(report_table));
                    pnlReportCenter.repaint();
                    pnlReportCenter.revalidate();
                }
            }
        });

        AdminTableModels adminTableModels = new AdminTableModels();

        btnDeleteCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomTable course_table = (CustomTable) ((JScrollPane) pnlCourseCenter.getComponent(0)).getViewport().getView();
                int row = course_table.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null,"Please Select A Course from Above To Delete.");
                    return;
                }
                String courseCode = (String) course_table.getValueAt(row, 0);

                int result = JOptionPane.showConfirmDialog(null, "Confirm Delete?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    if(new Courses().removeCourse(courseCode)) {
                        new Exc("Successfully Deleted");
                    } else {
                        new Exc("Error Occurred While Deleting Data");

                    }
                }
                course_table.setModel(adminTableModels.getCourseDefaultTableModel());
            }
        });
        btnAddCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCourse(null).setVisible(true);
            }
        });
        btnCancelCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomTable course_table = (CustomTable) ((JScrollPane) pnlCourseCenter.getComponent(0)).getViewport().getView();
                int row = course_table.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null,"Please Select A Course from Above To Change the Availability.");
                    return;
                }
                String courseCode = (String) course_table.getValueAt(row, 0);

                int result = JOptionPane.showConfirmDialog(null, "Confirm Change?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    if(new Courses().setCourseAvailability(courseCode)) {
                        new Exc("Availability Successfully Changed");
                    } else {
                        new Exc("Error Occurred While Changing Availability");

                    }
                }
                course_table.setModel(adminTableModels.getCourseDefaultTableModel());
            }
        });


        btnAddTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTutor(null);
            }
        });

        btnDeleteTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomTable tutor_table = (CustomTable) ((JScrollPane) pnlTutorCenter.getComponent(0)).getViewport().getView();
                int row = tutor_table.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(null,"Please Select A Tutor from Above To Delete.");
                    return;
                }
                int tutorID = (int) tutor_table.getValueAt(row, 0);

                int result = JOptionPane.showConfirmDialog(null, "Confirm Delete?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    if(new Backend.Tutors().deleteTutor(tutorID)) {
                        new Exc("Successfully Deleted");
                    } else {
                        new Exc("Error Occurred While Deleting Data");

                    }
                }
                tutor_table.setModel(adminTableModels.getTutorDefaultTableModel());
            }
        });
        pnlModuleAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddModules(null);

            }
        });
    }

    public static void main(String[] args) {
        new AdminDashboard(new JFrame());
    }

    private void createUIComponents() {
        AdminTableModels adminTableModels = new AdminTableModels();

        pnlStdCenter = new JPanel();
        CustomTable student_table = new CustomTable();
        student_table.setModel(adminTableModels.getStudentDefaultTableModel());
        pnlStdCenter.add(new JScrollPane(student_table));

        pnlTutorCenter = new JPanel();
        CustomTable tutor_table = new CustomTable();
        tutor_table.setModel(adminTableModels.getTutorDefaultTableModel());
        pnlTutorCenter.add(new JScrollPane(tutor_table));

        pnlCourseCenter = new JPanel();
        CustomTable course_table = new CustomTable();
        course_table.setModel(adminTableModels.getCourseDefaultTableModel());
        pnlCourseCenter.add(new JScrollPane(course_table));

        pnlModuleCenter = new JPanel();
        CustomTable module_table = new CustomTable();
        module_table.setModel(adminTableModels.getModuleDefaultTableModel());
        pnlModuleCenter.add(new JScrollPane(module_table));

        pnlReportCenter = new JPanel();
        CustomTable report_table = new CustomTable();
        report_table.setModel(adminTableModels.getReportDefaultTableModel());
        pnlReportCenter.add(new JScrollPane(report_table));
    }
}
