package Backend.Interfaces;

/*
 * CRUD operations for students
 */

import Backend.Details.StudentDetails;
import java.util.ArrayList;

public interface StudentInterface {
    public boolean createStudent(StudentDetails s);
    public boolean updateStudent(StudentDetails s);
    public boolean removeStudent(int stdID);
    public boolean enrollModule(StudentDetails stdDetails, String CourseCode);
    public ArrayList<String> enrolledModule(StudentDetails stdDetails);
    public String getCourse(StudentDetails stdDetails);

    public StudentDetails getStudentDetails(int stdID);
    public int generateStudentID();
}
