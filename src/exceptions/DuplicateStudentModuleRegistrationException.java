package exceptions;

public class DuplicateStudentModuleRegistrationException extends Throwable{
    public DuplicateStudentModuleRegistrationException() {
        super("You Have Already Enrolled For This Module");
    }
}
