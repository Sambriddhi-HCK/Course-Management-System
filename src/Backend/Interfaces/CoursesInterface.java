package Backend.Interfaces;

/*
* CRUD operations for courses
*/

import Backend.Details.CourseDetails;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
public interface CoursesInterface {
    public boolean createCourse(CourseDetails course);
    public boolean updateCourse(CourseDetails course, String newID);
    public boolean setCourseAvailability(String courseCode);
    public boolean removeCourse(String courseCode);
    public LinkedHashMap<String, String> courseList();
    public HashMap<String, String> availableCourses();
    public List<Integer> getTotalSemesters(String moduleCode);
    public CourseDetails getCourseDetail(String courseCode);
}
