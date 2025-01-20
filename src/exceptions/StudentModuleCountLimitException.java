package exceptions;

public class StudentModuleCountLimitException extends Throwable {
    public StudentModuleCountLimitException() {
        super("You Cannot Enroll in More Than 4 Modules For a Semester");
    }
}
