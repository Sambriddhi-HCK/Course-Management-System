package Backend.Details;
/*
* Class to store details of Courses and their Availability
* Contains the getter and setter methods for retrieving Course Information
*/

public class CourseDetails {
    private String courseCode;
    private String courseName;
    private int totalModule;
    private int totalSemester;
    private int courseLength;
    private String availability;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTotalModule() {
        return totalModule;
    }

    public void setTotalModule(int totalModule) {
        this.totalModule = totalModule;
    }

    public int getTotalSemester() {
        return totalSemester;
    }

    public void setTotalSemester(int totalSemester) {
        this.totalSemester = totalSemester;
    }

    public int getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(int courseLength) {
        this.courseLength = courseLength;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(boolean isAvailable) {
        this.availability = (isAvailable ? "Available" : "Not Available");
    }

}
