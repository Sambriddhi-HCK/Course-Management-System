package GUI.Dashboard.Tutor;

import Backend.Details.TutorDetails;
import Backend.Tutors;
import Customs.CustomTable;
import Database.TutorTableModels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TeacherDashBoard extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlSouth;
    private JPanel pnlNorth;
    private JPanel InrTop;
    private JPanel pnlLogout;
    private JPanel title;

    private JLabel logout;
    private JLabel pageTitle;
    private JPanel pnlIcon;
    private JLabel Icon;
    private JPanel pnlCenter;
    private JPanel InrNorth;
    private JPanel pnlCards;
    private JLabel Courses;
    private JLabel Modules;
    private JLabel Marks;
    private JPanel pnlCourses;
    private JPanel pnlModules;
    private JPanel pnlStudents;
    private JPanel pnlCoursesInfo;
    private JPanel pnlCoursesCenter;
    private JPanel pnlModulesCenter;
    private JPanel pnlModulesCRUD;
    private JPanel pnlInfo;
    private JTextArea Info;
    private JTabbedPane tabbedPane;
    private JPanel module1;
    private JPanel module2;
    private JPanel module3;
    private JPanel module4;
    private JPanel pnlStudentCenter;
    private int tutId;

    public TeacherDashBoard(JFrame parent, int tutId) {

        super(parent);
        this.tutId = tutId;

        setContentPane(pnlMain);

        System.out.println(this.tutId);
        setTitle("Teacher Dashboard");
        setMinimumSize(new Dimension(1000,700));
        setLocationRelativeTo(parent);
        setVisible(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new GUI.WelcomePage.Login(new JFrame());
            }
        });
        Courses.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCards.removeAll();
                pnlCards.add(pnlCourses);
                setTitle("Teacher - Courses");
                pnlCards.repaint();
                pnlCards.revalidate();
            }
        });
        Modules.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pnlCards.removeAll();
                pnlCards.add(pnlModules);
                setTitle("Teacher - Modules");
                pnlCards.repaint();
                pnlCards.revalidate();
            }
        });
        Marks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new MarksForm(null);
                pnlCards.repaint();
                pnlCards.revalidate();
            }
        });
    }


    private void createUIComponents() {
        System.out.println(this.tutId);
        TutorTableModels tutorTableModels = new TutorTableModels();
        TutorDetails tutor = new Tutors().getTutorDetails(this.tutId);
        Tutors tutors = new Tutors();
        ArrayList<String> modules = tutors.getAssignedModules(tutId);

        if (tutor == null) {
            JOptionPane.showMessageDialog(this, "Error Fetching Tutor Details");
            return;
        }
        if (modules == null) {
            JOptionPane.showMessageDialog(this, "Error Fetching Tutor Modules");
            return;
        }
        tutor.setModules(modules);
        System.out.println(this.tutId);

        pnlCoursesCenter = new JPanel();
        CustomTable course_table = new CustomTable();
        course_table.setModel(tutorTableModels.getCourseDefaultTableModel());
        pnlCoursesCenter.add(new JScrollPane(course_table));


        module1 = new JPanel();
        CustomTable module1_table = new CustomTable();
        tutor.setModule1code(tutor.getModules().get(0));
        module1_table.setModel(tutorTableModels.getStudentDefaultTableModel(tutor));
        module1.add(new JScrollPane(module1_table));
        module1_table.revalidate();
        module1_table.repaint();

        module2 = new JPanel();
        CustomTable module2_table = new CustomTable();
        tutor.setModule2code(tutor.getModules().get(1));
        module2_table.setModel(tutorTableModels.getStudentDefaultTableModel(tutor));
        module2.add(new JScrollPane(module2_table));
        module2_table.revalidate();
        module2_table.repaint();

        module3 = new JPanel();
        CustomTable module3_table = new CustomTable();
        tutor.setModule3code(tutor.getModules().get(2));
        module3_table.setModel(tutorTableModels.getStudentDefaultTableModel(tutor));
        module3.add(new JScrollPane(module3_table));
        module3_table.revalidate();
        module3_table.repaint();

        module4 = new JPanel();
        CustomTable module4_table = new CustomTable();
        tutor.setModule4code(tutor.getModules().get(3));
        module4_table.setModel(tutorTableModels.getStudentDefaultTableModel(tutor));
        module4.add(new JScrollPane(module4_table));
        module4_table.revalidate();
        module4_table.repaint();

    }
}
