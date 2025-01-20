package Backend.Details;

/*
 * This class is used to store the Information of Students
 * This class contains the getter and setter methods for retrieving Student Information
 */

import java.sql.Date;
import java.util.ArrayList;

public class StudentDetails {
    private int stdId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;
    private String phone;
    private String courseEnrolled;
    private int academicYear;
    private int currentSemester;
    private float finalGrade;
    private String email;
    private String password;
    private Date dateEnrolled;
    private ArrayList<Object> modulesEnrolled = new ArrayList<>();

    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCourseEnrolled() {
        return courseEnrolled;
    }

    public void setCourseEnrolled(String courseEnrolled) {
        this.courseEnrolled = courseEnrolled;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(float finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Object> getModulesEnrolled() {
        return modulesEnrolled;
    }

    public void setModulesEnrolled(ArrayList<Object> modulesEnrolled) {
        this.modulesEnrolled = modulesEnrolled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordString) {
        this.password = passwordString;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(Date dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }
}
