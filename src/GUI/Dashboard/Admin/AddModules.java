package GUI.Dashboard.Admin;

import Backend.Details.ModuleDetails;
import Backend.Modules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddModules extends JDialog{
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel InrTop;
    private JPanel title;
    private JLabel pageTitle;
    private JPanel pnlIcon;
    private JLabel Icon;
    private JPanel pnlCenter;
    private JPanel pnlSouth;
    private JPanel pnlButtons;
    private JPanel pnlEntryBox;
    private JPanel labels;
    private JLabel MCode;
    private JLabel MLevel;
    private JLabel MCourseId;
    private JLabel CTotalSems;
    private JLabel MName;
    private JLabel MModuleType;
    private JPanel entries;
    private JTextField txtMCode;
    private JTextField txtMName;
    private JComboBox txtMModuleType;
    private JComboBox txtMSem;
    private JComboBox txtMLevel;
    private JTextField tctMCourseId;
    private JButton btnAddModule;
    private JButton btnCancelFromModule;


    public AddModules( JFrame parent) {

        super(parent, "Add Module", true);
        setContentPane(pnlMain);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnCancelFromModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnAddModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                    String moduleCode = txtMCode.getText();
                    String moduleName = txtMName.getText();
                    String moduleType = (String) txtMModuleType.getSelectedItem();
                    int semester = Integer.parseInt((String)txtMSem.getSelectedItem());
                    String courseId = tctMCourseId.getText();
                    String level = (String) txtMLevel.getSelectedItem();
                    ModuleDetails module = new ModuleDetails();
                    Modules modules = new Modules();
                    module.setModuleCode(moduleCode);
                    module.setModuleName(moduleName);
                    module.setModuleType(moduleType);
                    module.setSemester(semester);
                    module.setCourseField(courseId);
                    module.setLevel(level);
                    boolean execute = modules.createModule(module);
                    if (execute) {
                        JOptionPane.showMessageDialog(null, "Module Added Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error Adding Module", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                new AdminDashboard(null).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new AddModules(null);
    }
}
