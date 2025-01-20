package exceptions;

public class DuplicateTutorModuleRegistrationException extends Throwable{
    public DuplicateTutorModuleRegistrationException() {
        super("The Tutor is Already Assigned in the Specified Module");
    }
}
