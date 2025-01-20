package Backend.Details;

/*
 * Class to store the Information of Modules
 * Contains the getter and setter methods for retrieving Module Information
 */
public class ModuleDetails {
    private String moduleCode;
    private String moduleName;
    private String moduleType;
    private int semester;
    private String courseId;
    private String courseFieldName;
    private String level;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCourseField() {
        return courseId;
    }

    public void setCourseField(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseFieldName() {
        return courseFieldName;
    }

    public void setCourseFieldName(String courseFieldName) {
        this.courseFieldName = courseFieldName;
    }

    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }



}
