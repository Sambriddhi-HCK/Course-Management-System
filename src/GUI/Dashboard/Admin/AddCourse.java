package GUI.Dashboard.Admin;

import Backend.Details.CourseDetails;
import Backend.Courses;
import exceptions.Exc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCourse extends JDialog {


    private JPanel pnlMain;
    private JPanel pnlSouth;
    private JPanel pnlCenter;
    private JPanel pnlNorth;
    private JPanel InrTop;
    private JLabel logout;
    private JPanel title;
    private JLabel pageTitle;
    private JPanel pnlIcon;
    private JLabel Icon;
    private JPanel pnlButtons;
    private JButton btnAdd;
    private JButton btnCancelFromCourse;
    private JPanel pnlEntryBox;
    private JPanel labels;
    private JPanel entries;
    private JTextField txtCCode;
    private JTextField txtCName;
    private JTextField txtCTotalModules;
    private JTextField txtCTotalSems;
    private JTextField txtCCourseLength;
    private JLabel CCode;
    private JLabel CName;
    private JLabel CTotalModules;
    private JCheckBox txtCAvailability;
    private JLabel CTotalSems;
    private JLabel CCourseLength;
    private JLabel CAvailability;

    public AddCourse(JFrame parent) {
        super(parent, "Add Course", true);
        setContentPane(pnlMain);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        System.out.println("dguvhbijnkm");


        btnCancelFromCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminDashboard(null).setVisible(true);
            }
        });


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("asgvhbkjdc");
                String cCode = txtCCode.getText();
                String cName = txtCName.getText();
                String cTotalModules = txtCTotalModules.getText();
                String cTotalSems = txtCTotalSems.getText();
                String cCourseLength = txtCCourseLength.getText();
                boolean cAvailability = txtCAvailability.isSelected() ;

                if (cCode.isEmpty() || cName.isEmpty() || cTotalModules.isEmpty() || cTotalSems.isEmpty() || cCourseLength.isEmpty() ) {
                    JOptionPane.showMessageDialog(null, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Add course to database
                    CourseDetails course = new CourseDetails();
                    course.setCourseCode(cCode);
                    course.setCourseName(cName);
                    course.setTotalModule(Integer.parseInt(cTotalModules));
                    course.setTotalSemester(Integer.parseInt(cTotalSems));
                    course.setCourseLength(Integer.parseInt(cCourseLength));
                    course.setAvailability(cAvailability);
                    Courses courses = new Courses();

                    boolean execute = courses.createCourse(course);
                    if (execute) {
                        JOptionPane.showMessageDialog(null, "Course added successfully");

                    } else {
                        new Exc("Error Occurred While Adding Course");
                    }
                    new AdminDashboard(null).setVisible(true);
                }
            }
        });
    }

public static void main(String[] args) {
        new AddCourse(null).setVisible(true);
    }
}
